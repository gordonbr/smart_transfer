package com.smarttransfer.repository;

import com.smarttransfer.model.Transfer;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferModelDAO {

    public void save(Session session, Transfer transfer) {

        if(transfer != null) {
            if(session.isOpen() && session.getTransaction().isActive()){
                session.save(transfer);
                session.flush();
            }
        }
    }

    public Transfer load(Session session, long id) {
        Transfer transfer = null;

        if(session.isOpen() && session.getTransaction().isActive()){
            transfer = session.get(Transfer.class, id);
        }

        return transfer;
    }

    public List<Transfer> loadByAccountId(Session session, long id) {

        List<Transfer> transfers = null;
        if(session.isOpen() && session.getTransaction().isActive()) {
            Query query = session.createQuery("from Transfer where accountSource.id = :idSource");
            query.setParameter("idSource", id);
            transfers = query.getResultList();
        }

        return transfers;
    }
}
