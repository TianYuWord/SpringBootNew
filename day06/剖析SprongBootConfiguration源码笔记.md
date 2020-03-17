#����SpringBootConfigurationԴ��
## һ�����ڿ�Ŀ��
3��Ŀ��
1������@SpringBootConfigurationԴ�룬����@SpringBootConfiguration��ԭ��
2������@Configuration��ԭ����ѧ������ú�@Configuration��
3������@Bean��ԭ����ѧ�����ʹ�ú�@Bean��

##��������@SpringBootConfigurationԴ��
�Ͻڿ������Ѿ�֪���� @SpringBootApplication��һ������ע�⣬
����@ComponentScan����@SpringBootConfiguration��@EnableAutoConfiguration��
��ڿΣ����Ǿ���ѧϰ@SpringBootConfiguration
�ȿ�@SpringBootConfiguration��Դ�롣
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {
    @AliasFor(
        annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}
```

##����ʲô��@Configurationע�⣬����ʲô���ã�
��Spring3.0��@Configuration���ڶ��������࣬�����滻xml�����ļ�����ע������ڲ�����һ��������@Beanע��ķ�����
��Щ�������ᱻAnnotationConfigApplicationContext��AnnotationConfigWebApplicationContext�����ɨ�裬
�����ڹ���bean���壬��ʼ��Spring������
## ������@Configuration����Spring������Spring����
@Configuration��ע�����ϣ�@Configuration�ȼ���spring��xml�����ļ��е�<Beans></Beans>

### ����1���ȼ���Spring��������
```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```
### ����2������һ��Configuration��
```
@Configuration
public class MyConfiguration {

}
```
���ϴ����ͬ�ڣ�һ�µ�xml  �������ϡ�SpringBootConfigurationSource����Ŀ����
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
        
</beans>
```

### ����3����һ��������
```
public class SpringBootConfigurationSource {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }
}
```
���
```
MyConfiguration����������ʼ��������
```

## �ģ���ΰ�һ������ע�ᵽSpring IOC ������
Ҫ��һ������ע�ᵽSpringIOC�����У�һ������@Bean ע����ʵ��

@Bean�����ã�����@Bean��ע�᷽��������һ�����󣬸ö�����ñ�ע����SpringIOC�����С�

### ����1������һ��bean

```
@Data
public class UserBean {
    private Integer id;
    private String username;
    private String password;
}
```
### ����2����beanע����IOC����
```
@Configuration
public class MyConfiguration {


    public MyConfiguration() {
        System.out.println("MyConfiguration����������ʼ��������");
    }

    @Bean("user1")
    public UserBean userBean(){
        UserBean userBean = new UserBean();
        userBean.setUsername("xiaoyu");
        userBean.setPassword("123456");
        return userBean;
    }

}
```
����Ĵ����ͬ�������XML����
```
<beans>
	<bean id="user1" class="cn.boot.model.UserBean">
	    <property name="username" value="xiaoyu"></property>
	    <property name="password" value="123456"></property>
	</bean>
</beans>
```
### ����3����һ��������
������bean���󣬿���ͨ��AnnotationConfigApplicationContext���ؽ�spring ioc �����С�
```
public class SpringBootConfigurationSource {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean bean = applicationContext.getBean("user1", UserBean.class);
        System.out.println(bean);
    }
}
```
���
```
MyConfiguration����������ʼ��������
UserBean(id=null, username=xiaoyu, password=123456)
```