package cn.tianyu20.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${tianyu.msg}")
    private String msg;

    @GetMapping("message")
    public String getMsg() {
        return msg;
    }

    @GetMapping("/hello")
    public String hello (){
        return "Hello Spring Boot";
    }
}
