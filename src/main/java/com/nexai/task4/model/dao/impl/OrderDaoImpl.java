package com.nexai.task4.model.dao.impl;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.OrderDao;
import com.nexai.task4.model.entity.Order;
import com.nexai.task4.pool.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_ORDER = "INSERT into order(number,date,user,route) values(????)";
    private static final String DELETE_ORDER = "DELETE from order WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE order SET number=?, date=?, user=?,route=? WHERE id=?";
    private static final String FIND_ALL_ORDER = "SELECT * FROM order ";
    private static final String FIND_BY_ID_ORDER = "SELECT * FROM order WHERE id=?";
    private static OrderDaoImpl instanse = new OrderDaoImpl();
    private List<Order> orders = new ArrayList<>();

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstanse() {
        return instanse;
    }

    @Override
    public void create(Order order) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ORDER)) {
            statement.setString(1, order.getNumber());
            statement.setDate(2, (Date) order.getDate());
            statement.executeUpdate();
            log.info("Order was added successfully");
        } catch (SQLException e) {
            log.error("Order was not added");
            throw new DaoException("Order was not added", e);
        }
    }

    @Override
    public List<Order> getAll() throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDER)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setNumber(rs.getString(2));
                order.setDate(rs.getDate(3));

                orders.add(order);
            }
        } catch (SQLException e) {
            log.error("Can't create list of orders");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all orders");
        return orders;
    }

    @Override
    public Order getById(int id) throws DaoException {
        Order order = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_ORDER)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getInt(1));
                order.setNumber(rs.getString(2));
                order.setDate(rs.getDate(3));

            }
        } catch (SQLException e) {
            log.error("Order with id" + order.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Order with id" + order.getId() + "was found");
        return order;
    }

    @Override
    public void update(Order order) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            statement.setString(1, order.getNumber());
            statement.setDate(2, (Date) order.getDate());
            statement.setInt(3, order.getId());
            statement.executeUpdate();
            log.info("Order was updated successfully");
        } catch (SQLException e) {
            log.error("Order wasn't updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Order order) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
            statement.setInt(1, order.getId());
            statement.executeUpdate();
            log.info("Order was deleted successfully");
        } catch (SQLException e) {
            log.error("Order was not deleted");
            throw new DaoException("Order was not deleted", e);
        }
    }
}
