package com.nicico.cost.jdbcclient.service;

import com.nicico.cost.crud.domain.entity.BaseEntity;
import com.nicico.cost.crud.repository.GeneralRepository;

import java.io.Serializable;

/**
 * @version 1.0.1
 * @author nima
 * @param <T> is the entity class that you must Extended to BaseEntity class {@link com.nicico.cost.crud.domain.entity.BaseEntity}
 * @param <I> is the IncrementalId Type Of Relation Data base
 * @apiNote this interface is the implementation of JpaRepository of Spring Data you can find know about it in {@link <a https://spring.io/projects/spring-data-jpa</a>}
 * you must create an interface and extended of it and generate a bean of your interface
 */
public interface JdbcService<T extends BaseEntity<I>, I extends Serializable> extends GeneralRepository<T, I> {
}
