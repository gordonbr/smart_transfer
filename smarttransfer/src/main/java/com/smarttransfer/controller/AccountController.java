package com.smarttransfer.controller;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.ResponseModel;
import com.smarttransfer.model.TransferModel;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.service.TransferService;
import com.smarttransfer.util.HibernateUtil;
import io.javalin.Handler;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class AccountController {

    private static AccountDAO accountDAO = new AccountDAO();
    private static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    public static Handler getAccount = ctx -> {
        LOGGER.info("GET account with id = {}", ctx.pathParam("id"));

        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            ctx.json(accountDAO.load(session, Long.parseLong(ctx.pathParam("id"))));
        }
    };

    public static Handler saveAccount = ctx -> {
        LOGGER.info("POST saveAccount with balance = {}", ctx.pathParam("balance"));

        try(Session session = HibernateUtil.getSession()) {
            double balance = Double.parseDouble(ctx.pathParam("balance"));
            Account account = new Account(balance);
            session.beginTransaction();
            accountDAO.save(session, account);
            session.getTransaction().commit();

            ctx.json(new ResponseModel(1, "Account saved with success."));
        }
    };

    public static Handler transferMoney = ctx -> {
        LOGGER.info("POST transfer");

        TransferModel transferModel = ctx.bodyAsClass(TransferModel.class);
        TransferService transferService = new TransferService();
        boolean success = false;

        for(int i = 0; i < 10 && !success; i++) {
            success = transferService.transferMoney(transferModel);
            if(!success) {
                LOGGER.error("TRANSFER FAILED sleeping");
                Thread.sleep(200);
            }
        }

        if(success) {
            ctx.json(transferModel);
        } else {
            ctx.status(500);
            ctx.json(new ResponseModel(0, "FAILED DUE TO STARVATION"));
        }
    };
}
