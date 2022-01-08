package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
