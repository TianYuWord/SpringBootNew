## �γ�Ŀ��
1��ʲô��Validator����У������ΪʲôҪ�ã�
2������ʵ�ֶ��û��������룬���䣬���֤ValidatorУ��
3��ʵ��һ���ֻ������У��
4����validator�쳣���롶ȫ���쳣��������

## ����ΪʲôҪ��Validator����У�������������ʲô���⣿
���������ճ��Ľӿڿ����У�����Ҫ�ԽӿڵĲ�������У�飬���磬��½ʱ��ҪУ���û��� �����Ƿ�Ϊ�գ����������ճ��Ľӿڲ���У��̫�����ˣ����뷱���ֶࡣ
Validator��ܾ���Ϊ�˽��������Ա�ڿ�����ʱ����д���룬��������Ч�ʵģ���ר���������ӿڲ�����У��ģ����� emailУ�飬�û������ȱ���λ��6��12֮�� �ȵȡ�
spring ��validatorУ������ѭ��JSR-303��֤�淶������У��淶����JSR��Java Secification Requests����д��
��Ĭ������£�SpringBoot������Hibernate Validator������֧��JSR-303 ��֤�淶��
�ʣ�Spring Boot��validatorУ������3�����ԣ�
1��JSR303���ԣ�JSR303��һ���׼��ֻ�ṩ�淶���ṩʵ�֣��涨һЩУ��淶��У��ע�⣬��@Null��@NotNull��@Pattern��λ��javax.validation.constraints���¡�
2��hibernate validation���ԣ�hibernate validation�Ƕ�JSR303�淶��ʵ�֣���������һЩ����У��ע�⣬��@Email��@Length��@Range�ȵ�
3��spring validation��spring validation��hibernate validation�����˶��η�װ����Springmvcģ����������Զ�У�飬����У����Ϣ��װ�����ض������С�

##��������springboot�Ĳ���У�鹦��
### ����1��pom�ļ�����������
springboot ��Ȼ֧��validator���ݾ�У��
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### ����2������һ��VO��
```
@Data
public class UserVO {

    private Integer id;

    @NotEmpty(message = "�û�������Ϊ��")
    @Length(min = 6,max = 12,message = "�û������ȱ���λ��6��12֮��")
    private String username;

    @NotEmpty(message = "���벻��Ϊ��")
    @Length(min = 6,max = 26,message = "���볤����6��26֮��")
    private String password;

    @Email(message = "��������ȷ������")
    private String email;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",message = "���֤��ʽ����")
    private String idCard;

    private Byte sex;
    private byte deleted;
    private Date updateTime;
    private Date createTime;

}
```

### ����3����һ��������
```
@Api(description = "validator����")
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
        Assert.notNull(userVO,"�û������ڣ�");
    }

}
```
ע�⣺���ϴ���һ��Ҫ����@Validated����������������У��UserVO�Ĳ����Ƿ���ȷ
ִ�н��
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
      "defaultMessage": "��������ȷ������",
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
      "defaultMessage": "���֤��ʽ����",
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

## �ģ�Validation����ע��

