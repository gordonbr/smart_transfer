package com.smarttransfer.controller;

import com.smarttransfer.repository.TransferModelDAO;
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

        LOGGER.info("GET byAccountSource with id = {}", ctx.pathParam("id"));
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            ctx.json(transferModelDAO.loadByAccountId(session, Long.parseLong(ctx.pathParam("id"))));
        }
    };
}
