package com.nicico.cost.jdbcclient.service.impl;

import com.nicico.cost.crud.domain.entity.BaseEntity;
import com.nicico.cost.framework.service.exception.ApplicationException;
import com.nicico.cost.jdbcclient.repository.RepositoryConnector;
import com.nicico.cost.jdbcclient.service.RepositoryConnectorService;
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
public abstract class RepositoryConnectorServiceImpl<T extends BaseEntity<I>, I extends Serializable> implements RepositoryConnectorService<T, I> {

    @Autowired
    RepositoryConnector<T, I> repositoryConnector;
    @Autowired
    ApplicationException applicationException;

    @Override
    public Optional<T> save(T t) {
        T save = repositoryConnector.save(t);
        return Optional.of(save);
    }

    @Override
    public Optional<T> update(I id, T t) {
        t.setId(id);
        T save = repositoryConnector.save(t);
        return Optional.of(save);
    }

    @Override
    public Optional<List<T>> saveAll(List<T> tList) {
        List<T> list = repositoryConnector.saveAll(tList);
        return Optional.of(list);
    }

    @Override
    public Optional<T> findById(I id) {
        return repositoryConnector.findById(id);
    }

    @Override
    public Optional<List<T>> findAll() {
        List<T> all = repositoryConnector.findAll();
        return Optional.of(all);
    }

    @Override
    public Optional<List<T>> findAll(int page, int pageSize) {
        Pageable pageable = pagination(page, pageSize);
        Page<T> all = repositoryConnector.findAll(pageable);
        return Optional.of(all.toList());
    }

    @Override
    public Optional<List<T>> findAll(int page, int pageSize, List<Sort.Order> orders) {
        Pageable pageable = pagination(page, pageSize, orders);
        Page<T> all = repositoryConnector.findAll(pageable);
        return Optional.of(all.toList());
    }

    @Override
    public long count() {
        return repositoryConnector.count();
    }

    @Override
    public void deleteById(I id) {
        repositoryConnector.deleteById(id);
    }

    private Pageable pagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize);
    }

    private Pageable pagination(int page, int pageSize, List<Sort.Order> orders) {
        return PageRequest.of(page - 1, pageSize, Sort.by(orders));
    }
}
