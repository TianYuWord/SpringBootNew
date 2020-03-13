package cn.boot.zy.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {
    private Integer status;
    private String message;
    private String exception;


    /**
     * 临时渲染报错信息
     * @param e
     * @param resultCode
     * @param message
     * @return
     */
    public static ErrorResult fail(Throwable e,ResultCode resultCode,String message){
        ErrorResult errorResult = fail(e,resultCode);
        errorResult.setMessage(message);
        return errorResult;
    }

    /**
     * 按照原有的枚举类来进行数据渲染
     * @param e
     * @param resultCode
     * @return
     */
    public static ErrorResult fail(Throwable e,ResultCode resultCode){
        ErrorResult errorResult = new ErrorResult();
        errorResult.setResultCode(resultCode);
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }

    public void setResultCode(ResultCode resultCode){
        this.status = resultCode.code();
        this.message = resultCode.message();
    }
}
