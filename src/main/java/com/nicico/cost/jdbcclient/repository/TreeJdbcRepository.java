package com.nicico.cost.jdbcclient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface TreeJdbcRepository<T, I extends Serializable> extends JdbcRepository<T, I> {

    List<T> findAllByParentId(I id);

    Page<T> findAllByParentId(I id, Pageable pageable);

    List<T> findAllByParentIdIsNull();

    List<T> findAllByParentIdIsNull(Sort orders);

    Page<T> findAllByParentIdIsNull(Pageable pageable);
}
