package com.nexai.task4.model.hibernate_dao;

import com.nexai.task4.model.entity.User;

public interface UserDao extends DefaultDao<User> {
    User getByLogin(String login);
}
