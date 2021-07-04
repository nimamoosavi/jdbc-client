package com.nicico.cost.jdbcclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * @param <T> is the entity class
 * @param <I> is the type of data base Identity class such as Long,String, ...
 * @apiNote this interface is the implementation of JpaRepository of Spring Data you can find know about it in {@link <a https://spring.io/projects/spring-data-jpa</a>}
 * you must create an interface and extended of it and generate a bean of your interface and use all method that Spring Data implement it And then you Access the
 * Spring Data Query Method and Used It in your Interface
 * Good Luck
 */
public interface JdbcRepository<T, I extends Serializable> extends JpaRepository<T, I> , JpaSpecificationExecutor<T> {
}
