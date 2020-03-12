package cn.boot.zy.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Result<T> {

    private Integer status;

    private String desc;

    private T data;

    /**
     * 不含数据的返回方式
     * @param resultCode
     * @return
     */
    public static Result suc(Object o,ResultCode resultCode){
        Result result = new Result();
        result.desc = resultCode.message();
        result.status = resultCode.code();
        result.setData(o);
        return result;
    }

    /**
     * 含数据的返回方式
     * @param e
     * @return
     */
    public static Result suc(Object e){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.data = e;
        return result;
    }

    public static Result fail(ResultCode resultCode){
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public void setResultCode(ResultCode resultCode){
        this.desc = resultCode.message();
        this.status = resultCode.code();
    }
}
