package com.smarttransfer.repository;

import com.smarttransfer.model.Account;
import com.smarttransfer.model.TransferModel;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferModelDAO {

    public void save(Session session, TransferModel transferModel) {

        if(transferModel != null) {
            if(session.isOpen() && session.getTransaction().isActive()){
                session.save(transferModel);
                session.flush();
            }
        }
    }

    public TransferModel load(Session session, long id) {
        TransferModel transferModel = null;

        if(session.isOpen() && session.getTransaction().isActive()){
            transferModel = session.get(TransferModel.class, id);
        }

        return transferModel;
    }

    public List<TransferModel> loadByAccountId(Session session, long id) {

        List<TransferModel> transferModels = null;
        if(session.isOpen() && session.getTransaction().isActive()) {
            Query query = session.createQuery("from TransferModel where accountSource.id = :idSource");
            query.setParameter("idSource", id);
            transferModels = query.getResultList();
        }

        return transferModels;
    }
}
