package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.entity.User;

public interface UserDao extends DefaultDao<User>{
    boolean authenticate(String login, String password) throws DaoException;
}
