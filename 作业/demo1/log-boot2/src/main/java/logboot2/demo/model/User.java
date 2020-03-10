package logboot2.demo.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class User {
    private Integer id;
    private String username;
    private byte sex;
    private byte deleted;
    private Date updateTime;
    private Date createTime;
}
