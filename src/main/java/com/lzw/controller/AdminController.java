package com.lzw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 跳转登陆页面
    @RequestMapping("index.page")
    public ModelAndView index(){
        return new ModelAndView("admin.jsp");
    }



}
