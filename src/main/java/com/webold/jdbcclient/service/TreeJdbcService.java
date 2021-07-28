package com.webold.jdbcclient.service;

import com.webold.crud.repository.TreeRepository;
import com.webold.framework.domain.dto.PageDTO;
import com.webold.framework.packages.crud.view.Query;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T> is the entity class
 * @param <I> is the type of data base Identity class such as Long,String, ...
 * @apiNote this interface used for connect to table that create structure is parent child and you can fetch some data with pagination and Query and etc...
 * Good Luck
 */
public interface TreeJdbcService<T, I extends Serializable> extends JdbcService<T, I>, TreeRepository<T,I> {

    /**
     * find all root parent
     * @return List<T> of parent
     */
    List<T> findAllParent();

    /**
     *
     * @param query the Object you can set for where clause
     * @return the list of Parent
     */
    List<T> findAllParent(Query query);

    /**
     *
     * @param page the page number that you want find in pagination
     * @param pageSize the total of data that you need to fetch in per page
     * @param query the Object you can set for where clause
     * @return PageDTO<List<T>>  that object of pagination
     */
    PageDTO<List<T>> findAllParent(int page, int pageSize, Query query);

    /**
     *
     * @param page the page number that you want find in pagination
     * @param pageSize the total of data that you need to fetch in per page
     * @return PageDTO<List<T>>  that object of pagination
     */
    PageDTO<List<T>> findAllParent(int page, int pageSize);

    /**
     *
     * @param pid the parent id for get child
     * @return the list of Parent
     */
    List<T> findAll(I pid);

    /**
     *
     * @param page the page number that you want find in pagination
     * @param pageSize the total of data that you need to fetch in per page
     * @param pid the parent id for get child
     * @return PageDTO<List<T>>  that object of pagination {@link PageDTO}
     */
    PageDTO<List<T>> findAll(int page, int pageSize, I pid);

    /**
     *
     * @param page the page number that you want find in pagination
     * @param pageSize the total of data that you need to fetch in per page
     * @param query the Object you can set for where clause
     * @param pid the parent id for get child
     * @return PageDTO<List<T>>  that object of pagination {@link PageDTO}
     */
    PageDTO<List<T>> findAll(int page, int pageSize, Query query, I pid);
}
