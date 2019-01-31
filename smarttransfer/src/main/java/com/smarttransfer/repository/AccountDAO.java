package com.smarttransfer.repository;

import com.smarttransfer.model.Account;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class AccountDAO {

    public void save(Session session, Account account) {

        if(account != null) {
            if(session.isOpen() && session.getTransaction().isActive()){
                Serializable id = session.save(account);
                account.setId((Long)id);
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

    public void delete(Session session, long id) {
        if(session.isOpen() && session.getTransaction().isActive() && id > 0){
            Account account = new Account();
            account.setId(id);
            session.delete(account);
        }
    }

}
