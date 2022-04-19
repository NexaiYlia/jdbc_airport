package com.nexai.task4.model.dao.impl;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.DefaultDao;
import com.nexai.task4.pool.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultDaoImpl<T> implements DefaultDao<T> {
    private static final Logger log = LogManager.getLogger();
    String sql = null;

    @Override
    public void create(T entity) throws DaoException {
        sql = getInsertOfEntity();
        try (var connection = DataSource.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            makeRequestForCreate(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        log.debug("Find all entities");
        sql=getSelectOfEntity();
        List<T> entities = new ArrayList<>();
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();
            entities=getListOfEntity(rs,entities);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return entities;
    }

    @Override
    public T getById(int id) throws DaoException {
        log.debug("Get entity by  id:{}", id);
        sql = getSelectByIdOfEntity();
        T item = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            item = getEntity(rs);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return item;
    }

    @Override
    public void update(T entity) throws DaoException {
        log.info("Updating  entity:{}", entity);
        sql= getUpdateOfEntity();
        try (var connection = DataSource.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            makeRequestForUpdate(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(T entity) throws DaoException {
        log.debug("Delete  entity:{}",entity);
        sql = getDeleteOfEntity();
        try (var connection = DataSource.getInstance().getConnection() ;
             var preparedStatement = connection.prepareStatement(sql)){
            makeRequestForDelete(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    String getSelectOfEntity() {
        return null;
    }

    String getSelectByIdOfEntity() {
        return null;
    }

    String getInsertOfEntity() {
        return null;
    }

    String getDeleteOfEntity() {
        return null;
    }

    String getUpdateOfEntity() {
        return null;
    }

    abstract void makeRequestForCreate(PreparedStatement preparedStatement, T item) throws DaoException;

    abstract void makeRequestForUpdate(PreparedStatement preparedStatement, T item);

    abstract void makeRequestForDelete(PreparedStatement preparedStatement, T item) throws DaoException;

    abstract T getEntity(ResultSet rs) throws DaoException;

    abstract List<T> getListOfEntity(ResultSet rs, List<T> items) throws DaoException;
}
