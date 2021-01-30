package com.wang.service;

import com.wang.dao.CommentRepository;
import com.wang.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    // 存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();



    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

    private void combinChildren(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> relys1 = comment.getReplyComments();
            for (Comment rely1 : relys1) {
                // 循环迭代，找出子代，存放在tempReplys中
                recursively(rely1);
            }
            // 修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            // 清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            // 将comment拷贝到c中
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        // 合并评论的各层子代到第一级子代集合中
        combinChildren(commentView);
        return commentView;
    }




    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        System.out.println("开始");
        Long parentCommentId = comment.getParentComment().getId();
        System.out.println("结束" + parentCommentId);
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
