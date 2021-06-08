package com.nicico.cost.jdbcclient.repository;

import com.nicico.cost.framework.packages.jdbc.base.BaseObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @param <T> is the entity class that you must Extended to BaseEntity class {@link BaseObject}
 * @param <I> is the type of data base Identity class such as Long,String, ...
 * @apiNote this interface is the implementation of JpaRepository of Spring Data you can find know about it in {@link <a https://spring.io/projects/spring-data-jpa</a>}
 * you must create an interface and extended of it and generate a bean of your interface and use all method that Spring Data implement it And then you Access the
 * Spring Data Query Method and Used It in your Interface
 * Good Luck
 */
public interface JdbcRepository<T extends BaseObject<I>, I extends Serializable> extends JpaRepository<T, I> {
}
