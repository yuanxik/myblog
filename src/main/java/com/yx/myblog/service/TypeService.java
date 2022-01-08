package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    //分页
    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type  newType);

    void deleteType(Long id);

    Type getTypeByName(String name);

    //返回所有type
    List<Type> listType();


    List<Type> listType(Integer size);
}
