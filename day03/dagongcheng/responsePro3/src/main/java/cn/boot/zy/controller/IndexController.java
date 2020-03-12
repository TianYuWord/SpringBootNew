package cn.boot.zy.controller;

import cn.boot.zy.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "作业")
@RestController()
public class IndexController {

    @ApiOperation("字符串返回值")
    @GetMapping("/ok")
    public String ok(){
        return "od";
    }


    @ApiOperation("返回对象")
    @GetMapping("/Ouser")
    public User Ouser(){
        User user = new User();
        user.setAge(18);
        user.setUsername("xiaoyu");
        user.setPassword("123456");

        return user;
    }

    @ApiOperation("error")
    @GetMapping("/error")
    public void error(){};

    @ApiOperation("error1 -- 运行时异常")
    @GetMapping("/error1")
    public void error1(){
        throw new RuntimeException();
    };

    @ApiOperation("error2 -- 直接抛出异常结果")
    @GetMapping("/error2")
    public Object error2(){
        return new Error();
    };
}
