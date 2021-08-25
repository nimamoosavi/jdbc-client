package app.ladderproject.jdbcclient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T> is the entity class
 * @param <I> is the type of database Identity class such as Long,String, ...
 * @apiNote this methode used for create and get Parent child Data Table, but you must know that your name of parent filed in Entity class must be parentId
 */
public interface TreeJdbcRepository<T, I extends Serializable> extends JdbcRepository<T, I> {

    /**
     *
     * @param id is the id of parent for search in data Table
     * @return List<T> is the Child Of ParentId
     */
    List<T> findAllByParentId(I id);

    /**
     *
     * @param id is the id of parent for search in data Table
     * @param pageable is the Pageable of spring class
     * @return Page<T> is the Child Of ParentId
     */
    Page<T> findAllByParentId(I id, Pageable pageable);

    /**
     *
     * @return List<T> is the list of all root parent
     */
    List<T> findAllByParentIdIsNull();

    /**
     * @param pageable is the Pageable of spring class
     * @return List<T> is the list of all root parent
     */
    Page<T> findAllByParentIdIsNull(Pageable pageable);
}
