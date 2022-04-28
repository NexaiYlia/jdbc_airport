package com.nexai.task4.model.dao.impl;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.UserDao;
import com.nexai.task4.model.entity.Role;
import com.nexai.task4.model.entity.User;
import com.nexai.task4.pool.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
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

    public void create(User user) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPassport());

            Integer roleId = null;

            if (user.getRole() != null) {
                roleId = user.getRole().getId();
            }

            statement.setInt(5, roleId);

            statement.execute();
            log.info("User was added successfully");
        } catch (SQLException e) {
            log.error("User was not added");
            throw new DaoException("User was not added", e);
        }
    }

    public void update(User user) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPassport());

            Integer roleId = null;

            if (user.getRole() != null) {
                roleId = user.getRole().getId();
            }

            statement.setInt(5, roleId);

            statement.executeUpdate();
            log.info("User was updated successfully");
        } catch (SQLException e) {
            log.error("User wasn't updated");
            throw new DaoException("SQL error", e);
        }
    }

    public void delete(User user) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getId());
            statement.execute();
            log.info("User was deleted successfully");
        } catch (SQLException e) {
            log.error("User was not deleted");
            throw new DaoException("Route was not deleted", e);
        }
    }

    public List<User> getAll() throws DaoException {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USER)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                var user = User.builder()
                        .id(rs.getInt(1))
                        .login(rs.getString(2))
                        .name(rs.getString(3))
                        .password(rs.getString(4))
                        .passport(rs.getString(5))
                        .role(checkRoleExist(rs.getInt(6), rs.getString(7), roles))
                        .build();

                users.add(user);
            }
        } catch (SQLException e) {
            log.error("Can't create list of user");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all user");
        return users;
    }


    @Override
    public User getById(int id) throws DaoException {
        User user = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_USER)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = User.builder()
                        .id(rs.getInt(1))
                        .login(rs.getString(2))
                        .name(rs.getString(3))
                        .password(rs.getString(4))
                        .passport(rs.getString(5))
                        .build();
            }
        } catch (SQLException e) {
            log.error("User with id" + user.getId() + " wasn't found");
            throw new DaoException("SQL error", e);
        }
        log.info("User with id" + user.getId() + "was found");
        return user;
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

    public static Role checkRoleExist(int id, String name, List<Role> roles) {
        var mayBeRole = roles.stream().filter(role -> role.getId() == id).findFirst();

        if (mayBeRole.isPresent()) {
            return mayBeRole.get();
        } else {
            var role = Role.builder()
                    .id(id)
                    .name(name)
                    .users(new ArrayList<>())
                    .build();

            roles.add(role);
            return role;
        }
    }
}







