package com.nexai.task4.model.dao;

public interface PivotTableDao<T> {
    void create(T item);

    void delete(T item);
}
