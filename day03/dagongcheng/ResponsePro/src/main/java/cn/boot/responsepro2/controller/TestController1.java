package cn.boot.responsepro2.controller;

import cn.boot.responsepro2.result.Result;
import cn.boot.responsepro2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 为了测试高级程序员对接口统一的Controller类
 */

@Api(description = "测试高级程序员接口统一")
@RestController
@RequestMapping("/test1")
public class TestController1 {
    @ApiOperation("高级程序员测试接口")
    @GetMapping(value = "/test")
    public String getResult(){
        System.out.println(Result.suc("test"));
        return "test";
    }

    //返回对象
    @GetMapping("/user/{id}")
    @ApiOperation("通过id获得用户信息")
    public User getOUser(@PathVariable("id") Integer id){
        User user = new User();
        user.setUsername("temp");
        user.setPassword("123456");
        user.setId(id);
        return user;
    }
}