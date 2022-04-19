package com.nexai.task4.model.dao.impl;

import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.RouteDao;
import com.nexai.task4.model.entity.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouteDaoImpl implements RouteDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_ROUTE = " INSERT into airportdb.route(id,city_from,city_to,price) values(?,?,?,?)";
    private static final String DELETE_ROUTE = "DELETE from airportdb.route WHERE id=?";
    private static final String UPDATE_ROUTE = "UPDATE airportdb.route SET cityFrom=?, cityTo=?, price=?  WHERE id=?";
    private static final String FIND_ALL_ROUTE = " SELECT * FROM airportdb.route ";
    private static final String FIND_BY_ID_ROUTE = "SELECT * FROM airportdb.route WHERE id=?";
    private static RouteDaoImpl instanse = new RouteDaoImpl();
    private List<Route> routes = new ArrayList<>();

    private RouteDaoImpl() {
    }

    public static RouteDaoImpl getInstanse() {
        return instanse;
    }

    @Override
    public void create(Route route) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ROUTE)) {
            statement.setInt(1, route.getId());
            statement.setString(2, route.getCityFrom());
            statement.setString(3, route.getCityTo());
            statement.setInt(4, route.getPrice());
            statement.executeUpdate();
            log.info("Route was added successfully");
        } catch (SQLException e) {
            log.error("Route was not added");
            throw new DaoException("Route was not added", e);
        }
    }

    @Override
    public List<Route> getAll() throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_ROUTE)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Route route = new Route();
                route.setId(rs.getInt(1));
                route.setCityFrom(rs.getString(2));
                route.setCityTo(rs.getString(3));
                route.setPrice(rs.getInt(4));

                routes.add(route);
            }
        } catch (SQLException e) {
            log.error("Can't create list of routes");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all routes");
        return routes;
    }

    @Override
    public Route getById(int id) throws DaoException {
        Route route = null;
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_ROUTE)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                route = new Route();
                route.setId(rs.getInt(1));
                route.setCityFrom(rs.getString(2));
                route.setCityTo(rs.getString(3));
                route.setPrice(rs.getInt(4));
            }

        } catch (SQLException e) {
            log.error("Route with id" + route.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Route with id" + route.getId() + "was found");
        return route;
    }

    @Override
    public void update(Route route) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
            statement.setString(1, route.getCityFrom());
            statement.setString(2, route.getCityTo());
            statement.setInt(3, route.getPrice());
            statement.setInt(4, route.getId());
            statement.executeUpdate();
            log.info("Route was updated successfully");
        } catch (SQLException e) {
            log.error("Route wasn't updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Route route) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setInt(1, route.getId());
            statement.executeUpdate();
            log.info("Route was deleted successfully");
        } catch (SQLException e) {
            log.error("Route was not deleted");
            throw new DaoException("Route was not deleted", e);
        }
    }

    @Override
    public List<Route> getRoutesByCityFrom(String cityFrom) throws DaoException {
        return getAll().stream()
                .filter(route -> route.getCityFrom().equals(cityFrom))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> getRoutesByCityTo(String cityTo) throws DaoException {
        return getAll().stream()
                .filter(route -> route.getCityTo().equals(cityTo))
                .collect(Collectors.toList());
    }
}
