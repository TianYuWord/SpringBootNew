## 课程目标
1，什么是Validator参数校验器？为什么要用？
2，编码实现对用户名，密码，邮箱，身份证Validator校验
3，实现一个手机号码的校验
4，把validator异常加入《全局异常处理器》

## 二，为什么要用Validator参数校验器，它解决了什么问题？
背景：在日常的接口开发中，经常要对接口的参数进行校验，例如，登陆时候要校验用户名 密码是否为空，但是这种日常的接口参数校验太繁琐了，代码繁琐又多。
Validator框架就是为了解决开发人员在开发的时候少写代码，提升开发效率的；它专门用来做接口参数的校验的，例如 email校验，用户名长度必须位于6到12之间 等等。
spring 的validator校验框架遵循了JSR-303验证规范（参数校验规范），JSR是Java Secification Requests的缩写。
在默认情况下，SpringBoot会引入Hibernate Validator机制来支持JSR-303 验证规范。
故，Spring Boot的validator校验框架有3个特性：
1，JSR303特性：JSR303是一项标准，只提供规范不提供实现，规定一些校验规范即校验注解，如@Null，@NotNull，@Pattern，位于javax.validation.constraints包下。
2，hibernate validation特性：hibernate validation是对JSR303规范的实现，并增加了一些其他校验注解，如@Email，@Length，@Range等等
3，spring validation：spring validation对hibernate validation进行了二次封装，在Springmvc模块中添加了自动校验，并将校验信息封装进了特定的类中。

##三，体验springboot的参数校验功能
### 步骤1：pom文件加入依赖包
springboot 天然支持validator数据据校验
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 步骤2：创建一个VO类
```
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
```

### 步骤3：加一个体验类
```
@Api(description = "validator测试")
@RequestMapping("user")
@RestController
@Slf4j
public class IndexController {


    @GetMapping("/register")
    public String index(){
        return null;
    }

    @PostMapping(value = "/user/update")
    public void updateUser(@RequestBody @Validated UserVO userVO){
        UserVO userVO1 = null;
        Assert.notNull(userVO,"用户不存在！");
    }

}
```
注意：以上代码一定要加上@Validated，它的作用是用来校验UserVO的参数是否正确
执行结果
```
{
  "timestamp": "2020-03-15T03:03:38.730+0000",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {
      "codes": [
        "Email.userVO.email",
        "Email.email",
        "Email.java.lang.String",
        "Email"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.email",
            "email"
          ],
          "arguments": null,
          "defaultMessage": "email",
          "code": "email"
        },
        [],
        {
          "defaultMessage": ".*",
          "arguments": null,
          "codes": [
            ".*"
          ]
        }
      ],
      "defaultMessage": "请输入正确的邮箱",
      "objectName": "userVO",
      "field": "email",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Email"
    },
    {
      "codes": [
        "Pattern.userVO.idCard",
        "Pattern.idCard",
        "Pattern.java.lang.String",
        "Pattern"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.idCard",
            "idCard"
          ],
          "arguments": null,
          "defaultMessage": "idCard",
          "code": "idCard"
        },
        [],
        {
          "defaultMessage": "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",
          "arguments": null,
          "codes": [
            "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$"
          ]
        }
      ],
      "defaultMessage": "身份证格式错误",
      "objectName": "userVO",
      "field": "idCard",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Pattern"
    }
  ],
  "message": "Validation failed for object='userVO'. Error count: 2",
  "path": "/user/user/update"
}
```

## 四，Validation常用注解

- @Null 限制只能为null
- @NotNull 限制必须不为null
- @AssertTrue 限制必须为true
- @DecimalMax(value) 限制必须为一个不大于指定值的数字
- @DecimalMin(value) 限制必须为一个不小于指定值的数字
- @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过integer，小数部分的位数不能超过fraction
- @Future 限制必须为一个将来的日期
- @Max(value) 限制必须为一个不大于指定值的数字
- @Min(value) 限制必须为一个不小于指定值的数字
- @Past 限制必须为一个过去的日期
- @Pattern(value) 限制必须符合指定的表达式
- @Size(max,min) 限制字符串长度必须在min和max之间
- @Past 验证注解的元素值（日期类型）比当前时间早
- @NotEmpty 验证注解的元素之不为null且不为空（字符串长度不为0，集合大小不为0）
- @NotBlank 验证注解的元素值不为空 （不为null，去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
- @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式


