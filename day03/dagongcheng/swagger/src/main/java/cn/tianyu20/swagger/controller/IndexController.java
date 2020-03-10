package cn.tianyu20.swagger.controller;


import cn.tianyu20.swagger.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "user")
@RestController
@RequestMapping("/user")
public class IndexController {

    @ApiOperation("修改某条数据")
    @GetMapping(value = "/u/{id}")
    public User findById(@PathVariable int id){
        Random rand = new Random();
        User user = new User();
        user.setId(id);
        user.setAge(18);
        user.setName("admin");
        user.setPrice(200.00);
        int n = new Random().nextInt(2);
        user.setSex((byte) n);
        return user;
    }

    @ApiOperation("单个用户查询，按userid查用户信息")
    @PostMapping(value = "/user/create")
    public User createUser(@RequestBody User user){return user;}
}
