package com.nicico.cost.jdbcclient.repository.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import com.nicico.cost.framework.packages.crud.view.Criteria;
import com.nicico.cost.framework.packages.crud.view.Operator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory<T> {

    private Map<Integer, Function<Criteria, Specification<T>>> specs;

    @PostConstruct
    private void init() {
        specs = new HashMap<>();
        specs.put(Operator.EQUALS.ordinal(), this::getEqualsSpecification);
        specs.put(Operator.GREATER_THAN.ordinal(), this::getGreaterThanSpecification);
        specs.put(Operator.LESS_THAN.ordinal(), this::getLessThanSpecification);
    }

    public Specification<T> getByCriteria(Criteria criteria) {
        return specs.get(criteria.getOperator().ordinal()).apply(criteria);
    }

    private Specification<T> getEqualsSpecification(Criteria criteria) {
        return (root, query, builder) ->
             builder.equal(root.get(criteria.getFieldName()), criteria.getValue());

    }

    private Specification<T> getGreaterThanSpecification(Criteria criteria) {
        return (root, query, builder) ->
             builder
                    .greaterThan(root.<String> get(criteria.getFieldName()), criteria.getValue().toString());
    }

    private Specification<T> getLessThanSpecification(Criteria criteria) {
        return (root, query, builder) ->
             builder
                    .lessThan(root.<String> get(criteria.getFieldName()), criteria.getValue().toString());
    }
}

