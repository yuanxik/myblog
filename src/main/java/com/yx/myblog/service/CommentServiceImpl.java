package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.dao.CommentRepository;
import com.yx.myblog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> byBlogId = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        //处理查询到的评论子父级集合
        List<Comment> comments = eachComment(byBlogId);
        return comments;
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long id = comment.getParentComment().getId();
        if (id!=-1){
            comment.setParentComment(commentRepository.findById(id).get());
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    /*
    循环每个评论节点复制到新的集合中，在不改变数据库的情况下处理数据
     */
    public List<Comment> eachComment(List<Comment> comments){
        ArrayList<Comment> commentsView = new ArrayList<>();
        for (Comment comment:comments
             ) {
            Comment comm = new Comment();
            BeanUtils.copyProperties(comment,comm);
            commentsView.add(comm);
        }
        //合并评论的各层子评论到第一级集合中
        combineChildren(commentsView);
        return commentsView;
    }


    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys=new ArrayList<>();
    /*

     */
    private void combineChildren(ArrayList<Comment> commentsView) {
        for (Comment comment:commentsView
             ) {
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment replyComment:replyComments){
                //循环迭代，找出子评论，存放在tempReplys中
                recursively(replyComment);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys=new ArrayList<>();
        }
    }
    /*
    迭代：找出replyComment的所有子评论，和子评论的所有子评论...存放再tempReplys中
     */
    private void recursively(Comment replyComment) {
        tempReplys.add(replyComment);
        if (replyComment.getReplyComments().size()>0){
            List<Comment> replyComments = replyComment.getReplyComments();
            for (Comment replyComm:replyComments){
                tempReplys.add(replyComm);
                if (replyComm.getReplyComments().size()>0){
                    recursively(replyComm);
                }
            }
        }
    }

}
