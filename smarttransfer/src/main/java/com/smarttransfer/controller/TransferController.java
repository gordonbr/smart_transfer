package com.smarttransfer.controller;

import com.smarttransfer.repository.TransferModelDAO;
import com.smarttransfer.util.AutomatedResponse;
import com.smarttransfer.util.EMessages;
import com.smarttransfer.util.ErrorHandler;
import com.smarttransfer.util.HibernateUtil;
import io.javalin.Handler;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferController {

    private static TransferModelDAO transferModelDAO = new TransferModelDAO();
    private static Logger LOGGER = LoggerFactory.getLogger(TransferController.class);

    public static Handler getByAccountSource = ctx -> {
        String id = ctx.pathParam("id");
        LOGGER.info("GET byAccountSource with id = {}", id);

        if(ErrorHandler.isAccountNumberValid(id)){
            try(Session session = HibernateUtil.getSession()) {
                session.beginTransaction();
                ctx.json(transferModelDAO.loadByAccountId(session, Long.parseLong(id)));
            }

        } else {
            ctx.status(500);
            ctx.json(AutomatedResponse.generateMessage(EMessages.INVALID_VALUE));
        }


    };
}
