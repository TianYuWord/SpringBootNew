package cn.boot.validatorpro.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  返回的数据最终都会走这里返回
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result <T> {

    //这里的参数都是客户端所看到的参数
    private Integer status;
    private String message;
    private T data;

    /**
     * 无参数的返回结果
     * @return
     */
    public static Result suc() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 有参数的返回结果
     * @param o
     * @return
     */
    public static Result suc(Object o) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        //直接将对象设置到了泛型里面
        result.setData(o);
        return result;
    }

    /**
     * 自定义的 无参数 方式指定
     * @return
     */
    public static Result fail(Integer status,String message){
        Result result = new Result();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }
    /**
     * 自定义的 无参数 方式指定
     * @return
     */
    public static Result fail(ResultCode resultCode){
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public void setResultCode(ResultCode resultCode){
        this.status = resultCode.code();
        this.message = resultCode.message();
    }
}
