package app.ladderproject.jdbcclient.repository.filter;

import app.ladderproject.core.packages.crud.view.Criteria;
import org.springframework.data.jpa.domain.Specification;

/**
 * @param <T> is the type of Object that return in specification
 * @author bagher moosavi
 */
public interface SpecificationFactory <T> {

    /**
     *
     * @param criteria is the input object that you need to convert to Specification Object
     * @return Specification<T> for use in jpa Query
     * @apiNote this method used for convert criteria to specification of Jpa that you can used in All library
     */
    Specification<T> getByCriteria(Criteria criteria);
}