- @Null ����ֻ��Ϊnull
- @NotNull ���Ʊ��벻Ϊnull
- @AssertTrue ���Ʊ���Ϊtrue
- @DecimalMax(value) ���Ʊ���Ϊһ��������ָ��ֵ������
- @DecimalMin(value) ���Ʊ���Ϊһ����С��ָ��ֵ������
- @Digits(integer,fraction) ���Ʊ���Ϊһ��С�������������ֵ�λ�����ܳ���integer��С�����ֵ�λ�����ܳ���integer��С�����ֵ�λ�����ܳ���fraction
- @Future ���Ʊ���Ϊһ������������
- @Max(value) ���Ʊ���Ϊһ��������ָ��ֵ������
- @Min(value) ���Ʊ���Ϊһ����С��ָ��ֵ������
- @Past ���Ʊ���Ϊһ����ȥ������
- @Pattern(value) ���Ʊ������ָ���ı��ʽ
- @Size(max,min) �����ַ������ȱ�����min��max֮��
- @Past ��֤ע���Ԫ��ֵ���������ͣ��ȵ�ǰʱ����
- @NotEmpty ��֤ע���Ԫ��֮��Ϊnull�Ҳ�Ϊ�գ��ַ������Ȳ�Ϊ0�����ϴ�С��Ϊ0��
- @NotBlank ��֤ע���Ԫ��ֵ��Ϊ�� ����Ϊnull��ȥ����λ�ո�󳤶�Ϊ0������ͬ��@NotEmpty��@NotBlankֻӦ�����ַ������ڱȽ�ʱ��ȥ���ַ����Ŀո�
- @Email ��֤ע���Ԫ��ֵ��Email��Ҳ����ͨ��������ʽ��flagָ���Զ����email��ʽ


##�Զ���validatorע��
ΪʲôҪ�Զ���validatorע���أ�
ӦΪvalidator���֧�ֵ�ע�����ޣ������ܷ������涼֧�֣�����Ҫ�����Զ���ע�⡣
���Ǿ����ֻ���Ϊ���ӣ��̴��дһ�����ֻ�����У���validatorע�⡣
### ����һ������һ��@Interface���ֻ���У��ע��
```
@Documented
//ָ��ʵ����
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "������ȷ���ֻ�����";

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
### �������дһ��ע���ʵ���࣬�ֻ�����У��ע��ʵ����
```
public class PhoneValidator implements ConstraintValidator<Phone,String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$"
    );

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    /**
     * У���ʵ���߼�
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

### ����3����UserVO�������ֻ�����У��ע��
```
@Data
public class UserVO {

    private Integer id;

    @NotEmpty(message = "�û�������Ϊ��")
    @Length(min = 6,max = 12,message = "�û������ȱ���λ��6��12֮��")
    private String username;

    @NotEmpty(message = "���벻��Ϊ��")
    @Length(min = 6,max = 26,message = "���볤����6��26֮��")
    private String password;

    @Email(message = "��������ȷ������")
    private String email;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",message = "���֤��ʽ����")
    private String idCard;

    @Phone
    private String phoneNumber;

    private Byte sex;
    private byte deleted;
    private Date updateTime;
    private Date createTime;

}
```
### ����4������Ч��
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
���
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
      "defaultMessage": "������ȷ���ֻ�����",
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
      "defaultMessage": "��������ȷ������",
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
      "defaultMessage": "���֤��ʽ����",
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

## ������validator�쳣���롶ȫ���쳣��������
��ΪʲôҪ��validator���롶ȫ���쳣���������أ�
��Ϊvalidator�쳣���ص�������json����json���ݽṹ���������ĵ�json���ر���.
�����ڿͻ�������������Ҳ���к���ʾ���ʣ�������롶ȫ���쳣��������

### ����1����ȫ���쳣������������validator�쳣����
```
//��ȫ���쳣��������
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * ��������ʱ�쳣
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        //����ʱ�쳣���������������¼�����ڷ��쳣�ʼ�֪ͨ
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR,e);
        log.error("URL:{},ϵͳ�쳣��",request.getRequestURI(),e);
        return errorResult;
    }

    /**
     * ��validator���쳣���ϵ���ȫ���쳣��������
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletRequest request){
        String msgs = this.handle(e.getBindingResult().getFieldErrors());
        ErrorResult error = ErrorResult.fail(ResultCode.ERROR,e,msgs);
        log.warn("URL:{} , ����У�����쳣:{} ",request.getRequestURI(),msgs);
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
���
```
{
  "status": 500,
  "message": "phoneNumber=[������ȷ���ֻ�����]  ",
  "data": null
}
```

ӦΪ֮ǰѧ��validator����Ͳ�����ͣ��