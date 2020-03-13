package cn.boot.responsePro3.controller;

import cn.boot.responsePro3.exception.BusinessException;
import cn.boot.responsePro3.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试自定义全局异常类
 */
@Api(description = "自定义全局异常处理接口")
@RestController
@RequestMapping("/test3x")
public class TestController3x {

    @ApiOperation("抛出自定义异常")
    @PostMapping(value = "/error")
    public void error3(){
        throw new BusinessException(ResultCode.USER_MAS_EXISTED);
    }

}
