package cn.boot.responsepro2.controller;

import cn.boot.responsepro2.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 为了测试初级程序员对接口统一的Controller类
 */

@Api(description = "测试初级程序员接口统一")
@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation("初级程序员测试接口")
    @GetMapping(value = "/test")
    public Result getResult(){
        System.out.println(Result.suc("test"));
        return Result.suc("test");
    }
}