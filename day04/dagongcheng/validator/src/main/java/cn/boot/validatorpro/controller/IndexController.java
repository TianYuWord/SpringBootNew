package cn.boot.validatorpro.controller;

import cn.boot.validatorpro.model.UserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * validator 参数校验器 Controller
 */
@Api(description = "validator测试")
@RequestMapping("user")
@RestController
@Slf4j
public class IndexController {


    @GetMapping("/register")
    public String index(){
        return null;
    }

    @PostMapping(value = "/user/update")
    public void updateUser(@RequestBody @Validated UserVO userVO){
        UserVO userVO1 = null;
        Assert.notNull(userVO,"用户不存在！");
    }

}
