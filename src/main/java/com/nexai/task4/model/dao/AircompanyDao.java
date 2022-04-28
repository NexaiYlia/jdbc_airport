package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.entity.Aircompany;
import com.nexai.task4.model.entity.Airplane;
import com.nexai.task4.model.entity.User;

import java.util.List;

public interface AircompanyDao extends DefaultDao<Aircompany> {
    List<Airplane> getAllAirplaneOfAircompany() throws DaoException;

    List<Aircompany> getAllAircompanyWithAirplanes();
}
