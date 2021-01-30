package com.wang.service;

import com.wang.po.Comment;

import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

}
