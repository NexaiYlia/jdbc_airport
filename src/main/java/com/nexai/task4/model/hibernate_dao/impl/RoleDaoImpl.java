package com.nexai.task4.model.hibernate_dao.impl;

import com.nexai.task4.model.entity.Role;
import com.nexai.task4.model.hibernate_dao.RoleDao;
import com.nexai.task4.pool.HibernateDataSource;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class RoleDaoImpl extends DefaultDaoImpl<Role> implements RoleDao {
    @Override
    public List<Role> getAll() {
        Session session = HibernateDataSource.getInstance().getSession();

        Query query = session.createQuery("from Role");

        return query.getResultList();
    }

    @Override
    public Role getById(int id) {
        Session session = HibernateDataSource.getInstance().getSession();

        return session.get(Role.class, id);
    }
}
