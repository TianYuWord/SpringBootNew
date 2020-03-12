package cn.boot.responsePro3.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {
    /**
     * 异常状态码
     */
    private Integer status;

    /**
     * 用户看得见的异常，例如 用户名重复！！
     */
    private String message;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 异常堆栈信息
     */
    //private String errors;

    /**
     * 对异常提示语进行封装
     */
    public static ErrorResult fail(ResultCode resultCode, Throwable e, String message){
        ErrorResult errorResult = fail(resultCode,e);
        errorResult.setMessage(message);
        return  errorResult;
    }

    /**
     * 对异常枚举进行封装
     */
    public static ErrorResult fail(ResultCode resultCode, Throwable e){
        ErrorResult errorResult = new ErrorResult();
        errorResult.setMessage(resultCode.message());
        errorResult.setStatus(resultCode.code());
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }
}
