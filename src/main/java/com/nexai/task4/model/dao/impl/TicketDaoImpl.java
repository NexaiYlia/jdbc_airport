package com.nexai.task4.model.dao.impl;

import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.TicketDao;
import com.nexai.task4.model.entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_TICKET = "INSERT into airportdb.ticket(id,order_id,number_of_seats) values(?,?,?)";
    private static final String DELETE_TICKET = "DELETE from airportdb.ticket WHERE id=?";
    private static final String UPDATE_TICKET = "UPDATE airportdb.ticket SET number_of_seats=?, order_id=? WHERE id=?";
    private static final String FIND_ALL_TICKET = "SELECT * FROM airportdb.ticket";
    private static final String FIND_BY_ID_TICKET = "SELECT * FROM airportdb.ticket WHERE id=?";
    private static TicketDaoImpl instanse = new TicketDaoImpl();
    private List<Ticket> tickets = new ArrayList<>();

    private TicketDaoImpl() {
    }

    public static TicketDaoImpl getInstanse() {
        return instanse;
    }

    @Override
    public void create(Ticket ticket) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_TICKET)) {
            statement.setInt(1, ticket.getId());
            statement.setInt(2, ticket.getOrderId());
            statement.setInt(3, ticket.getNumberOfSeats());
            statement.executeUpdate();
            log.info("Ticket was added successfully");
        } catch (SQLException e) {
            log.error("Ticket was not added");
            throw new DaoException("Ticket was not added", e);
        }
    }

    @Override
    public List<Ticket> getAll() throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_TICKET)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setNumberOfSeats(rs.getInt(2));
                ticket.setOrderId(rs.getInt(3));

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.error("Can't create list of tickets");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all tickets");
        return tickets;
    }

    @Override
    public Ticket getById(int id) throws DaoException {
        Ticket ticket = null;
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_TICKET)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setNumberOfSeats(rs.getInt(2));
                ticket.setOrderId(rs.getInt(3));
            }
        } catch (SQLException e) {
            log.error("Ticket with id" + ticket.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Ticket with id" + ticket.getId() + "was found");
        return ticket;
    }

    @Override
    public void update(Ticket ticket) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setInt(1, ticket.getNumberOfSeats());
            statement.setInt(2, ticket.getOrderId());
            statement.executeUpdate();
            log.info("User updated successfully");
        } catch (SQLException e) {
            log.error("User can't be updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Ticket ticket) throws DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TICKET)) {
            statement.setInt(1, ticket.getId());
            statement.executeUpdate();
            log.info("Ticket was deleted successfully");
        } catch (SQLException e) {
            log.error("Ticket was not deleted");
            throw new DaoException("Ticket was not deleted", e);
        }
    }
}
