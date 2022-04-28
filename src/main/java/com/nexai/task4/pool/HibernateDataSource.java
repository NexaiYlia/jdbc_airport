package com.nexai.task4.pool;



import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateDataSource {
    private static HibernateDataSource instance = new HibernateDataSource();
    private static EntityManagerFactory emFactory;

    public static HibernateDataSource getInstance() {

        if (instance == null) {
            instance = new HibernateDataSource();
        }

        return instance;
    }

    private HibernateDataSource() {
        emFactory = Persistence.createEntityManagerFactory("by.it_academy");
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public Session getSession(){
        return emFactory.createEntityManager().unwrap(Session.class);
    }
}
