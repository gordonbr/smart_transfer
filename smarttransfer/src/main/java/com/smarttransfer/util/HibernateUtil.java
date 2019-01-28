package com.smarttransfer.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static Session getSession() {
        return factory.openSession();
    }
}
