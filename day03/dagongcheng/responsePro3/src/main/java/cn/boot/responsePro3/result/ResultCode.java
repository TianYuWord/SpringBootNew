package cn.boot.responsePro3.result;

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0,"成功"),

    /* 系统500错误 */
    SYSTEM_ERROR(1000,"系统异常，请稍候重试"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001,"参数无效"),

    /*  用户错误：20001-29999 */
    USER_MAS_EXISTED(20001,"用户名已存在"),

    USER_NOT_FIND(20002,"用户名不存在");

    private Integer code;

    private String message;

    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }
}