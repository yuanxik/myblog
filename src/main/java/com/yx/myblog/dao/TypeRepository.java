package com.yx.myblog.dao;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from Type t")   //自定义查询
    List<Type> findTop(Pageable pageable);
}
