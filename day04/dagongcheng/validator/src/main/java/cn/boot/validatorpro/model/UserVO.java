package cn.boot.validatorpro.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserVO {

    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6,max = 12,message = "用户名长度必须位于6到12之间")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 26,message = "密码长度在6到26之间")
    private String password;

    @Email(message = "请输入正确的邮箱")
    private String email;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",message = "身份证格式错误")
    private String idCard;

    private Byte sex;
    private byte deleted;
    private Date updateTime;
    private Date createTime;

}
