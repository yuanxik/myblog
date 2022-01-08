package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.dao.TypeRepository;
import com.yx.myblog.exception.NotFoundException;
import com.yx.myblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeRepository typeRepository;

    //增删改查应该放到事务中
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type newType) {

        Type t = typeRepository.findById(id).get();
        if (t==null){
            throw new NotFoundException("分类不存在");
        }
        BeanUtils.copyProperties(newType,t);
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        List<Type> all = typeRepository.findAll();
        return all;
    }

    @Override
    public List<Type> listType(Integer size) {
        Sort sort= Sort.by(Sort.Direction.DESC,"blogs.size");

        Pageable pageable =  PageRequest.of(0, size,sort);
        return typeRepository.findTop(pageable);
    }
}
