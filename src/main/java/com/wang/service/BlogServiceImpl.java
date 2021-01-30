package com.wang.service;

import com.wang.NotFoundException;
import com.wang.dao.BlogRepository;
import com.wang.po.Blog;
import com.wang.po.BlogQuery;
import com.wang.po.Type;
import com.wang.utils.MarkdownUtils;
import com.wang.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;



    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }




    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"
                            + blogQuery.getTitle() + "%"));
                }
                if (blogQuery.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                if (blogQuery.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }


    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }



    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null){
            try {
                throw new NotFoundException("该博客不存在");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }


    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null) {
            try {
                throw new NotFoundException("博客不存在");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);
        return b;
    }
}
