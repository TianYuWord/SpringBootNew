## 目标
1，为什么需要《全局异常处理器》
2，自己实现一个《全局异常处理器》
3，封装一个自定义异常，并集成进《全局异常处理器》
4，把《全局异常处理器》集成经《接口返回值统一标准格式》

## 二，springboot为什么需要全局异常处理器？
1，先讲解什么是全局异常处理器
2，那为什么需要全局异常处理器呢？

- 第一个原因：不用强写try-catch，由全局异常处理器统一捕获处理
```

```
如果不用try-catch捕获的话，客户端就会怎么样？
```
{
  "timestamp": "2020-03-11T14:50:21.727+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/error"
}
```
一般程序员的try-catch
```
@GetMapping("/error1")
@ApiOperation("error1")
public String error(){
try {
    int i = 9 / 0;
}catch (Exception ex){
    log.error("error");
    return "no";
}
}
```

但是还要一直自动化处理的，就是不用写try-catch，有全局异常处理器来处理。
- 第二个原因 自定义异常，只能用全局异常捕获
```
@GetMapping("/error")
@ApiOperation("error")
public void error(){
	int i = 9 / 0;
}
```
```
{
  "timestamp": "2020-03-11T14:50:21.727+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/error"
}
```
不可能这样返回给客户端，客户端是看不懂的，而且需要接进《接口返回值统一标准格式》

- 第三个原因：JSR303规范的Validator参数校验器，参数校验不通过会抛异常，是无法使用try-catch语句直接捕获，
只能使用全局异常处理器了，JSR303规范的Validator参数校验器的异常处理后面课程会单独讲解，本节课暂不讲解。


## 三，案例实战，自己实现一个SpringBoot《全局异常处理器》
### 步骤1：封装异常内容，统一存储正在枚举类中
把所有的未知运行时异常都，用SYSTEM_ERROR(10000,"系统异常请稍候重试")来提示
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
### 步骤2：封装Controller的异常结果
```
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {
    /**
     * 异常状态码
     */
    private Integer status;

    /**
     * 用户看得见的异常，例如 用户名重复！！
     */
    private String message;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 异常堆栈信息
     */
    private String errors;

    /**
     * 对异常提示语进行封装
     */
    public static ErrorResult fail(ResultCode resultCode,Throwable e,String message){
        ErrorResult errorResult = ErrorResult.fail(resultCode,e);
        errorResult.setMessage(message);
        return  errorResult;
    }

    /**
     * 对异常枚举进行封装
     */
    public static ErrorResult fail(ResultCode resultCode,Throwable e){
        ErrorResult errorResult = new ErrorResult();
        errorResult.setMessage(resultCode.message());
        errorResult.setStatus(resultCode.code());
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }

    public class fail extends ErrorResult {

    }
}

```
### 步骤3：加个全局异常处理器，对异常进行处理
```
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
}
```
handleThrowable 方法的哦作用是：捕获运行时异常，并把异常统一封装为ErrorrResult对象。
以上几个细节点我们要单独讲解
1，@ControllerAdvice在《response统一格式》课程的时候，我们就讲解过它是增强Controller的扩展功能。而全局异常处理器，就是扩展功能之一。
2，@ExeceptionHandler统一处理某一类异常，从而能够减少代码重复率和复杂度，@ExceptionHandler(Throwable.class)指处理Throwable的异常。
3，@ResponseStatus 指定客户端受到的Http状态吗，这里配置500错误，客户端就会现实500错误，

### 步骤4：体验效果
```
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
```
结果
```
{
  "status": 0,
  "desc": "成功",
  "data": {
    "status": 1000,
    "message": "系统异常，请稍候重试",
    "exception": "java.lang.ArithmeticException",
    "errors": null
  }
}
```

## 三，案例实战：把自定义异常 集成 进《全局异常处理器》
### 步骤1：封装一个自定义异常
自定义异常通常是集成
```

### 步骤2：把自定义异常  集成 进全局异常处理
全局异常处理器只要宅上节课的基础上，添加一个自定义异常即可。
```
@ExceptionHandler(BusinessException.class)
public ErrorResult handleBusinessException(BusinessException e,HttpServletRequest request){
ErrorResult error = ErrorResult.builder().status(e.code)
	.message(e.message)
	.exception(e.getClass().getName())
	.build();
	log.warn("URL:{} ,异常业务：{}",request.getRequestURI(),error);
	return error;
}
```
### 步骤3 ：体验效果
```
{
  "status": 20001,
  "message": "用户名已存在",
  "exception": "cn.boot.responsePro3.exception.BusinessException",
  "errors": null
}
```

## 四 案例实战 ：把《全局异常处理器》集成进《接口反回值统一标准格式》
目标：把《全局异常处理器》的json格式转换为《接口返回统一标准格式》格式
```
{
  "status": 20001,
  "message": "用户名已存在",
  "exception": "cn.boot.responsePro3.exception.BusinessException"
}
```
转换
```
{
  "status": 20001,
  "message": "用户名已存在",
  "data": null
}
```

### 步骤1 ：改造ResponseHandler
```
@ControllerAdvice(basePackages = "cn.boot.responsepro")
@Slf4j
public class ResponseHandler1 implements ResponseBodyAdvice<Object> {

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
}
```
在 《接口返回值统一标准格式》 的从基础上
```
if(o instanceof ErrorResule){
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
```
### 步骤2 ：体验效果
```
@ApiOperation("抛出异常")
@PostMapping(value = "/error")
public void error3(){
	throw new BusinessException(ResultCode.USER_MAS_EXISTED);
}
```
结果
```
{
  "status": 20001,
  "desc": "用户名已存在",
  "data": null
}
```