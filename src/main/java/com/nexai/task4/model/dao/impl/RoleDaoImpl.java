package com.nexai.task4.model.dao.impl;

import com.nexai.task4.pool.DataSource;
import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.dao.RoleDao;
import com.nexai.task4.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoleDaoImpl implements RoleDao {
    private static final Logger log = LogManager.getLogger();
    private static final String CREATE_ROLE = "INSERT into airportdb.role(id,name_role) values(?,?)";
    private static final String FIND_ALL_ROLE = "SELECT * FROM airportdb.role";
    private static final String FIND_BY_ID_ROLE = "SELECT * FROM airportdb.role WHERE id=?";
    private static RoleDaoImpl instanse = new RoleDaoImpl();
    private List<Role> roles = new ArrayList<>();

    private RoleDaoImpl() {
    }

    public static RoleDaoImpl getInstanse() {
        return instanse;
    }

    @Override
    public void create(Role role) throws DaoException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ROLE)) {
            statement.setInt(1, role.getId());
            statement.setString(2, role.getName());
            statement.executeUpdate();
            log.info("Role was added successfully");
        } catch (SQLException e) {
            log.error("Role was not added");
            throw new DaoException("Role was not added", e);
        }
    }

    @Override
    public List<Role> getAll() throws DaoException {

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ROLE)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt(1));
                role.setName(rs.getString(2));

                roles.add(role);
            }
        } catch (SQLException e) {
            log.error("Can't create list of roles");
            throw new DaoException("SQL error", e);
        }
        log.info("Create list of all roles");
        return roles;
    }

    @Override
    public Role getById(int id) throws DaoException {
        Role role = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_ROLE)) {
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

    @Override
    public void update(Role entity) {
        log.error("You cannot perform this operation");
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Role entity) {
        log.error("You cannot perform this operation");
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Role> getRoleByName(String name) throws DaoException {
        log.info("List of roles with name: " + name + " was created");
        return getAll().stream()
                .filter(role -> role.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> getRoleById(int roleId) throws DaoException {
        log.info("List of roles with id: " + roleId + " was created");
        return getAll().stream()
                .filter(role -> role.getId() == roleId)
                .collect(Collectors.toList());
    }
}

