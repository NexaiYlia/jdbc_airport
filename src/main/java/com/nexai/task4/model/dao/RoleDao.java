package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.entity.Role;

import java.util.List;

public interface RoleDao extends DefaultDao<Role> {
    List<Role> getRoleByName(String name) throws DaoException;

    List<Role> getRoleById(int roleId) throws DaoException;
}
