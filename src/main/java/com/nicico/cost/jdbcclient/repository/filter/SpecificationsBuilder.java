package com.nicico.cost.jdbcclient.repository.filter;

import com.nicico.cost.framework.packages.crud.view.Criteria;
import com.nicico.cost.framework.packages.crud.view.Operator;
import com.nicico.cost.framework.service.exception.ApplicationException;
import com.nicico.cost.framework.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.Objects;
import static com.nicico.cost.framework.enums.exception.ExceptionEnum.NOT_SAVE;


@Component
@AllArgsConstructor
public class SpecificationsBuilder<T> {
    private final SpecificationFactory<T> specificationFactory;
    private final ApplicationException<ServiceException> applicationException;

    public Specification<T> build(Criteria criteria) {
        if (criteria.getCriteriaChain() != null && !criteria.getCriteriaChain().isEmpty()) {
            Specification<T> firstCriteria = Specification.where(build(criteria.getCriteriaChain().get(0)));

            for (int i = 1; i < criteria.getCriteriaChain().size(); i++) {
                if (Objects.isNull(firstCriteria))
                    throw applicationException.createApplicationException(NOT_SAVE, HttpStatus.BAD_REQUEST);
                if (criteria.getOperator().equals(Operator.AND))
                    firstCriteria = firstCriteria.and(build(criteria.getCriteriaChain().get(i)));
                else
                    firstCriteria = firstCriteria.or(build(criteria.getCriteriaChain().get(i)));
            }
            return firstCriteria;
        } else {
            return specificationFactory.getByCriteria(criteria);
        }
    }
}
