package cn.boot.validatorpro.controller;

import cn.boot.validatorpro.model.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        Assert.notNull(userVO1,"用户不存在！");//message会在全局异常被捕获
    }

    /**
     * 作业：体验 Assert 的 isTrue() 和 notEmpty() 方法
     */
    @ApiOperation("作业")
    @PostMapping(value = "/zy")
    public void zy(@RequestBody @Validated UserVO userVO){
        Assert.isTrue(userVO.isVip(),"错误，非vip参数请求");
        Assert.notEmpty(userVO.getSkill(),"错误，必须有一个以上的技能");
    }

}
