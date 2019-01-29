package com.smarttransfer.service;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.TransferModel;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.repository.TransferModelDAO;
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

    public boolean transferMoney(TransferModel transferModel){

        AccountDAO accountDAO = new AccountDAO();
        TransferModelDAO transferModelDAO = new TransferModelDAO();
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();

        try{
            transaction = session.beginTransaction();

            Account accountSource = accountDAO.load(session, transferModel.getAccountSource().getId());
            Account accountTarget = accountDAO.load(session, (transferModel.getAccountTarget().getId()));

            /**
             * If there is enough founds in the source account, makes the transfer.
             * Saves the transfer to audit and log.
             */
            if(accountSource.getBalance() - transferModel.getValue() >= 0){
                accountSource.setBalance(accountSource.getBalance() - transferModel.getValue());
                accountTarget.setBalance((accountTarget.getBalance() + transferModel.getValue()));

                accountDAO.update(session, accountSource);
                accountDAO.update(session, accountTarget);

                transferModel.setTimestamp(new Date(Calendar.getInstance().getTimeInMillis()));
                transferModel.setAccountSource(accountSource);
                transferModel.setAccountTarget(accountTarget);
                transferModelDAO.save(session, transferModel);
                transaction.commit();
                return true;
            }

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
                LOGGER.error("ERROR TRANSFER on commit ROLLBACK");
            }
            LOGGER.error("ERROR TRANSFER on commit");
        } finally {
            session.close();
        }

        return false;
    }
}
