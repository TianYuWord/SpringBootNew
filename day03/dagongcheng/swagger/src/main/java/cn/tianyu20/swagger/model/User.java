package cn.tianyu20.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户信息")
@Data
public class User {
    @ApiModelProperty("用户ID")
    private Integer id;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("余额")
    private Double price;
    @ApiModelProperty("用户的性别")
    private byte sex;
}
