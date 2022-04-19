package com.nexai.task4;


import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.TicketDao;
import com.nexai.task4.model.dao.impl.TicketDaoImpl;
import com.nexai.task4.model.entity.Ticket;
import com.nexai.task4.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException, DaoException {
        Connection connection = DataSource.getInstance().getConnection();
        TicketDao ticketDao = TicketDaoImpl.getInstanse();


        Ticket ticket = new Ticket(6,4,2);
        ticketDao.create(ticket);

        System.out.println(ticketDao.getAll());
        ticketDao.delete(ticket);

        System.out.println(ticketDao.getById(2));


    }
}
