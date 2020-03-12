package cn.boot.responsepro2.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 全局异常接口测试类
 */
public class ErrorTestController {

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
}
