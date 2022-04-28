package com.nexai.task4.model.dao.impl;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.TicketDao;
import com.nexai.task4.model.entity.Order;
import com.nexai.task4.model.entity.Ticket;
import com.nexai.task4.model.entity.User;
import com.nexai.task4.pool.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TICKET)) {

            statement.setInt(1, ticket.getNumberOfSeats());
            Integer orderId = null;

            if (ticket.getOrder() != null) {
                orderId = ticket.getOrder().getId();
            }

            statement.setInt(2, orderId);
            statement.execute();
            log.info("Ticket was added successfully");
        } catch (SQLException e) {
            log.error("Ticket was not added");
            throw new DaoException("Ticket was not added", e);
        }
    }

    @Override
    public List<Ticket> getAll() throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_TICKET)) {
            List<Order> orders = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                var ticket = Ticket.builder()
                        .id(rs.getInt(1))
                        .numberOfSeats(rs.getInt(2))
                        .order(checkOrderExist(rs.getInt(3), rs.getString(4), rs.getDate(5), orders))
                        .build();


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
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_TICKET)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setNumberOfSeats(rs.getInt(2));

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
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setInt(1, ticket.getNumberOfSeats());
            statement.executeUpdate();
            log.info("User updated successfully");
        } catch (SQLException e) {
            log.error("User can't be updated");
            throw new DaoException("SQL error", e);
        }
    }

    @Override
    public void delete(Ticket ticket) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TICKET)) {
            statement.setInt(1, ticket.getId());
            statement.executeUpdate();
            log.info("Ticket was deleted successfully");
        } catch (SQLException e) {
            log.error("Ticket was not deleted");
            throw new DaoException("Ticket was not deleted", e);
        }
    }

    public Order checkOrderExist(int id, String number, Date date, List<Order> orders) {
        var mayBeOrder = orders.stream().filter(order -> order.getId() == id).findFirst();
        if (mayBeOrder.isPresent()) {
            return mayBeOrder.get();
        } else {
            var order = Order.builder()
                    .id(id)
                    .number(number)
                    .date(date)
                    .build();

            orders.add(order);
            return order;
        }
    }
}
