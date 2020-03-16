package cn.boot.validatorpro.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误的结果返回对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static ErrorResult fail(ResultCode resultCode, Throwable throwable,String msg) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setStatus(resultCode.code());
        errorResult.setMessage(msg);
        errorResult.setThrowable(throwable);
        return errorResult;
    }

    public void setResultCode(ResultCode resultCode) {
        this.status = resultCode.code();
        this.message = resultCode.message();
    }
}
