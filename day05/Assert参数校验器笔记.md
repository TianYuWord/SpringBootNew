### һ���γ�Ŀ��
���ڿξ�2��Ŀ�꣺
1�����ʹ��Assert����У����
2��Ϊʲô����Validator����У����������������Assert����У������

### ����Ϊʲô��Assert����У�飿
Assert����Ϊ����Ϊ�����ԡ�������spring��һ��util�࣬org.springframework.util.Assert
һ�����϶�ĳһ��ʵ�ʵ�ֵ�Ƿ�Ϊ�Լ�Ԥ��õ��ģ�������ǣ����׳��쳣��

### ����Ϊʲô����Validator����У�飬��������Assert����У�飿
��Ҫ��2��ԭ��<br>
1����Ϊvalidatorֻ����˲�������������У�飬������˲�����ҵ��֮��У�顣
   �������´��룬validator�Ǹ㲻����
```
public void test1(){
User user = userDao.selectById(userId);
	if(user == null){
	   //ֱ���׳��쳣 , ����Ĵ��벻����idea�༭�ģ����ܻ��׳��쳣����ŵ���˼����˵�����������if else�������ͻ��׳��쳣
	   throw new RuntimeException();
	}
}
```
2������Assert��ʹ���������
���´������ת��Ϊһ�����Ŵ���
```
public void test1(){
User user = userDao.selectById(userId);
	if(user == null){
	   Assert.notNull(user,"�û������ڣ�")
	}
}
```

### �ģ�����ʵս���޸��û���Ϣʱ����AssertУ���û��Ƿ���ڣ�
#### ����1��У���û��Ƿ����
```
@PostMapping(value = "/user/update")
public void updateUser(@RequestBody @Validated UserVO userVO){
	UserVO userVO1 = null;
	Assert.notNull(userVO1,"�û������ڣ�");
}
```
#### ���Խ��: ����û��ȫ���쳣�����״̬���׳�json��ʽ���ݣ�
```
{
  "timestamp": "2020-03-16T15:28:19.581+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "�û������ڣ�",
  "path": "/user/user/update"
}
```
�����ϲ��Խ������֪����
1������У�飬һ�㶼��validator��Assert һ����ʹ�õģ�validatorֻ��������������У�飬������˲�����ҵ��վ֮�������У�顣
2���Ӳ��Խ������Assert�׳��쳣��һ��json�����json ����������Ҫ�ģ����Ա���͡�ȫ���쳣��������һ���װ

AssertҲ��Spring�ٷ�һֱ�õ�ע��

### �壬���õ�Assert����
һ �߼�����
1��isTrue()
<br> �������Ϊ���׳�IllegalArgumentException�쳣��
2��state()
<br> �÷�����isTrueһ�������׳�IllegalStateException�쳣��

һ����������Ͷ���
1��notNull()
<br>ͨ��notNull() �������Լ������null��
2��isNull()
<br>����������Ϊnull��
3��isInstanceOf()
<br>isInstanceOf() �������������Ϊ��һ���ض����͵�ʵ��
4��isAssignable()
<br>ʹ��Assert.isAssignable()�����������

- ���Ķ���
1��hasLength()
<br> �������ַ����ǿշ�������ζ�����ٰ���һ���հף�����ʹ��hasLength()����
2��hasText()
<br>��������ǿ����������ַ������ٰ���һ���ǿ��ַ���������Ӵ��hasText()����
3��doesNotContain()
<br>������ͨ��doesNotContan()����������������ָ���Ӵ���

- Collection��map����
1��CollectionӦ��notEmpty()
<br> ����������ʾ��notEmpty()��������collection��Ϊ�գ���ζ�߲���null����������һ��Ԫ�ء�
2��mapӦ��notEmpty()
<br> ͬ���ķ�����������map�����map��null�������ٰ���һ��entry��key��value��ֵ�ԣ���

- �������
1��notEmpty()
<br> notEmpty()�������Լ�����鲻Ϊnull�������ٰ���һ��Ԫ�أ�
2��noNullElements()
<br> noNullElements()����ȷ�����鲻ΪnullԪ�أ�

### �壬5������ʵս����Assert�쳣���롶ȫ���쳣��������
ΪʲôҪ�ӣ�
ӦΪAssert���쳣�ṹ����:
```

```
�����쳣һ���ǲ�����ͻ��������ģ���������롶ȫ���쳣��������
��ΪAssert�׳����쳣�������IllegalArgumentException.class ���ͣ�������ȫ���쳣��ʹ��IllegalArgumentException
```
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    /**
     * ��������ʱ�쳣
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        //����ʱ�쳣���������������¼�����ڷ��쳣�ʼ�֪ͨ
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR,e);
        log.error("URL:{},ϵͳ�쳣��",request.getRequestURI(),e);
        return errorResult;
    }*/

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
            sb.append("]  ");
        }
        return sb.toString();
    }

    /**
     * Assert ���쳣ͳһ��װ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentException(IllegalArgumentException e,HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().message(e.getMessage()).status(4000).throwable(e).build();
        log.warn("URL:{} ,ҵ��У���쳣��{}",request.getRequestURI(),e);
        return errorResult;
    }
}
```

ִ�н��
```
{
  "status": 4000,
  "message": "�û������ڣ�",
  "data": null
}
```
###�ߣ��κ���ϰ
��������Assert��isTrue()��notEmpty()��Ч�����ǵ�Ҫ�쳣��ϡ�ȫ���쳣����������ʵ�֡�
```
@ApiOperation("��ҵ")
@PostMapping(value = "/zy")
public void zy(@RequestBody @Validated UserVO userVO){
	Assert.isTrue(userVO.isVip(),"���󣬷�vip��������");
	Assert.notEmpty(userVO.getSkill(),"���󣬱�����һ�����ϵļ���");
}
```
ִ�н��1
```
{
  "status": 4000,
  "message": "���󣬷�vip��������",
  "data": null
}
```
ִ�н��2
```
{
  "status": 4000,
  "message": "���󣬱�����һ�����ϵļ���",
  "data": null
}
```