package cn.boot.responsepro2.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Byte sex;
    private String phone;
}
