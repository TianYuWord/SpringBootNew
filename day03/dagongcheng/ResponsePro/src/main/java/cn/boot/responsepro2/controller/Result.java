package cn.boot.responsepro2.controller;

import cn.boot.responsepro2.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result <T> {

    /**
     * 1,status 状态值：代表本次请求response的状态结果。
     */
    private Integer status;

    /**
     * 2,response描述：对本次状态码的描述。
     */
    private String desc;

    /**
     * 3,data数据：本次返回的数据。
     */
    private T data;

    /**
     * 成功，创建ResResult：没data数据
     * @return
     */
    public static cn.boot.responsepro2.result.Result suc(){
       cn.boot.responsepro2.result.Result result = new cn.boot.responsepro2.result.Result();
       result.setResultCode(cn.boot.responsepro2.result.ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     * @param data
     * @return
     */
    public static cn.boot.responsepro2.result.Result suc(Object data){
        cn.boot.responsepro2.result.Result result = new cn.boot.responsepro2.result.Result();
        //优雅的枚举，在任何时候都返回的是它本身，这就是枚举的魅力
        result.setResultCode(cn.boot.responsepro2.result.ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败指定status，desc
     * @param status
     * @param desc
     * @return
     */
    public static cn.boot.responsepro2.result.Result fail(Integer status, String desc){
        cn.boot.responsepro2.result.Result result = new cn.boot.responsepro2.result.Result();
        result.setStatus(status);
        result.setDesc(desc);
        return result;
    }

    /**
     * 失败，指定ResultCode枚举
     * @param resultCode
     * @return
     */
    public static cn.boot.responsepro2.result.Result fail(cn.boot.responsepro2.result.ResultCode resultCode){
        cn.boot.responsepro2.result.Result result = new cn.boot.responsepro2.result.Result();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 把ResultCode枚举转化为ResResult
     * @param code
     */
    private void setResultCode(ResultCode code){
        this.status = code.code();
        this.desc = code.message();
    }
}
