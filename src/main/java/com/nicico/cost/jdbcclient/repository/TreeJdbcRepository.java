package com.nicico.cost.jdbcclient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

public interface TreeJdbcRepository<T, I extends Serializable> extends JdbcRepository<T, I> {

    List<T> findAllByParentId(I id);

    Page<T> findAllByParentId(I id, Pageable pageable);

    Page<T> findAllByParentId(I id, Specification<T> tSpecification,Pageable pageable);

    List<T> findAllByParentIdIsNull();

    List<T> findAllByParentIdIsNull(Specification<T> tSpecification, Sort sort);

    Page<T> findAllByParentIdIsNull(Specification<T> tSpecification, Pageable pageable);

    List<T> findAllByParentIdIsNull(Sort sort);

    List<T> findAllByParentIdIsNull(Specification<T> tSpecification);

    Page<T> findAllByParentIdIsNull(Pageable pageable);
}
