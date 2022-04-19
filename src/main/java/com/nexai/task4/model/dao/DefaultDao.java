package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;

import java.util.List;

public interface DefaultDao<T> {
    void create(T entity) throws DaoException;

    List<T> getAll() throws DaoException;

    T getById(int id) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;
}
