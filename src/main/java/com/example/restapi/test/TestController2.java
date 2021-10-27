package com.example.restapi.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController2 {

    @GetMapping("/test")
    public String test(){
        System.out.println("TEST");
        return "test";
    }
}
