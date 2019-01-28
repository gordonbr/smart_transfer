package com.smarttransfer.repository;

import com.smarttransfer.model.Account;
import org.hibernate.Session;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class AccountDAO {

    public void save(Session session, Account account) {

        if(account != null) {
            if(session.isOpen() && session.getTransaction().isActive()){
                session.save(account);
                session.flush();
            }
        }
    }

    public Account load(Session session, long id) {
        Account account = null;

        if(session.isOpen() && session.getTransaction().isActive()){
            account = session.get(Account.class, id);
        }

        return account;
    }

    public void update(Session session, Account account) {

        if(session.isOpen() && session.getTransaction().isActive() && account != null){
            session.update(account);
        }
    }

//    public void updateTransfer(Session session, Account source, Account target) {
//
//        if(source != null && target != null) {
//            try (Session session = sessionFactory.openSession()) {
//                Transaction transaction = session.beginTransaction();
//                session.update(source);
//                session.update(target);
//                session.flush();
//                transaction.commit();
//            }
//        }
//
//    }









}
