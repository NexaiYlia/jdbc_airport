package com.nexai.task4.model.dao;

import com.nexai.task4.exception.DaoException;
import com.nexai.task4.model.entity.Route;

import java.util.List;

public interface RouteDao extends DefaultDao<Route>{
    List<Route> getRoutesByCityFrom(String cityFrom) throws DaoException;
    List<Route> getRoutesByCityTo(String cityTo) throws DaoException;
}
