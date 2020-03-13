## Ŀ��
1��Ϊʲô��Ҫ��ȫ���쳣��������
2���Լ�ʵ��һ����ȫ���쳣��������
3����װһ���Զ����쳣�������ɽ���ȫ���쳣��������
4���ѡ�ȫ���쳣�����������ɾ����ӿڷ���ֵͳһ��׼��ʽ��

## ����springbootΪʲô��Ҫȫ���쳣��������
1���Ƚ���ʲô��ȫ���쳣������
2����Ϊʲô��Ҫȫ���쳣�������أ�

- ��һ��ԭ�򣺲���ǿдtry-catch����ȫ���쳣������ͳһ������
```

```
�������try-catch����Ļ����ͻ��˾ͻ���ô����
```
{
  "timestamp": "2020-03-11T14:50:21.727+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/error"
}
```
һ�����Ա��try-catch
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

���ǻ�Ҫһֱ�Զ�������ģ����ǲ���дtry-catch����ȫ���쳣������������
- �ڶ���ԭ�� �Զ����쳣��ֻ����ȫ���쳣����
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
�������������ظ��ͻ��ˣ��ͻ����ǿ������ģ�������Ҫ�ӽ����ӿڷ���ֵͳһ��׼��ʽ��

- ������ԭ��JSR303�淶��Validator����У����������У�鲻ͨ�������쳣�����޷�ʹ��try-catch���ֱ�Ӳ���
ֻ��ʹ��ȫ���쳣�������ˣ�JSR303�淶��Validator����У�������쳣�������γ̻ᵥ�����⣬���ڿ��ݲ����⡣


## ��������ʵս���Լ�ʵ��һ��SpringBoot��ȫ���쳣��������
### ����1����װ�쳣���ݣ�ͳһ�洢����ö������
�����е�δ֪����ʱ�쳣������SYSTEM_ERROR(10000,"ϵͳ�쳣���Ժ�����")����ʾ
```
public enum ResultCode {

    /* �ɹ�״̬�� */
    SUCCESS(0,"�ɹ�"),

    /* ϵͳ500���� */
    SYSTEM_ERROR(1000,"ϵͳ�쳣�����Ժ�����"),

    /* ��������10001-19999 */
    PARAM_IS_INVALID(10001,"������Ч"),

    /*  �û�����20001-29999 */
    USER_MAS_EXISTED(20001,"�û����Ѵ���"),

    USER_NOT_FIND(20002,"�û���������");

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
### ����2����װController���쳣���
```
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {
    /**
     * �쳣״̬��
     */
    private Integer status;

    /**
     * �û����ü����쳣������ �û����ظ�����
     */
    private String message;

    /**
     * �쳣������
     */
    private String exception;

    /**
     * �쳣��ջ��Ϣ
     */
    private String errors;

    /**
     * ���쳣��ʾ����з�װ
     */
    public static ErrorResult fail(ResultCode resultCode,Throwable e,String message){
        ErrorResult errorResult = ErrorResult.fail(resultCode,e);
        errorResult.setMessage(message);
        return  errorResult;
    }

    /**
     * ���쳣ö�ٽ��з�װ
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
### ����3���Ӹ�ȫ���쳣�����������쳣���д���
```
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
}
```
handleThrowable ������Ŷ�����ǣ���������ʱ�쳣�������쳣ͳһ��װΪErrorrResult����
���ϼ���ϸ�ڵ�����Ҫ��������
1��@ControllerAdvice�ڡ�responseͳһ��ʽ���γ̵�ʱ�����Ǿͽ����������ǿController����չ���ܡ���ȫ���쳣��������������չ����֮һ��
2��@ExeceptionHandlerͳһ����ĳһ���쳣���Ӷ��ܹ����ٴ����ظ��ʺ͸��Ӷȣ�@ExceptionHandler(Throwable.class)ָ����Throwable���쳣��
3��@ResponseStatus ָ���ͻ����ܵ���Http״̬����������500���󣬿ͻ��˾ͻ���ʵ500����

### ����4������Ч��
```
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
```
���
```
{
  "status": 0,
  "desc": "�ɹ�",
  "data": {
    "status": 1000,
    "message": "ϵͳ�쳣�����Ժ�����",
    "exception": "java.lang.ArithmeticException",
    "errors": null
  }
}
```

## ��������ʵս�����Զ����쳣 ���� ����ȫ���쳣��������
### ����1����װһ���Զ����쳣
�Զ����쳣ͨ���Ǽ���
```

### ����2�����Զ����쳣  ���� ��ȫ���쳣����
ȫ���쳣������ֻҪլ�ϽڿεĻ����ϣ����һ���Զ����쳣���ɡ�
```
@ExceptionHandler(BusinessException.class)
public ErrorResult handleBusinessException(BusinessException e,HttpServletRequest request){
ErrorResult error = ErrorResult.builder().status(e.code)
	.message(e.message)
	.exception(e.getClass().getName())
	.build();
	log.warn("URL:{} ,�쳣ҵ��{}",request.getRequestURI(),error);
	return error;
}
```
### ����3 ������Ч��
```
{
  "status": 20001,
  "message": "�û����Ѵ���",
  "exception": "cn.boot.responsePro3.exception.BusinessException",
  "errors": null
}
```

## �� ����ʵս ���ѡ�ȫ���쳣�����������ɽ����ӿڷ���ֵͳһ��׼��ʽ��
Ŀ�꣺�ѡ�ȫ���쳣����������json��ʽת��Ϊ���ӿڷ���ͳһ��׼��ʽ����ʽ
```
{
  "status": 20001,
  "message": "�û����Ѵ���",
  "exception": "cn.boot.responsePro3.exception.BusinessException"
}
```
ת��
```
{
  "status": 20001,
  "message": "�û����Ѵ���",
  "data": null
}
```

### ����1 ������ResponseHandler
```
@ControllerAdvice(basePackages = "cn.boot.responsepro")
@Slf4j
public class ResponseHandler1 implements ResponseBodyAdvice<Object> {

    /**
     * �Ƿ�֧��advice����
     * true=֧�֣�false=��֧��
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * ����response�ľ���ҵ�񷽷�
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
        log.info("----����ControllerAdvice---");
        if(o instanceof String){
            return JSON.toJSONString(Result.suc(o));
        }
        return Result.suc(o);
    }
}
```
�� ���ӿڷ���ֵͳһ��׼��ʽ�� �Ĵӻ�����
```
if(o instanceof ErrorResule){
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
```
### ����2 ������Ч��
```
@ApiOperation("�׳��쳣")
@PostMapping(value = "/error")
public void error3(){
	throw new BusinessException(ResultCode.USER_MAS_EXISTED);
}
```
���
```
{
  "status": 20001,
  "desc": "�û����Ѵ���",
  "data": null
}
```