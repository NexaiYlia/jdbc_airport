package com.nexai.task4.model.dao.impl;

import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.AircompanyDao;
import com.nexai.task4.model.entity.Aircompany;
import com.nexai.task4.model.entity.Airplane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AircompanyDaoImpl implements AircompanyDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_AIRCOMPANY = "INSERT into airportdb.aircompany(name) values(?)";
    private static final String DELETE_AIRCOMPANY = "DELETE from airportdb.aircompany WHERE id=?";
    private static final String UPDATE_AIRCOMPANY = "UPDATE airportdb.aircompany SET name=?  WHERE id=?";
    private static final String FIND_ALL_AIRCOMPANY = "SELECT * FROM airportdb.aircompany ";
    private static final String FIND_ALL_AIRPLANES_OF_AIRCOMPANY = "SELECT * FROM airportdb.airplane WHERE aircompany_id=?";
    private static final String FIND_BY_ID_AIRCOMPANY = "SELECT * FROM airportdb.aircompany WHERE id=?";
    private static AircompanyDaoImpl instanse = new AircompanyDaoImpl();
    private List<Aircompany> aircompanies = new ArrayList<>();

    private AircompanyDaoImpl() {
    }

    public static AircompanyDaoImpl getInstanse() {
        return instanse;
    }

    @Override
    public void create(Aircompany aircompany) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_AIRCOMPANY)) {
            statement.setString(1, aircompany.getName());
            statement.executeUpdate();
            log.info("Aircompany was added successfully");
        } catch (SQLException e) {
            log.error("Aircompany was not added");
            throw new DaoException("Aircompany was not added", e);
        }

    }

    @Override
    public List<Aircompany> getAll() throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_AIRCOMPANY)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Aircompany aircompany = new Aircompany();
                aircompany.setId(rs.getInt(1));
                aircompany.setName(rs.getString(2));

                aircompanies.add(aircompany);
            }
        } catch (SQLException e) {
            log.error("Can't create list of aircompanies");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all aircompanies");
        return aircompanies;
    }

    @Override
    public List<Airplane> getAllAirplaneOfAircompany() throws DaoException {
        List<Airplane> airplanes = new ArrayList<>();
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_AIRPLANES_OF_AIRCOMPANY)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Airplane airplane = new Airplane();
                airplane.setId(rs.getInt(1));
                airplane.setModel(rs.getString(2));
                airplane.setAircompanyId(rs.getInt(3));
                airplanes.add(airplane);
            }
        } catch (SQLException e) {
            log.error("Can't create list of aircompanies");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all aircompanies");
        return airplanes;
    }

    @Override
    public Aircompany getById(int id) throws DaoException {
        Aircompany aircompany = null;
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_AIRCOMPANY)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                aircompany = new Aircompany();
                aircompany.setId(rs.getInt(1));
                aircompany.setName(rs.getString(2));
            }

        } catch (SQLException e) {
            log.error("Aircompany with id" + aircompany.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Aircompany with id" + aircompany.getId() + "was found");
        return aircompany;
    }

    @Override
    public void update(Aircompany aircompany) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AIRCOMPANY)) {
            statement.setString(1, aircompany.getName());
            statement.setInt(2, aircompany.getId());
            statement.executeUpdate();
            log.info("Aircompany was updated successfully");
        } catch (SQLException e) {
            log.error("Aircompany wasn't be updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Aircompany aircompany) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AIRCOMPANY)) {
            statement.setInt(1, aircompany.getId());
            statement.executeUpdate();
            log.info("Aircompany was deleted successfully");
        } catch (SQLException e) {
            log.error("Aircompany was not deleted");
            throw new DaoException("Aircompany was not deleted", e);
        }
    }
}
