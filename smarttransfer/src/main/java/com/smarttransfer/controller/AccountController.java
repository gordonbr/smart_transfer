package com.smarttransfer.controller;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.TransferModel;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.service.TransferService;
import io.javalin.Handler;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class AccountController {

    private static AccountDAO accountDAO = AccountDAO.getRepository();

    public static Handler saveAccount = ctx -> ctx.json(accountDAO.load(Long.parseLong(ctx.pathParam("id"))));

    public static Handler getAccount = ctx -> {
        double balance = Double.parseDouble(ctx.pathParam("balance"));
        Account account = new Account(balance);
        accountDAO.save(account);
        ctx.status(200);
    };

    public static Handler transferMoney = ctx -> {
        TransferModel transferModel = ctx.bodyAsClass(TransferModel.class);
        TransferService transferService = new TransferService();
        transferService.transferMoney(transferModel);
        ctx.json(transferModel);
    };
}
