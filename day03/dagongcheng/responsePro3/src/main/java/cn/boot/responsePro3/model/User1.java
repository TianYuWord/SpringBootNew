package cn.boot.responsePro3.model;

import lombok.Data;

@Data
public class User1 {
    private Integer id;
    private String username;
    private String password;
    private Byte sex;
    private String phone;
}