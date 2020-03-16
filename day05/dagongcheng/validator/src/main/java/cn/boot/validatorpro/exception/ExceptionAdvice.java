package cn.boot.validatorpro.exception;


import cn.boot.validatorpro.result.ErrorResult;
import cn.boot.validatorpro.result.Result;
import cn.boot.validatorpro.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//《全局异常处理器》

/**
 * 已知的下一站是，ResponseBodyAdvice
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * 处理运行时异常
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        //运行时异常，可以在在这里记录，用于发异常邮件通知
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR,e);
        log.error("URL:{},系统异常：",request.getRequestURI(),e);
        return errorResult;
    }*/

    /**
     * 将validator的异常整合到《全局异常处理器》
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletRequest request){
        String msgs = this.handle(e.getBindingResult().getFieldErrors());
        ErrorResult error = ErrorResult.fail(ResultCode.ERROR,e,msgs);
        log.warn("URL:{} , 参数校验器异常:{} ",request.getRequestURI(),msgs);
        return error;
    }


    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for(FieldError fieldError:fieldErrors){
            sb.append(fieldError.getField());
            sb.append("=[");
            sb.append(fieldError.getDefaultMessage());
            sb.append("]  ");
        }
        return sb.toString();
    }

    /**
     * Assert 的异常统一封装
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentException(IllegalArgumentException e,HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().message(e.getMessage()).status(4000).throwable(e).build();
        log.warn("URL:{} ,业务校验异常：{}",request.getRequestURI(),e);
        return errorResult;
    }
}
