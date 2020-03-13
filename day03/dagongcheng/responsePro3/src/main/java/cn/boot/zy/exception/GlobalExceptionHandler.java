package cn.boot.zy.exception;

import cn.boot.zy.result.ErrorResult;
import cn.boot.zy.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截运行时异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    private ErrorResult SystemErrorHandler(Throwable e, HttpServletRequest request){
        ErrorResult fail = ErrorResult.fail(e, ResultCode.ERROR);
        log.error(fail.toString());
        return fail;
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(BusinessException.class)
    private ErrorResult GlobalExceptionHandler(BusinessException e,HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder()
                .message(e.getMessage())
                .status(e.getCode())
                .exception(e.getClass().getName())
                .build();
        log.error(errorResult.toString() + "---- BusinessException");
        return errorResult;
    }

    /**
     * 自定义空指针异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NullException.class)
    private ErrorResult NullException(NullException e,HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .exception(e.getClass().getName())
                .build();
        log.error(errorResult.toString() + "---- NullException");
        return errorResult;
    }
}
