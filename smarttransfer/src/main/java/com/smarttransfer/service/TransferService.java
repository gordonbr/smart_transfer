package com.smarttransfer.service;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.Transfer;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.repository.TransferModelDAO;
import com.smarttransfer.util.EMessages;
import com.smarttransfer.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferService {

    private static Logger LOGGER = LoggerFactory.getLogger(TransferService.class);

    public EMessages transferMoney(Transfer transfer){

        AccountDAO accountDAO = new AccountDAO();
        TransferModelDAO transferModelDAO = new TransferModelDAO();
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        EMessages messsage;

        try{
            transaction = session.beginTransaction();

            Account accountSource = accountDAO.load(session, transfer.getAccountSource().getId());
            Account accountTarget = accountDAO.load(session, (transfer.getAccountTarget().getId()));

            if(accountSource == null || accountTarget == null) {
                messsage = EMessages.ACCOUNT_NOT_FOUND;
            } else if (transfer.getValue() <= 0) {
                messsage = EMessages.INVALID_VALUE;
            } else if(accountSource.getBalance() - transfer.getValue() < 0) {
                messsage = EMessages.NOT_ENOUGH_FUNDS;
            } else {
                accountSource.setBalance(accountSource.getBalance() - transfer.getValue());
                accountTarget.setBalance((accountTarget.getBalance() + transfer.getValue()));

                accountDAO.update(session, accountSource);
                accountDAO.update(session, accountTarget);

                transfer.setTimestamp(new Date(Calendar.getInstance().getTimeInMillis()));
                transfer.setAccountSource(accountSource);
                transfer.setAccountTarget(accountTarget);
                transferModelDAO.save(session, transfer);

                transaction.commit();
                messsage = EMessages.SUCCESS;
            }

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
                LOGGER.error("ERROR TRANSFER on commit ROLLBACK");
            }
            LOGGER.error("ERROR TRANSFER on commit");
            messsage = EMessages.FAILED;
        } finally {
            session.close();
        }

        return messsage;
    }
}
