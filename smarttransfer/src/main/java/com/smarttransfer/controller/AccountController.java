package com.smarttransfer.controller;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.TransferModel;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.service.TransferService;
import com.smarttransfer.util.AutomatedResponse;
import com.smarttransfer.util.EMessages;
import com.smarttransfer.util.ErrorHandler;
import com.smarttransfer.util.HibernateUtil;
import com.sun.org.apache.regexp.internal.RE;
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
        String id = ctx.pathParam("id");

        LOGGER.info("GET account with id = {}", ctx.pathParam("id"));

        if(!ErrorHandler.isAccountNumberValid(id)) {
            ctx.status(500);
            ctx.json(AutomatedResponse.generateMessage(EMessages.INVALID_VALUE));
        } else {

            try (Session session = HibernateUtil.getSession()) {
                session.beginTransaction();
                Account account = accountDAO.load(session, Long.parseLong(id));
                if(account != null) {
                    ctx.json(account);
                } else {
                    ctx.status(500);
                    ctx.json(AutomatedResponse.generateMessage(EMessages.ACCOUNT_NOT_FOUND));
                }
            }
        }
    };

    public static Handler saveAccount = ctx -> {
        String balance = ctx.pathParam("balance");
        LOGGER.info("POST saveAccount with balance = {}", balance);

        if(!ErrorHandler.isAccountNumberValid(balance)) {
            ctx.status(500);
            ctx.json(AutomatedResponse.generateMessage(EMessages.INVALID_VALUE));
        } else {
            try(Session session = HibernateUtil.getSession()) {
                Account account = new Account(Double.parseDouble(balance));
                session.beginTransaction();
                accountDAO.save(session, account);
                session.getTransaction().commit();

                ctx.json(AutomatedResponse.generateMessage(EMessages.SUCCESS));
            }
        }
    };

    public static Handler transferMoney = ctx -> {
        LOGGER.info("POST transfer");
        EMessages result = EMessages.FAILED;

        try {
            TransferModel transferModel = ctx.bodyAsClass(TransferModel.class);
            TransferService transferService = new TransferService();

            for (int i = 0; i < 10 && result.equals(EMessages.FAILED); i++) {
                result = transferService.transferMoney(transferModel);

                if (result.equals(EMessages.FAILED)) {
                    LOGGER.error("TRANSFER FAILED sleeping");
                    Thread.sleep(200);
                }
            }
        } catch (Exception e) {
            result = EMessages.INVALID_JSON;
        }

        if (!result.equals(EMessages.SUCCESS)) {
            ctx.status(500);
        }

        ctx.json(AutomatedResponse.generateMessage(result));
    };
}
