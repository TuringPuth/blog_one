package com.wang.service;

import com.wang.po.Blog;
import com.wang.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Blog getAndConvert(Long id);




}
