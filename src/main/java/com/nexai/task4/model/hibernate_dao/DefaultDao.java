package com.nexai.task4.model.hibernate_dao;

import java.util.List;

public interface DefaultDao<T> {
    void createOrUpdate(T entity);

    List<T> getAll();

    T getById(int id);

    void delete(T entity);
}
