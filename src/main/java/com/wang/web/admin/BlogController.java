package com.wang.web.admin;

import com.wang.po.Blog;
import com.wang.po.BlogQuery;
import com.wang.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {



    private final String INPUT =  "/admin/blogs-input";
    private final String REDIRECT = "redirect:/admin/blogs";
    private final String LIST = "/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10,sort = {"updateTime"}) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }


    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "/admin/blogs :: blogList";
    }


    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }



    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;

        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return REDIRECT;
    }

}