##自定义validator注解
为什么要自定义validator注解呢？
应为validator框架支持的注解有限，不可能方方面面都支持，故需要我们自定义注解。
我们就以手机号为例子，教大家写一个对手机号码校验的validator注解。
### 步骤一；创建一个@Interface的手机好校验注解
```
@Documented
//指定实现类
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "输入正确的手机号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.METHOD, ElementType.FIELD ,ElementType.CONSTRUCTOR, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        Phone[] value();
    }
}
```
### 步骤二；写一个注解的实现类，手机号码校验注解实现类
```
public class PhoneValidator implements ConstraintValidator<Phone,String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$"
    );

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    /**
     * 校验的实际逻辑
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if( s == null || s.length() == 0) {
            return true;
        }

        Matcher m = PHONE_PATTERN.matcher(s);

        return m.matches();
    }
}

```

### 步骤3：给UserVO，加上手机号码校验注解
```
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

    @Phone
    private String phoneNumber;

    private Byte sex;
    private byte deleted;
    private Date updateTime;
    private Date createTime;

}
```
### 步骤4：体验效果
```
{
  "createTime": "2020-03-15T15:25:37.576Z",
  "deleted": 0,
  "email": "string",
  "id": 0,
  "idCard": "string",
  "password": "string",
  "phoneNumber": "string",
  "sex": 0,
  "updateTime": "2020-03-15T15:25:37.576Z",
  "username": "string"
}
```
结果
```
{
  "timestamp": "2020-03-15T15:25:43.746+0000",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {
      "codes": [
        "Phone.userVO.phoneNumber",
        "Phone.phoneNumber",
        "Phone.java.lang.String",
        "Phone"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.phoneNumber",
            "phoneNumber"
          ],
          "arguments": null,
          "defaultMessage": "phoneNumber",
          "code": "phoneNumber"
        }
      ],
      "defaultMessage": "输入正确的手机号码",
      "objectName": "userVO",
      "field": "phoneNumber",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Phone"
    },
    {
      "codes": [
        "Email.userVO.email",
        "Email.email",
        "Email.java.lang.String",
        "Email"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.email",
            "email"
          ],
          "arguments": null,
          "defaultMessage": "email",
          "code": "email"
        },
        [],
        {
          "defaultMessage": ".*",
          "arguments": null,
          "codes": [
            ".*"
          ]
        }
      ],
      "defaultMessage": "请输入正确的邮箱",
      "objectName": "userVO",
      "field": "email",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Email"
    },
    {
      "codes": [
        "Pattern.userVO.idCard",
        "Pattern.idCard",
        "Pattern.java.lang.String",
        "Pattern"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.idCard",
            "idCard"
          ],
          "arguments": null,
          "defaultMessage": "idCard",
          "code": "idCard"
        },
        [],
        {
          "defaultMessage": "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",
          "arguments": null,
          "codes": [
            "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$"
          ]
        }
      ],
      "defaultMessage": "身份证格式错误",
      "objectName": "userVO",
      "field": "idCard",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Pattern"
    }
  ],
  "message": "Validation failed for object='userVO'. Error count: 3",
  "path": "/user/user/update"
}
```

## 六，把validator异常加入《全局异常处理器》
那为什么要把validator加入《全局异常处理器》呢？
因为validator异常返回的内容是json，而json数据结构（例如上文的json）特别复杂.
不利于客户端联调，而且也不有好提示，故，必须加入《全局异常处理器》

### 步骤1：《全局异常处理器》加入validator异常处理
```
//《全局异常处理器》
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * 处理运行时异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        //运行时异常，可以在在这里记录，用于发异常邮件通知
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR,e);
        log.error("URL:{},系统异常：",request.getRequestURI(),e);
        return errorResult;
    }

    /**
     * 将validator的异常整合到《全局异常处理器》
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletRequest request){
        String msgs = this.handle(e.getBindingResult().getFieldErrors());
        ErrorResult error = ErrorResult.fail(ResultCode.ERROR,e,msgs);
        log.warn("URL:{} , 参数校验器异常:{} ",request.getRequestURI(),msgs);
        return error;
    }


    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for(FieldError fieldError:fieldErrors){
            sb.append(fieldError.getField());
            sb.append("=[");
            sb.append(fieldError.getDefaultMessage());
            sb.append("]");
            return sb.toString();
        }
        return null;
    }
}
```
结果
```
{
  "status": 500,
  "message": "phoneNumber=[输入正确的手机号码]  ",
  "data": null
}
```

应为之前学过validator这里就不多做停留