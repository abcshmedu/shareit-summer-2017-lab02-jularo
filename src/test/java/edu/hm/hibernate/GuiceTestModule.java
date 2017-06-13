package edu.hm.hibernate;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by axel on 26.05.17.
 */
public class GuiceTestModule extends AbstractModule {

    protected void configure() {
        bind(SessionFactory.class).toInstance(new Configuration().configure().buildSessionFactory());
    }

}
