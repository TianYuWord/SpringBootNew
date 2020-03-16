package cn.boot.validatorpro.result;

/**
 * 整个服务器的状态码都在这里
 */
public enum ResultCode {

    SUCCESS(200,"请求成功"),
    SYSTEM_ERROR(500,"服务器异常"),
    ERROR(500,"请求失败");

    private Integer code;
    private String message;

    /**
     * 返回结果值
     * @param code
     * @param message
     */
    ResultCode(Integer code, String message) {
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
