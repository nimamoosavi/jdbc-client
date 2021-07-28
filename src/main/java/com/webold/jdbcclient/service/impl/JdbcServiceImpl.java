package com.webold.jdbcclient.service.impl;

import com.webold.framework.domain.dto.PageDTO;
import com.webold.framework.packages.crud.view.Keyword;
import com.webold.framework.packages.crud.view.Query;
import com.webold.framework.packages.crud.view.Sort;
import com.webold.framework.service.exception.ApplicationException;
import com.webold.framework.service.exception.ServiceException;
import com.webold.jdbcclient.repository.JdbcRepository;
import com.webold.jdbcclient.repository.filter.SpecificationBuilder;
import com.webold.jdbcclient.service.JdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.webold.framework.enums.exception.ExceptionEnum.NOT_SAVE;
import static com.webold.framework.enums.exception.ExceptionEnum.NOT_UPDATE;

/**
 * @param <T> is the BaseObject class
 * @param <I> is the type of data base Identity class such as Long,String, ...
 * @apiNote this class you must extended your service and create a bean of it and is the implementation of General Repository in Crud Library
 */
public abstract class JdbcServiceImpl<T, I extends Serializable> implements JdbcService<T, I> {

    @Autowired
    JdbcRepository<T, I> jdbcRepository;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    ApplicationException<ServiceException> applicationException;

    @Autowired
    SpecificationBuilder<T> specificationsBuilder;

    @Override
    public T save(T t) {
        Object id = entityManagerFactory.getPersistenceUnitUtil().getIdentifier(t);
        if (id == null)
            return jdbcRepository.save(t);
        else
            throw applicationException.createApplicationException(NOT_SAVE, HttpStatus.BAD_REQUEST);
    }

    @Override
    public T update(T t) {
        Object id = entityManagerFactory.getPersistenceUnitUtil().getIdentifier(t);
        if (id != null)
            return jdbcRepository.save(t);
        else
            throw applicationException.createApplicationException(NOT_UPDATE, HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<T> saveAll(List<T> tList) {
        return jdbcRepository.saveAll(tList);
    }

    @Override
    public Optional<T> findById(I id) {
        return jdbcRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return jdbcRepository.findAll();
    }

    @Override
    public List<T> findAll(Query query) {
        if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty()) && query.getCriteria() != null) {
            Specification<T> specification = specificationsBuilder.build(query.getCriteria());
            org.springframework.data.domain.Sort sort = sort(query.getSorts());
            return jdbcRepository.findAll(specification, sort);
        } else if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty())) {
            org.springframework.data.domain.Sort sort = sort(query.getSorts());
            return jdbcRepository.findAll(sort);
        } else if (query.getCriteria() != null) {
            Specification<T> specification = specificationsBuilder.build(query.getCriteria());
            return jdbcRepository.findAll(specification);
        } else
            return jdbcRepository.findAll();
    }

    @Override
    public PageDTO<List<T>> findAll(int page, int pageSize, Query query) {
        if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty()) && query.getCriteria() != null) {
            Specification<T> specification = specificationsBuilder.build(query.getCriteria());
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Page<T> all = jdbcRepository.findAll(specification, pageable);
            return convertPageToPageDTO(all);
        } else if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty())) {
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Page<T> all = jdbcRepository.findAll(pageable);
            return convertPageToPageDTO(all);
        } else if (query.getCriteria() != null) {
            Specification<T> specification = specificationsBuilder.build(query.getCriteria());
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = jdbcRepository.findAll(specification, pagination);
            return convertPageToPageDTO(all);
        } else {
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = jdbcRepository.findAll(pagination);
            return convertPageToPageDTO(all);
        }
    }


    @Override
    public long count(Query query) {
        Specification<T> specification = specificationsBuilder.build(query.getCriteria());
        return jdbcRepository.count(specification);
    }

    @Override
    public PageDTO<List<T>> findAll(int page, int pageSize) {
        Pageable pageable = pagination(page, pageSize);
        Page<T> all = jdbcRepository.findAll(pageable);
        return convertPageToPageDTO(all);
    }


    @Override
    public long count() {
        return jdbcRepository.count();
    }

    @Override
    public void deleteById(I id) {
        jdbcRepository.deleteById(id);
    }

    public List<Map<String, Object>> queryForList(String sql, MapSqlParameterSource sqlParameterSource) {
        return namedParameterJdbcTemplate.queryForList(sql, sqlParameterSource);
    }

    public List<T> queryForList(String sql, MapSqlParameterSource sqlParameterSource, Class<T> tClass) {
        return namedParameterJdbcTemplate.queryForList(sql, sqlParameterSource, tClass);
    }

    public List<T> queryForList(String sql, int queryTimeOut, Class<T> tClass) {
        JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        jdbcTemplate.setQueryTimeout(queryTimeOut);
        return jdbcTemplate.queryForList(sql, tClass);
    }

    public List<Map<String, Object>> queryForList(String sql, int queryTimeOut) {
        JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        jdbcTemplate.setQueryTimeout(queryTimeOut);
        return jdbcTemplate.queryForList(sql);
    }

    public T query(String sql, MapSqlParameterSource sqlParameterSource, Class<T> tClass) {
        return namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, tClass);
    }

    public T query(String sql, int queryTimeOut, Class<T> tClass) {
        JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        jdbcTemplate.setQueryTimeout(queryTimeOut);
        return jdbcTemplate.queryForObject(sql, tClass);
    }

    public Map<String, Object> query(String sql, MapSqlParameterSource sqlParameterSource) {
        return namedParameterJdbcTemplate.queryForMap(sql, sqlParameterSource);
    }

    public int maxRows() {
        return namedParameterJdbcTemplate.getJdbcTemplate().getMaxRows();
    }

    public int queryTimeOut() {
        return namedParameterJdbcTemplate.getJdbcTemplate().getQueryTimeout();
    }

    public int queryTimeOut(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return namedParameterJdbcTemplate.getJdbcTemplate().getQueryTimeout();
    }

    public int queryTimeOut(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.getQueryTimeout();
    }


    public Pageable pagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize);
    }

    public Pageable pagination(int page, int pageSize, List<Sort> sorts) {
        List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<>();
        for (Sort sort : sorts) {
            if (Boolean.TRUE.equals(sort.getKeyword().equals(Keyword.DESC)))
                orders.add(org.springframework.data.domain.Sort.Order.desc(sort.getField()));
            else
                orders.add(org.springframework.data.domain.Sort.Order.asc(sort.getField()));
        }
        return PageRequest.of(page - 1, pageSize, org.springframework.data.domain.Sort.by(orders));
    }

    public org.springframework.data.domain.Sort sort(List<Sort> sorts) {
        List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<>();
        for (Sort sort : sorts) {
            if (Boolean.TRUE.equals(sort.getKeyword().equals(Keyword.DESC)))
                orders.add(org.springframework.data.domain.Sort.Order.desc(sort.getField()));
            else
                orders.add(org.springframework.data.domain.Sort.Order.asc(sort.getField()));
        }
        return org.springframework.data.domain.Sort.by(orders);
    }

    public PageDTO<List<T>> convertPageToPageDTO(Page<T> page) {
        return PageDTO.<List<T>>builder().pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .object(page.toList()).build();
    }

}
