package com.nicico.cost.jdbcclient.service;

import com.nicico.cost.crud.repository.GeneralRepository;
import com.nicico.cost.framework.packages.crud.view.Criteria;
import com.nicico.cost.framework.packages.crud.view.Sort;
import com.nicico.cost.framework.packages.jdbc.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @param <T> is the entity class
 * @param <I> is the IncrementalId Type Of Relation Data base
 * @author nima
 * @version 1.0.1
 * @apiNote this interface is the implementation of JpaRepository of Spring Data you can find know about it in {@link <a https://spring.io/projects/spring-data-jpa</a>}
 * and Used NameParameterJdbcTemplate And JdbcTemplate of Spring Framework For native Query.
 * you must create an interface and extended of it and generate a bean of your interface
 */
public interface JdbcService<T, I extends Serializable> extends GeneralRepository<T, I>, Jdbc<T, I> {

    /**
     * @param sql                is the native query for execute in Data Base
     * @param sqlParameterSource is the parameter source for execute parametric
     * @return List<Map < String, Object>> of the result of data base
     */
    List<Map<String, Object>> queryForList(String sql, MapSqlParameterSource sqlParameterSource);

    /**
     * @param sql                is the native query for execute in Data Base
     * @param sqlParameterSource is the parameter source for execute parametric
     * @param tClass             the class you want cast it
     * @return List<T> the object that cast it
     */
    List<T> queryForList(String sql, MapSqlParameterSource sqlParameterSource, Class<T> tClass);

    /**
     * @param sql          is the native query for execute in Data Base
     * @param queryTimeOut is the time that you want method wait for response
     * @param tClass       the class you want cast it
     * @return List<T> the object that cast it
     */
    List<T> queryForList(String sql, int queryTimeOut, Class<T> tClass);

    /**
     * @param sql          is the native query for execute in Data Base
     * @param queryTimeOut is the time that you want method wait for response
     * @return List<Map < String, Object>> is the result of data Base
     */
    List<Map<String, Object>> queryForList(String sql, int queryTimeOut);

    /**
     * @param sql                is the native query for execute in Data Base
     * @param sqlParameterSource is the parameter source for execute parametric
     * @param tClass             the class you want cast it
     * @return the single result that result from data Base
     * @apiNote you must know if the result not been single the methode throw Runtime Exception
     */
    T query(String sql, MapSqlParameterSource sqlParameterSource, Class<T> tClass);

    /**
     * @param sql          is the native query for execute in Data Base
     * @param queryTimeOut is the time that you want method wait for response
     * @param tClass       the class you want cast it
     * @return T is the Object of result from data base
     */
    T query(String sql, int queryTimeOut, Class<T> tClass);

    /**
     * @param sql                is the native query for execute in Data Base
     * @param sqlParameterSource is the parameter source for execute parametric
     * @return Map<String, Object> the result of Data base
     * @apiNote you must know if the result not been single the methode throw Runtime Exception
     */
    Map<String, Object> query(String sql, MapSqlParameterSource sqlParameterSource);

    /**
     * @return the number of max rows in table
     */
    int maxRows();

    /**
     * @return the default Time out of Connection
     */
    int queryTimeOut();

    /**
     * @param namedParameterJdbcTemplate is the Object of Spring that you must used it for execute query
     * @return the Object Time out of Connection
     */
    int queryTimeOut(NamedParameterJdbcTemplate namedParameterJdbcTemplate);

    /**
     * @param jdbcTemplate is the Object of Spring that you must used it for execute query
     * @return the Object Time out of Connection
     */
    int queryTimeOut(JdbcTemplate jdbcTemplate);

    /**
     * @param t the Entity View Model that you must Add To Data Base
     * @return the Optional Of Entity that save it in data base
     * @apiNote this method used SpringJpa
     */
    T save(T t);

    /**
     * @param t  the Entity View Model that you must Add To Data Base
     * @return the Entity that save it in data base
     * @apiNote this method used SpringJpa
     */
    T update(T t);

    /**
     * @param tList the list of Entity that you must save it in Data base
     * @return the List Of Entity and their Ids
     * @apiNote this method used SpringJpa
     */
    List<T> saveAll(List<T> tList);

    /**
     * @param id the incrementalId of data base Object
     * @return the Entity that save it in data base
     * @apiNote used for fetch Data By IncrementalId And this method used SpringJpa
     */
    Optional<T> findById(I id);

    /**
     * @return the List Of Entities
     * @apiNote this method used SpringJpa
     */
    List<T> findAll();

    /**
     * @param criteria is the criteria for find in where Clause
     * @return the List Of Entities
     * @apiNote this methode uses for Fetch All Data
     */
    List<T> findAll(Criteria criteria);

    /**
     * @param page     the page number that you must fetch it
     * @param pageSize the page Size of that you need to split Data
     * @apiNote this method used SpringJpa
     */
    List<T> findAll(int page, int pageSize);

    /**
     * @param page     the page number that you must fetch it
     * @param criteria is the criteria for find in where Clause
     * @param pageSize the page Size of that you need to split Data
     * @return the Optional List Of Entity from Response Of Data Base
     */
    List<T> findAll(int page, int pageSize, Criteria criteria);

    /**
     * @param page     the page number that you must fetch it
     * @param pageSize the page Size of that you need to split Data
     * @param sorts   is the list of fields and your direction such as Asc and Desc for Sorting
     * @return the List Of Entity from Response Of Data Base
     * @apiNote this method used SpringJpa
     */
    List<T> findAll(int page, int pageSize, List<Sort> sorts);

    /**
     * @param page     the page number that you must fetch it
     * @param criteria is the criteria for find in where Clause
     * @param pageSize the page Size of that you need to split Data
     * @param orders   is the list of fields and your direction such as Asc and Desc for Sorting
     * @return the Optional List Of Entity from Response Of Data Base
     */
    List<T> findAll(int page, int pageSize, List<Sort> orders, Criteria criteria);

    /**
     * @return the Number Of data
     * @apiNote this method used SpringJpa
     */
    long count();

    /**
     * @param criteria is the criteria for find in where Clause
     * @return the Number Of data
     * @apiNote method used for get the count Of Data
     */
    long count(Criteria criteria);

    /**
     * @param id is the incrementalId of Object that you need too remove it from Data Base
     * @throws RuntimeException has throw if Delete Method Not Acceptable
     * @apiNote this method used SpringJpa
     */
    void deleteById(I id);
}
