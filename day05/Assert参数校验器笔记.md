### 一，课程目标
本节课就2个目标：
1，如何使用Assert参数校验器
2，为什么用了Validator参数校验器，还必须再用Assert参数校验器？

### 二，为什么用Assert参数校验？
Assert翻译为中文为“断言”，它是spring的一个util类，org.springframework.util.Assert
一般来断定某一个实际的值是否为自己预想得到的，如果不是，则抛出异常。

### 三，为什么用了Validator参数校验，还必须用Assert参数校验？
主要是2给原因：<br>
1，因为validator只解决了参数自生的数据校验，解决不了参数和业务之间校验。
   例如以下代码，validator是搞不定的
```
public void test1(){
User user = userDao.selectById(userId);
	if(user == null){
	   //直接抛出异常 , 这里的代码不是用idea编辑的，可能会抛出异常，大概的意思就是说，如果不满足if else的条件就会抛出异常
	   throw new RuntimeException();
	}
}
```
2，采用Assert能使代码更优雅
以下代码可以转变为一下优雅代码
```
public void test1(){
User user = userDao.selectById(userId);
	if(user == null){
	   Assert.notNull(user,"用户不存在！")
	}
}
```

### 四，案例实战：修改用户信息时，用Assert校验用户是否存在？
#### 步骤1：校验用户是否存在
```
@PostMapping(value = "/user/update")
public void updateUser(@RequestBody @Validated UserVO userVO){
	UserVO userVO1 = null;
	Assert.notNull(userVO1,"用户不存在！");
}
```
#### 测试结果: （在没有全局异常捕获的状态下抛出json格式数据）
```
{
  "timestamp": "2020-03-16T15:28:19.581+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "用户不存在！",
  "path": "/user/user/update"
}
```
从以上测试结果可以知道：
1，参数校验，一般都是validator和Assert 一起结合使用的，validator只解决了自身的数据校验，解决不了参数和业务站之间的数据校验。
2，从测试结果看，Assert抛出异常是一个json，这个json 不是我们想要的，所以必须和《全局异常处理器》一起封装

Assert也是Spring官方一直用的注解

### 五，常用的Assert场景
一 逻辑语言
1，isTrue()
<br> 如果条件为假抛出IllegalArgumentException异常。
2，state()
<br> 该方法与isTrue一样，但抛出IllegalStateException异常。

一，对象和类型断言
1，notNull()
<br>通过notNull() 方法可以假设对象不null；
2，isNull()
<br>用来检查对象为null：
3，isInstanceOf()
<br>isInstanceOf() 方法检查对象必须为另一个特定类型的实例
4，isAssignable()
<br>使用Assert.isAssignable()方法检查类型

- 本文断言
1，hasLength()
<br> 如果检查字符不是空符串，意味者至少包含一个空白，可以使用hasLength()方法
2，hasText()
<br>我们能增强检查条件，字符串至少包含一个非空字符，可以是哟个hasText()方法
3，doesNotContain()
<br>我们能通过doesNotContan()方法检查参数不包含指定子串。

- Collection和map断言
1，Collection应用notEmpty()
<br> 如其名称所示，notEmpty()方法断言collection不为空，意味者不是null并包含至少一个元素。
2，map应用notEmpty()
<br> 同样的方法重载用于map，检查map不null，并至少包含一个entry（key，value键值对）。

- 数组断言
1，notEmpty()
<br> notEmpty()方法可以检查数组不为null，且至少包括一个元素：
2，noNullElements()
<br> noNullElements()方法确保数组不为null元素：

### 五，5，案例实战：把Assert异常加入《全局异常处理器》
为什么要加？
应为Assert的异常结构如下:
```

```
这种异常一般是不允许客户段联调的！！必须加入《全局异常处理器》
因为Assert抛出的异常大多数是IllegalArgumentException.class 类型，所以在全局异常中使用IllegalArgumentException
```
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * 处理运行时异常
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        //运行时异常，可以在在这里记录，用于发异常邮件通知
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR,e);
        log.error("URL:{},系统异常：",request.getRequestURI(),e);
        return errorResult;
    }*/

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
            sb.append("]  ");
        }
        return sb.toString();
    }

    /**
     * Assert 的异常统一封装
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentException(IllegalArgumentException e,HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().message(e.getMessage()).status(4000).throwable(e).build();
        log.warn("URL:{} ,业务校验异常：{}",request.getRequestURI(),e);
        return errorResult;
    }
}
```

执行结果
```
{
  "status": 4000,
  "message": "用户不存在！",
  "data": null
}
```
###七：课后练习
编码体验Assert的isTrue()，notEmpty()的效果，记得要异常结合《全局异常处理器》来实现。
```
@ApiOperation("作业")
@PostMapping(value = "/zy")
public void zy(@RequestBody @Validated UserVO userVO){
	Assert.isTrue(userVO.isVip(),"错误，非vip参数请求");
	Assert.notEmpty(userVO.getSkill(),"错误，必须有一个以上的技能");
}
```
执行结果1
```
{
  "status": 4000,
  "message": "错误，非vip参数请求",
  "data": null
}
```
执行结果2
```
{
  "status": 4000,
  "message": "错误，必须有一个以上的技能",
  "data": null
}
```