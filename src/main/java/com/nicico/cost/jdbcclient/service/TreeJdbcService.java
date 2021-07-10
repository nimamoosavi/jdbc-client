package com.nicico.cost.jdbcclient.service;

import com.nicico.cost.framework.domain.dto.PageDTO;
import com.nicico.cost.framework.packages.crud.view.Sort;

import java.io.Serializable;
import java.util.List;

public interface TreeJdbcService<T, I extends Serializable> extends JdbcService<T, I> {
    List<T> findAll(I pid);

    List<T> findAllParent();

    List<T> findAllParent(List<Sort> orders);

    PageDTO<List<T>> findAllParent(int page, int pageSize, List<Sort> orders);

    PageDTO<List<T>> findAllParent(int page, int pageSize);

    PageDTO<List<T>> findAll(int page, int pageSize, I pid);

    PageDTO<List<T>> findAll(int page, int pageSize, List<Sort> orders, I pid);
}
