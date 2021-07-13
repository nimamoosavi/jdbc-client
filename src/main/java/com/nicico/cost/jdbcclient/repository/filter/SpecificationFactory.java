package com.nicico.cost.jdbcclient.repository.filter;

import com.nicico.cost.framework.packages.crud.view.Criteria;
import com.nicico.cost.framework.packages.crud.view.Operator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.Join;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class SpecificationFactory<T> {
    private Map<Integer, Function<Criteria, Specification<T>>> specs;
    @PostConstruct
    private void init() {
        specs = new HashMap<>();
        specs.put(Operator.EQUALS.ordinal(), this::getEqualsSpecification);
        specs.put(Operator.NOT_EQUALS.ordinal(), this::getNotEqualsSpecification);
        specs.put(Operator.GREATER_THAN.ordinal(), this::getGreaterThanSpecification);
        specs.put(Operator.LESS_THAN.ordinal(), this::getLessThanSpecification);
        specs.put(Operator.GREATER_OR_EQUALS.ordinal(), this::getGreaterOrEqualsSpecification);
        specs.put(Operator.LESS_OR_EQUALS.ordinal(), this::getLessOrEqualsSpecification);
        specs.put(Operator.CONTAINS.ordinal(), this::getContainsSpecification);
        specs.put(Operator.START_WITH.ordinal(), this::getStartWithSpecification);
        specs.put(Operator.END_WITH.ordinal(), this::getEndWithSpecification);
        specs.put(Operator.NOT_CONTAIN.ordinal(), this::getNotContainsSpecification);
        specs.put(Operator.NOT_START_WITH.ordinal(), this::getNotStartWithSpecification);
        specs.put(Operator.NOT_END_WITH.ordinal(), this::getNotEndWithSpecification);
        specs.put(Operator.BLANK.ordinal(), this::getBlankSpecification);
        specs.put(Operator.NOT_BLANK.ordinal(), this::getNotBlankSpecification);
        specs.put(Operator.NULL.ordinal(), this::getNullSpecification);
        specs.put(Operator.NOT_NULL.ordinal(), this::getNotNullSpecification);
    }

    public Specification<T> getByCriteria(Criteria criteria) {
        return specs.get(criteria.getOperator().ordinal()).apply(criteria);
    }

    private Specification<T> getEqualsSpecification(Criteria criteria) {
        return (root, query, builder) -> {
            if(criteria.getFieldName().contains(".")){
                String[] arrStr = criteria.getFieldName().split("\\.");
                Join join = root.join(arrStr[0]);
                return builder.equal(join.get(arrStr[1]),criteria.getValue());
            }
            return builder.equal(root.get(criteria.getFieldName()), criteria.getValue());
        };
    }

    private Specification<T> getNotEqualsSpecification(Criteria criteria) {
        return (root, query, builder) -> {
            if(criteria.getFieldName().contains(".")){
                String[] arrStr = criteria.getFieldName().split("\\.");
                Join join = root.join(arrStr[0]);
                return builder.notEqual(join.get(arrStr[1]),criteria.getValue());
            }
           return builder.notEqual(root.get(criteria.getFieldName()), criteria.getValue());
        };
    }
    private Specification<T> getLessThanSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.lessThan(root.get(criteria.getFieldName()), (Comparable<Object>) criteria.getValue());
    }
    private Specification<T> getGreaterThanSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.greaterThan(root.get(criteria.getFieldName()), (Comparable<Object>) criteria.getValue());
    }
    private Specification<T> getGreaterOrEqualsSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get(criteria.getFieldName()), (Comparable<Object>) criteria.getValue());
    }
    private Specification<T> getLessOrEqualsSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get(criteria.getFieldName()), (Comparable<Object>) criteria.getValue());
    }
    private Specification<T> getContainsSpecification(Criteria criteria) {
        return (root, query, builder) ->
                root.get(criteria.getFieldName()).in(criteria.getValue());
    }
    private Specification<T> getStartWithSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.like(root.get(criteria.getFieldName()),  criteria.getValue()+"%");
    }
    private Specification<T> getEndWithSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.like(root.get(criteria.getFieldName()),  "%"+criteria.getValue());
    }
    private Specification<T> getNotContainsSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.notLike(root.get(criteria.getFieldName()), "%"+criteria.getValue()+"%");
    }
    private Specification<T> getNotStartWithSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.notLike(root.get(criteria.getFieldName()), "%"+ criteria.getValue());
    }
    private Specification<T> getNotEndWithSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.notLike(root.get(criteria.getFieldName()), criteria.getValue()+"%");
    }
    private Specification<T> getBlankSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.equal(root.get(criteria.getFieldName()), "");
    }
    private Specification<T> getNotBlankSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.notEqual(root.get(criteria.getFieldName()), "");
    }
    private Specification<T> getNullSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.isNull(root.get(criteria.getFieldName()));
    }
    private Specification<T> getNotNullSpecification(Criteria criteria) {
        return (root, query, builder) ->
                builder.isNotNull(root.get(criteria.getFieldName()));
    }
}

