package com.nexai.task4.model.dao.impl;

import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.UserDao;
import com.nexai.task4.model.entity.Role;
import com.nexai.task4.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends DefaultDaoImpl<User> implements UserDao {
    private static final Logger log = LogManager.getLogger();
    private static final String SELECT_LOGIN_PASSWORD = "SELECT password FROM airportdb.user WHERE login =?";
    private static final String CREATE_USER = " INSERT into airportdb.user(id,login,password,name,passport,role_id) values(?,?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE from airportdb.user WHERE id=?";
    private static final String UPDATE_USER = "UPDATE airportdb.user SET login=?, password=?, name=?,passport=?,role_id=?  WHERE id=?";
    private static final String FIND_ALL_USER = "SELECT * FROM airportdb.user ";
    private static final String FIND_BY_ID_USER = "SELECT * FROM airportdb.user WHERE id=?";
    private static final String FIND_BY_ID_ROLE = "SELECT * FROM airportdb.role WHERE id=?";
    private static UserDaoImpl instanse = new UserDaoImpl();
    private List<User> users = new ArrayList<>();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstanse() {
        return instanse;
    }


    @Override
    void makeRequestForCreate(PreparedStatement preparedStatement, User item) throws DaoException {
        try {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getLogin());
            preparedStatement.setString(3, item.getPassword());
            preparedStatement.setString(4, item.getName());
            preparedStatement.setString(5, item.getPassport());
            preparedStatement.setInt(6, item.getRoleId());
            preparedStatement.execute();
            log.info("User was added successfully");
        } catch (SQLException e) {
            log.error("User was not added");
            throw new DaoException("User was not added", e);
        }
    }

    @Override
    public void makeRequestForUpdate(PreparedStatement preparedStatement, User item) {
        try {
            preparedStatement.setString(1, item.getLogin());
            preparedStatement.setString(2, item.getPassword());
            preparedStatement.setString(3, item.getName());
            preparedStatement.setString(4, item.getPassport());
            preparedStatement.setInt(5, item.getRoleId());
            preparedStatement.setInt(6, item.getId());
            preparedStatement.executeUpdate();
            log.info("User updated successfully");
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    void makeRequestForDelete(PreparedStatement preparedStatement, User item) throws DaoException {
        try {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.executeUpdate();
            log.info("User was deleted successfully");
        } catch (SQLException e) {
            log.error("User was not deleted");
            throw new DaoException("User was not deleted", e);
        }
    }

    @Override
    User getEntity(ResultSet rs) throws DaoException {
        User user = null;
        try {
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setPassport(rs.getString(5));
                user.setRoleId(rs.getInt(6));
            }
        } catch (SQLException e) {
            log.error("User with id" + user.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("User with id" + user.getId() + "was found");
        return user;
    }

    @Override
    public List<User> getListOfEntity(ResultSet rs, List<User> items) throws DaoException {
        try {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setPassport(rs.getString(5));
                user.setRoleId(rs.getInt(6));

                users.add(user);
            }
        } catch (SQLException e) {
            log.error("Can't create list of user");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all user");
        return users;
    }


    public boolean authenticate(String login, String password) throws DaoException {

        boolean match = false;
        try (Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passwordFromDb = resultSet.getString(2);
            match = password.equals(passwordFromDb);
            log.info("User was authenticated successfully");
        } catch (SQLException e) {
            log.info("User authentication was failed");
            throw new DaoException("SQL error", e);
        }
        return match;
    }

    private Role findRole(int id) throws DaoException {
        Role role = null;
        Connection connection = DataSource.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_ROLE)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                role = new Role();
                role.setId(rs.getInt(1));
                role.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            log.error("Role with id" + role.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("Role with id" + role.getId() + "was found");
        return role;
    }

    public String getSelectOfEntity() {
        return FIND_ALL_USER;
    }


    public String getSelectByIdOfEntity() {
        return FIND_BY_ID_USER;
    }


    public  String getInsertOfEntity() {
        return CREATE_USER;
    }


    public String getDeleteOfEntity() {
        return DELETE_USER;
    }

    public  String getUpdateOfEntity() {
        return UPDATE_USER;
    }
}







