package com.lj.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
//   access to the main page :(ip:port/web name) must be omitted
    @RequestMapping("/")
     public String index(){
//        Forward requests! not redirect
         return "index";
     }
}
