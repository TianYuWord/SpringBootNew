package cn.boot.validatorpro.result;

import lombok.Data;

/**
 * 错误的结果返回对象
 */
@Data
public class ErrorResult {

    private Integer status;
    private String message;
    private Throwable throwable;

    public static ErrorResult fail(ResultCode resultCode, Throwable throwable) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setResultCode(resultCode);
        errorResult.setThrowable(throwable);
        return errorResult;
    }

    public void setResultCode(ResultCode resultCode) {
        this.status = resultCode.code();
        this.message = resultCode.message();
    }
}
