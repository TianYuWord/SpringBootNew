package cn.tianyu20.modlue1.modlue1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Value("${xiaoyu.msg}")
    private String msg;

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }

    @GetMapping("/index.html")
    public String index(){
        return "hello Spring";
    }

}