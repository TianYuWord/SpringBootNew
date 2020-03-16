package cn.boot.validatorpro.controller;

import cn.boot.validatorpro.result.ErrorResult;
import cn.boot.validatorpro.result.Result;
import cn.boot.validatorpro.result.ResultCode;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackages = "cn.boot.validatorpro")
public class ResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //正常接口返回，以及全局异常，接口统一格式
        if(o instanceof ErrorResult){
            ErrorResult errorResult = (ErrorResult) o;
            return Result.builder().message(errorResult.getMessage()).status(errorResult.getStatus()).build();
        }else if (o instanceof String){
            return JSON.toJSONString(Result.suc(o));
        }
        return Result.suc(o);
    }
}
