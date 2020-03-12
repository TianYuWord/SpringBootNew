package cn.boot.zy.result;

/**
 * 该类只是用于存储一下状态码
 */
public enum ResultCode {
    /*响应成功时*/
    SUCCESS(200,"成功了"),

    /*响应失败时*/
    ERROR(500,"失败了");

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
