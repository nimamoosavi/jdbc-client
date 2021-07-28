package com.webold.jdbcclient.service.impl;

import com.webold.framework.domain.dto.PageDTO;
import com.webold.framework.packages.crud.view.Criteria;
import com.webold.framework.packages.crud.view.Operator;
import com.webold.framework.packages.crud.view.Query;
import com.webold.jdbcclient.repository.TreeJdbcRepository;
import com.webold.jdbcclient.service.TreeJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

public abstract class TreeJdbcServiceImpl<T, I extends Serializable> extends JdbcServiceImpl<T, I> implements TreeJdbcService<T, I> {

    public static final String PARENT_ID = "parentId";

    @Autowired
    private TreeJdbcRepository<T, I> treeJdbcRepository;


    @Override
    public List<T> findAll(I pid) {
        return treeJdbcRepository.findAllByParentId(pid);
    }

    @Override
    public List<T> findAllParent() {
        return treeJdbcRepository.findAllByParentIdIsNull();
    }

    @Override
    public List<T> findAllParent(Query query) {
        if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty()) && query.getCriteria() != null) {
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentIdCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            org.springframework.data.domain.Sort sort = sort(query.getSorts());
            return treeJdbcRepository.findAll(specification, sort);
        } else if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty())) {
            org.springframework.data.domain.Sort sort = sort(query.getSorts());
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Specification<T> specification = specificationsBuilder.build(parentIdCriteria);
            return treeJdbcRepository.findAll(specification, sort);
        } else if (query.getCriteria() != null) {
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentIdCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            return treeJdbcRepository.findAll(specification);
        } else
            return treeJdbcRepository.findAllByParentIdIsNull();
    }

    @Override
    public PageDTO<List<T>> findAllParent(int page, int pageSize, Query query) {
        if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty()) && query.getCriteria() != null) {
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentIdCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Page<T> all = treeJdbcRepository.findAll(specification, pageable);
            return convertPageToPageDTO(all);
        } else if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty())) {
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Specification<T> specification = specificationsBuilder.build(parentIdCriteria);
            Page<T> all = treeJdbcRepository.findAll(specification, pageable);
            return convertPageToPageDTO(all);
        } else if (query.getCriteria() != null) {
            Criteria parentIdCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentIdCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = treeJdbcRepository.findAll(specification, pagination);
            return convertPageToPageDTO(all);
        } else {
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = treeJdbcRepository.findAllByParentIdIsNull(pagination);
            return convertPageToPageDTO(all);
        }
    }

    @Override
    public PageDTO<List<T>> findAllParent(int page, int pageSize) {
        Pageable pagination = pagination(page, pageSize);
        Page<T> allByParentIdIsNull = treeJdbcRepository.findAllByParentIdIsNull(pagination);
        return convertToPageDTO(allByParentIdIsNull);
    }

    @Override
    public PageDTO<List<T>> findAll(int page, int pageSize, I pid) {
        Pageable pageable = pagination(page, pageSize);
        Page<T> allByParentId = treeJdbcRepository.findAllByParentId(pid, pageable);
        return convertToPageDTO(allByParentId);
    }

    @Override
    public PageDTO<List<T>> findAll(int page, int pageSize, Query query, I pid) {
        if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty()) && query.getCriteria() != null) {
            Criteria parentCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Page<T> all = treeJdbcRepository.findAll(specification, pageable);
            return convertPageToPageDTO(all);
        } else if (query.getSorts() != null && Boolean.FALSE.equals(query.getSorts().isEmpty())) {
            Pageable pageable = pagination(page, pageSize, query.getSorts());
            Page<T> all = treeJdbcRepository.findAllByParentId(pid, pageable);
            return convertPageToPageDTO(all);
        } else if (query.getCriteria() != null) {
            Criteria parentCriteria = Criteria.builder().fieldName(PARENT_ID).operator(Operator.NULL).build();
            Criteria combineCriteria = Criteria.combineCriteria(query.getCriteria(), parentCriteria, Operator.AND);
            Specification<T> specification = specificationsBuilder.build(combineCriteria);
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = treeJdbcRepository.findAll(specification, pagination);
            return convertPageToPageDTO(all);
        } else {
            Pageable pagination = pagination(page, pageSize);
            Page<T> all = treeJdbcRepository.findAllByParentId(pid, pagination);
            return convertPageToPageDTO(all);
        }
    }


    private PageDTO<List<T>> convertToPageDTO(Page<T> page) {
        return PageDTO.<List<T>>builder().object(page.toList()).pageSize(page.getSize()).totalElement(page.getTotalElements()).totalPages(page.getTotalPages()).build();
    }
}
