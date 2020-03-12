package cn.boot.responsepro2.advice;

import cn.boot.responsepro2.result.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 高级程序员不会在Controller类里面进行配置，应为那样的配置方法每一次写接口都要写一边Result返回对象
 * 而现在的高级程序员可以使用SpringBoot自带的一个responseBodyAdvice来进行配置，应为每个接口返回的时候
 * 都会检测整个类，这样就可以在每一个response返回之前在这里统一进行接口的封装
 */
@ControllerAdvice(basePackages = "cn.boot.responsepro")
@Slf4j
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     * true=支持，false=不支持
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理response的具体业务方法
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /*if(o instanceof ErrorResule){
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
        }else */
        if(o instanceof Error){

        }
        log.info("----走了ControllerAdvice---");
        if(o instanceof String){
            return JSON.toJSONString(Result.suc(o));
        }
        return Result.suc(o);
    }
}
