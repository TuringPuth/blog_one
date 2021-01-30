package com.wang.web;


import com.wang.service.BlogService;
import com.wang.service.TagService;
import com.wang.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Controller
public class IndexController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "/index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {

        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query,Model model){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }







}
