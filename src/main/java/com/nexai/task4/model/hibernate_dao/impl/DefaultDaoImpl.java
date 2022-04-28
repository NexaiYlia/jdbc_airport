package com.nexai.task4.model.hibernate_dao.impl;


import com.nexai.task4.model.hibernate_dao.DefaultDao;
import com.nexai.task4.pool.HibernateDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DefaultDaoImpl<T> implements DefaultDao<T> {
    @Override
    public void createOrUpdate(T entity) {
        Session session = HibernateDataSource.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T getById(int id) {
        return null;
    }

    @Override
    public void delete(T entity) {
        Session session = HibernateDataSource.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
    }
}
