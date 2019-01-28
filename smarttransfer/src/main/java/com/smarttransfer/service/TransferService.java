package com.smarttransfer.service;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.TransferModel;
import com.smarttransfer.repository.AccountDAO;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferService {

    public void transferMoney(TransferModel transferModel){

        AccountDAO accountDAO = AccountDAO.getRepository();

        Account accountSource = accountDAO.load(transferModel.getIdSource());
        Account accountTarget = accountDAO.load((transferModel.getIdTarget()));

        if(accountSource.getBalance() - transferModel.getValue() >= 0){
            accountSource.setBalance(accountSource.getBalance() - transferModel.getValue());
            accountTarget.setBalance((accountTarget.getBalance() + transferModel.getValue()));

            accountDAO.updateTransfer(accountSource, accountTarget);
        }
    }
}
