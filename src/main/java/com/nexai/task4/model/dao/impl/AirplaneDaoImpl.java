package com.nexai.task4.model.dao.impl;

import com.nexai.task4.model.entity.Aircompany;
import com.nexai.task4.model.entity.Role;
import com.nexai.task4.model.entity.User;
import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.AirplaneDao;
import com.nexai.task4.model.entity.Airplane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDaoImpl implements AirplaneDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_AIRPLANE = "INSERT into airportdb.airplane(model,aircompany_id) values(?,?)";
    private static final String DELETE_AIRPLANE = "DELETE from airportdb.airplane WHERE id=?";
    private static final String UPDATE_AIRPLANE = "UPDATE airportdb.airplane SET model=?,aircompany_id=? WHERE id=?";
    private static final String FIND_ALL_AIRPLANE = "SELECT * FROM airportdb.airplane ";
    private static final String FIND_BY_ID_AIRPLANE = "SELECT * FROM airportdb.airplane WHERE id=?";
    private static AirplaneDaoImpl instanse = new AirplaneDaoImpl();
    private List<Airplane> airplanes = new ArrayList<>();

    private AirplaneDaoImpl() {
    }

    public static AirplaneDaoImpl getInstanse() {
        return instanse;
    }


    @Override
    public void create(Airplane airplane) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_AIRPLANE)) {
            statement.setString(1, airplane.getModel());

            Integer aircompanyId = null;

            if (airplane.getAircompany() != null) {
                aircompanyId = airplane.getAircompany().getId();
            }
            statement.setInt(5, aircompanyId);
            statement.execute();
            log.info("Airplane added successfully");
        } catch (SQLException e) {
            log.error("Airplane not added");
            throw new DaoException("Airplane not added", e);
        }

    }

    @Override
    public void update(Airplane airplane) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AIRPLANE)) {
            statement.setString(1, airplane.getModel());
            Integer aircompanyId = null;

            if (airplane.getAircompany() != null) {
                aircompanyId = airplane.getAircompany().getId();
            }

            statement.setInt(2, aircompanyId);
            statement.setInt(3, airplane.getId());
            statement.executeUpdate();
            log.info("Airplane updated successfully");
        } catch (SQLException e) {
            log.error("Airplane can't be updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Airplane airplane) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE)) {
            statement.setInt(1, airplane.getId());
            statement.executeUpdate();
            log.info("Airplane was deleted successfully");
        } catch (SQLException e) {
            log.error("Airplane was not deleted");
            throw new DaoException("Airplane was not deleted", e);
        }
    }

    @Override
    public List<Airplane> getAll() throws DaoException {
        List<Aircompany> aircompanies = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_AIRPLANE)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                var airplane = Airplane.builder()
                        .id(rs.getInt(1))
                        .model(rs.getString(2))
                        .aircompany(checkAircompanyExist(rs.getInt(3), rs.getString(4), aircompanies))
                        .build();

                airplanes.add(airplane);
            }
        } catch (SQLException e) {
            log.error("Can't create list of airplanes");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all airplanes");
        return airplanes;
    }

    public Aircompany checkAircompanyExist(int id, String name, List<Aircompany> aircompanies) {
        var mayBeAircompany = aircompanies.stream().filter(aircompany -> aircompany.getId() == id).findFirst();

        if (mayBeAircompany.isPresent()) {
            return mayBeAircompany.get();
        } else {
            var aircompany = Aircompany.builder()
                    .id(id)
                    .name(name)
                    .airplaneList(new ArrayList<>())
                    .build();

            aircompanies.add(aircompany);
            return aircompany;
        }
    }


    @Override
    public Airplane getById(int id) throws DaoException {
        Airplane airplane = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_AIRPLANE)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                airplane = Airplane.builder()
                        .id(rs.getInt(1))
                        .model(rs.getString(2))
                        .build();
            }
        } catch (SQLException e) {
            log.error("Aircompany with id" + airplane.getId() + " didn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Aircompany with id" + airplane.getId() + "found");
        return airplane;
    }


    @Override
    public Airplane getAirplaneByModel(String model) throws DaoException {
        var airplane = getAll().stream()
                .filter(entity -> entity.getModel().equals(model))
                .findFirst()
                .orElse(null);
        return airplane;
    }
}
