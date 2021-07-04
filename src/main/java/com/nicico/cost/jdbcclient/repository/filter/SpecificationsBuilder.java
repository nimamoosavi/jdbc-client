package com.nicico.cost.jdbcclient.repository.filter;

import com.nicico.cost.framework.packages.crud.view.Criteria;
import com.nicico.cost.framework.packages.crud.view.Operator;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SpecificationsBuilder<T> {
    private final  SpecificationFactory<T> specificationFactory;


    public Specification<T> build(Criteria criteria) {
        if (criteria.getCriteriaChain() != null && !criteria.getCriteriaChain().isEmpty()) {
            Specification firstCriteria = build(criteria.getCriteriaChain().get(0));
            for(int i=1;i<criteria.getCriteriaChain().size();i++)
            {
                if(criteria.getOperator().equals(Operator.AND))
                    firstCriteria = Specification.where(firstCriteria).and(build(criteria.getCriteriaChain().get(i)));
                else
                    firstCriteria = Specification.where(firstCriteria).or(build(criteria.getCriteriaChain().get(i)));
            }
            return firstCriteria;
        } else {
            return specificationFactory.getByCriteria(criteria);
        }
    }
}
