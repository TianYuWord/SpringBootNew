#Response 统一格式
##一，本课程目标
1，弄清楚为什么要对springboot，所有Controller的response做统一格式封装？
1，学会用ResponseBodyAdvice接口 和 @ControllerAdvice注解

##二，为什么要对springboot的接口返回值统一标准格式？
我们先来看下，springboot默认情况下的response是什么格式的

###第一种格式：response为Spring

```
@ApiOperation("获得字符接口")
@GetMapping("/string")
	public String getString(){
	return "test";
}
```

以上SpringBoot的返回值为

```
test
```

### 第二种格式：response为Object

```
@GetMapping("/user/{id}")
@ApiOperation("通过id获得用户信息")
	public User getOUser(@PathVariable("id") Integer id){
	User user = new User();
	user.setUsername("temp");
	user.setPassword("123456");
	user.setId(id);
	return user;
}
```

以上SpringBoot返回值格式为

```
Response body
{
  "id": 12,
  "username": "temp",
  "password": "123456",
  "sex": null,
  "phone": null
}
```

### 第三种格式：response为void
```
@GetMapping("/empty")
@ApiOperation("empty")
public void empty(){
}
```
以上SpringBoot返回值格式为空

### 第四种格式：response为异常
```
@GetMapping("/error")
@ApiOperation("error")
public void error(){
	int i = 9/0;
}
```

以上SpringBoot返回值格式为异常
```
{
  "timestamp": "2020-03-10T15:17:56.503+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/user"
}
```

以上4种情况 ，如果你和客户端（app h5）开发人员联调接口，他们会很懵逼，因为你给他们的接口没有一个统一的格式，客户端开发人员，不知道如何处理返回值。

故，我们应该统一response的标准格式。
一般的response的标准格式包含3部分：
1，status状态值：代表本次请求response的状态结果。
2，response描述：对本次1状态码的描述。
3，data数据：本次返回的数据。
```
{
	"status" : 0
	"desc" : "成功"
	"data" : "test"
}
```

##四，初级程序员对response代码封装
对response的统一封装，是有一定的技术含量的，我们下来看一下，初级程序员的封装，网上有很多教程是这样写的。
### 步骤1：把标准格式转化为代码
```
{
	"status" : 0
	"desc" : "成功"
	"data" : "test"
}
```
把以上代码转化为Result代码
```
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result <T> {

    /**
     * 1,status 状态值：代表本次请求response的状态结果。
     */
    private Integer status;

    /**
     * 2,response描述：对本次状态码的描述。
     */
    private String desc;

    /**
     * 3,data数据：本次返回的数据。
     */
    private T data;

    /**
     * 成功，创建ResResult：没data数据
     * @return
     */
    public static Result suc(){
       Result result = new Result();
       result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     * @param data
     * @return
     */
    public static Result suc(Object data){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败指定status，desc
     * @param status
     * @param desc
     * @return
     */
    public static Result fail(Integer status,String desc){
        Result result = new Result();
        result.setStatus(status);
        result.setDesc(desc);
        return result;
    }

    /**
     * 失败，指定ResultCode枚举
     * @param resultCode
     * @return
     */
    public static Result fail(ResultCode resultCode){
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 把ResultCode枚举转化为ResResult
     * @param code
     */
    private void setResultCode(ResultCode code){
        this.status = code.code();
        this.desc = code.message();
    }
}
```

### 步骤2：把状态码存在枚举类
```
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0,"成功"),

    /* 系统500错误 */
    SYSTEM_ERROR(1000,"系统异常，请稍候重试"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001,"参数无效"),

    /*  用户错误：20001-29999 */
    USER_MAS_EXISTED(20001,"用户名已存在"),

    USER_NOT_FIND(20002,"用户名不存在");

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

```

### 步骤3：加一个体验类
```
@Api(description = "测试初级程序员接口统一")
@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation("初级程序员测试接口")
    @GetMapping(value = "/test")
    public Result getResult(){
        System.out.println(Result.suc("test"));
        return Result.suc("test");
    }
}
```
结论：看到这里应该可以看出这个代码有很多弊端
应为今后你每写一个接口，都要手工指定Result.suc() 这行代码，多累呀？
如果你写这种代码推广给整个公司用，然后硬性规定代码这么写！！所有程序员都会鄙视你的！！！

##五，高级程序员对response代码的封装
如果你在公司推广代码规范，为了避免被公司和其他程序员吐曹和鄙视，我们必须优化代码
优化的目标：不要每个接口都手工指定result返回值。

### 步骤1：采用ResponseBodyAdvice技术来实现response的统一格式
springboot提供了ResponseBodyAdvice来帮助我们处理
ResponseBodyAdvice的作用：拦截Controller方法的返回值，统一处理返回值，统一处理返回值/响应体，一般采用response的统一格式，加解密，签名等等
先看一下ResponseBodyAdvice接口的源码
```
public interface ResponseBodyAdvice<T> {
    
    /**
     * 是否支持advice功能
     * true=支持，false=不支持
     * @param methodParameter
     * @param aClass
     * @return
     */
    boolean supports(MethodParameter var1, Class<? extends HttpMessageConverter<?>> var2);

     /**
     * 处理response的具体业务方法
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Nullable
    T beforeBodyWrite(@Nullable T var1, MethodParameter var2, MediaType var3, Class<? extends HttpMessageConverter<?>> var4, ServerHttpRequest var5, ServerHttpResponse var6);
}
```

###步骤2：写一个ResponseBodyAdvice实现类

```
@ControllerAdvice(basePackages = "cn.boot.responsepro")
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     * true=支持，false=不支持
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理response的具体业务方法
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof ErrorResule){
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
        }else if(o instanceof String){
            return JsonUtil.object2Json(Result.suc(o));
        }
        return Result.suc(o);
    }
}
```

以上代码，有2个地方需要重点讲解：
#### 第1个地方：@ControllerAdvice 注解：
@ControllerAdvice这是一个非常有用的注解，它的作用是增强Controller的扩展功能类。
那@ControllerAdvice对Controller增强了那些扩展够功能呢？主要体现在2方面
1，对Controller全局数据统一处理，例如我们这节课就是对response统一封装。
2，对Controller全局异常统一处理，这个后面的课程会详细讲解。

在使用@ControllerAdvicce时，还要特别注意，加上basePackages，
@ControllerAdvice(basePackage = "cn.boot.response")，应为如果不加的话，他可是对整个系统的Controller做了扩展功能，
他会对某些特殊功能产生突出，例如不加的话，在使用Swagger时会出现空白页面异常。

#### 第2个地方：beforeBodyWriter方法体的response类型判断
```
if(o instanceof String){
	return JsonUtilr.object2Json(ResResult.suc(o));
}
```
实际上高级程序员就是实现了（implements）ControllerAdvice整个类，并且结合了初级程序员的Result来实现了接口的统一封装
以上代码一定要加，应为Controller的返回值为String的时候，它直接返回String，不是json
故我们要手工做以下json转换处理
```
<!--json 依赖-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>
```



##六：课后练习题
1，实现ResponseBodyAdvice这个接口，并且在接口上标上@ControllerAdvice(basePackages = "cn.boot.responsepro")
然后更具情况返回
```
@Override
public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
	/*if(o instanceof ErrorResule){
	    ErrorResult errorResult = (ErrorResult) o;
	    return Result.fail(errorResult.getStatus(),errorResult.getMessage());
	}else */
	if(o instanceof Error){

	}
	log.info("----走了ControllerAdvice---");
	if(o instanceof String){
	    return JSON.toJSONString(Result.suc(o));
	}
	return Result.suc(o);
}
```