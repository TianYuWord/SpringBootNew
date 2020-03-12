package cn.boot.responsepro2.controller;

import cn.boot.responsepro2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(description = "用户接口")
@RequestMapping("/user")
@RestController
@Slf4j
public class IndexController1 {

    //什么也不返回，接收Post
    @PostMapping(value = "/user/create")
    public void createUser(@RequestBody @Validated User user){
        log.debug(user.toString());
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

    @ApiOperation("获得字符接口")
    @GetMapping("/string")
    public String getString(){
        return "test";
    }

    @GetMapping("/empty")
    @ApiOperation("empty")
    public void empty(){
    }

    @GetMapping("/error")
    @ApiOperation("error")
    public void error(){
        int i = 9 / 0;
    }

    @GetMapping("/error1")
    @ApiOperation("error1")
    public String error1(){
        try {
            int i = 9 / 0;
        }catch (Exception ex){
            log.error("error");
            return "no";
        }
        return "success:ok";
    }
}
