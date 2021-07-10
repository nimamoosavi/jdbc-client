package com.nicico.cost.jdbcclient.service.impl;

import com.nicico.cost.framework.domain.dto.PageDTO;
import com.nicico.cost.framework.packages.crud.view.Sort;
import com.nicico.cost.jdbcclient.repository.TreeJdbcRepository;
import com.nicico.cost.jdbcclient.service.TreeJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public abstract class TreeJdbcServiceImpl<T, I extends Serializable> extends JdbcServiceImpl<T, I> implements TreeJdbcService<T, I> {


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
    public List<T> findAllParent(List<Sort> orders) {
        org.springframework.data.domain.Sort sort = sort(orders);
        return treeJdbcRepository.findAllByParentIdIsNull(sort);
    }

    @Override
    public PageDTO<List<T>> findAllParent(int page, int pageSize, List<Sort> orders) {
        Pageable pagination = pagination(page, pageSize, orders);
        Page<T> allByParentIdIsNull = treeJdbcRepository.findAllByParentIdIsNull(pagination);
        return convertToPageDTO(allByParentIdIsNull);
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
    public PageDTO<List<T>> findAll(int page, int pageSize, List<Sort> orders, I pid) {
        Pageable pageable = pagination(page, pageSize, orders);
        Page<T> allByParentId = treeJdbcRepository.findAllByParentId(pid, pageable);
        return convertToPageDTO(allByParentId);
    }


    private PageDTO<List<T>> convertToPageDTO(Page<T> page){
        return PageDTO.<List<T>>builder().object(page.toList()).pageSize(page.getSize()).totalElement(page.getTotalElements()).totalPages(page.getTotalPages()).build();
    }
}
