package com.nicico.cost.jdbcclient.service.impl;

import com.nicico.cost.crud.domain.entity.BaseEntity;
import com.nicico.cost.framework.service.exception.ApplicationException;
import com.nicico.cost.jdbcclient.repository.JdbcRepository;
import com.nicico.cost.jdbcclient.service.JdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @param <T> is the entity class that you must Extended to BaseEntity class {@link com.nicico.cost.crud.domain.entity.BaseEntity}
 * @param <I> is the type of data base Identity class such as Long,String, ...
 * @apiNote this class you must extended your service and create a bean of it and is the implementation of General Repository in Crud Library
 */
public abstract class JdbcServiceImpl<T extends BaseEntity<I>, I extends Serializable> implements JdbcService<T, I> {

    @Autowired
    JdbcRepository<T, I> jdbcRepository;
    @Autowired
    ApplicationException applicationException;

    @Override
    public Optional<T> save(T t) {
        T save = jdbcRepository.save(t);
        return Optional.of(save);
    }

    @Override
    public Optional<T> update(I id, T t) {
        t.setId(id);
        T save = jdbcRepository.save(t);
        return Optional.of(save);
    }

    @Override
    public Optional<List<T>> saveAll(List<T> tList) {
        List<T> list = jdbcRepository.saveAll(tList);
        return Optional.of(list);
    }

    @Override
    public Optional<T> findById(I id) {
        return jdbcRepository.findById(id);
    }

    @Override
    public Optional<List<T>> findAll() {
        List<T> all = jdbcRepository.findAll();
        return Optional.of(all);
    }

    @Override
    public Optional<List<T>> findAll(int page, int pageSize) {
        Pageable pageable = pagination(page, pageSize);
        Page<T> all = jdbcRepository.findAll(pageable);
        return Optional.of(all.toList());
    }

    @Override
    public Optional<List<T>> findAll(int page, int pageSize, String orders) {
        Pageable pageable = pagination(page, pageSize, orders);
        Page<T> all = jdbcRepository.findAll(pageable);
        return Optional.of(all.toList());
    }

    @Override
    public long count() {
        return jdbcRepository.count();
    }

    @Override
    public void deleteById(I id) {
        jdbcRepository.deleteById(id);
    }

    private Pageable pagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize);
    }

    private Pageable pagination(int page, int pageSize, String order) {
        return PageRequest.of(page - 1, pageSize, Sort.by(order));
    }
}
