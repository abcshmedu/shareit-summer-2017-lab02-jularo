package edu.hm.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author boettcher
 */
public class HibernateUtils {

    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}