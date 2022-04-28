package com.nexai.task4.model.hibernate_dao.impl;

import com.nexai.task4.model.entity.User;
import com.nexai.task4.model.hibernate_dao.UserDao;
import com.nexai.task4.pool.HibernateDataSource;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoImpl extends DefaultDaoImpl<User> implements UserDao {

    @Override
    public List<User> getAll() {

        EntityManager entityManager = HibernateDataSource.getInstance().getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = criteriaBuilder.or(
                criteriaBuilder.isNotNull(userRoot.get("name")),
                criteriaBuilder.isNotEmpty(userRoot.get("password"))
        );

        criteriaQuery.select(userRoot).orderBy(criteriaBuilder.desc(userRoot.get("id"))).where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public User getById(int id) {
        Session session = HibernateDataSource.getInstance().getSession();

        return session.get(User.class, id);
    }

    @Override
    public User getByLogin(String login) {
        Session session = HibernateDataSource.getInstance().getSession();

        Query query = session.createQuery("from User where login = ?");
        query.setParameter(0, login);

        List<User> users = query.getResultList();

        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
