package cn.boot.zy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 高级程序员异常接口统一
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException{
    private Integer code;
    private String message;
}
