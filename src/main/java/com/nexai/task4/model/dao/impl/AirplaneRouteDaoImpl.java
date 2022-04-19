package com.nexai.task4.model.dao.impl;

import com.nexai.task4.model.dao.PivotTableDao;
import com.nexai.task4.model.entity.AirplaneRoute;
import com.nexai.task4.pool.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class AirplaneRouteDaoImpl implements PivotTableDao<AirplaneRoute> {
    private static final Logger log = LogManager.getLogger();
    public static final String INSERT = "INSERT INTO airplane_route (airplane_id,route_id) values (? , ?)";
    public static final String DELETE = "DELETE FROM airplane_route WHERE airplane_id =? AND route_id = ?";

    @Override
    public void create(AirplaneRoute item) {
        log.info("Creating " + "item:{}", item);
        try (var connection = DataSource.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setInt(1, item.getAirplaneId());
            preparedStatement.setInt(2, item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(AirplaneRoute item) {
        log.info("Delete " + "item:{}", item);
        try (var connection = DataSource.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, item.getAirplaneId());
            preparedStatement.setInt(2, item.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
