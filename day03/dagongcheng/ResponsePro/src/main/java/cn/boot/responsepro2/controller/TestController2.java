package cn.boot.responsepro2.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常接口测试
 */

@Api(description = "全局异常接口")
@RestController
@RequestMapping("/test2")
public class TestController2 {
    @PostMapping(value = "/error1")
    public void error(){
        //运行时异常
        int i = 9/0;
    }
}