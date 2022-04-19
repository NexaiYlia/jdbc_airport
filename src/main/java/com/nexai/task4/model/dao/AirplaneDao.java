package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.entity.Airplane;
import com.nexai.task4.model.entity.User;

public interface AirplaneDao extends DefaultDao<Airplane>{
    Airplane getAirplaneByModel(String model) throws DaoException;
}
