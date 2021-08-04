package app.ladderproject.jdbcclient.repository.filter;

import app.ladderproject.core.packages.crud.view.Criteria;
import org.springframework.data.jpa.domain.Specification;

/**
 * @param <T> is the type of Object that return in specification
 * @author bagher moosavi
 */
public interface SpecificationBuilder<T> {

    /**
     * @param criteria is the input object that you need to convert to Specification Object
     * @return Specification<T> for use in jpa Query
     */
    Specification<T> build(Criteria criteria);
}
