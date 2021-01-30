package com.wang.dao;

import com.wang.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
