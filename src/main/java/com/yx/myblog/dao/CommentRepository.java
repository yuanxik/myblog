package com.yx.myblog.dao;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //根据id查询父评论为空的评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);


}
