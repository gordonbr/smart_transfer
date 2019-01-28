package com.smarttransfer.repository;

import com.smarttransfer.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class AccountDAO {

    private SessionFactory sessionFactory;

    private static AccountDAO accountDAO;

    private AccountDAO() {
        sessionFactory = new Configuration().configure()
                .buildSessionFactory();
    }

    public static AccountDAO getRepository() {
        if(accountDAO == null) {
            accountDAO = new AccountDAO();
        }

        return accountDAO;
    }

    public void save(Account account) {

        if(account != null) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(account);
                session.getTransaction().commit();
            }
        }
    }

    public Account load(long id) {
        Account account = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            account = session.get(Account.class, id);
        }

        return account;
    }

    public void updateTransfer(Account source, Account target) {

        if(source != null && target != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(source);
                session.update(target);
                session.flush();
                transaction.commit();
            }
        }

    }









}
