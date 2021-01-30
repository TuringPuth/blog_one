package com.wang.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(){
        return "/about";
    }
}
