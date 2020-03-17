#剖析SpringBootConfiguration源码
## 一：本节课目标
3个目标
1，剖析@SpringBootConfiguration源码，掌握@SpringBootConfiguration的原理
2，分析@Configuration的原理，并学会如何用好@Configuration？
3，分析@Bean的原理，并学会如何使用好@Bean？

##二：剖析@SpringBootConfiguration源码
上节课我们已经知道了 @SpringBootApplication是一个复合注解，
包括@ComponentScan，和@SpringBootConfiguration，@EnableAutoConfiguration。
这节课，我们就来学习@SpringBootConfiguration
先看@SpringBootConfiguration的源码。
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

##三，什么是@Configuration注解，他有什么作用？
从Spring3.0，@Configuration用于定义配置类，可以替换xml配置文件，被注解的类内部包含一个或多个被@Bean注解的方法。
这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
并用于构建bean定义，初始化Spring容器。
## 三，用@Configuration配置Spring并加载Spring容器
@Configuration标注在类上，@Configuration等价于spring的xml配置文件中的<Beans></Beans>

### 步骤1：先加入Spring的依赖包
```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```
### 步骤2：创建一个Configuration类
```
@Configuration
public class MyConfiguration {

}
```
以上代码等同于，一下的xml  这里请结合《SpringBootConfigurationSource》项目来看
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
        
</beans>
```

### 步骤3：加一个测试类
```
public class SpringBootConfigurationSource {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }
}
```
结果
```
MyConfiguration容器启动初始化。。。
```

## 四：如何把一个对象注册到Spring IOC 容器中
要把一个对象注册到SpringIOC容器中，一般是用@Bean 注解来实现

@Bean的作用：带有@Bean的注册方法将返回一个对象，该对象因该被注册在SpringIOC容器中。

### 步骤1：创建一个bean

```
@Data
public class UserBean {
    private Integer id;
    private String username;
    private String password;
}
```
### 步骤2：把bean注解在IOC容器
```
@Configuration
public class MyConfiguration {


    public MyConfiguration() {
        System.out.println("MyConfiguration容器启动初始化。。。");
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
上面的代码等同于下面的XML配置
```
<beans>
	<bean id="user1" class="cn.boot.model.UserBean">
	    <property name="username" value="xiaoyu"></property>
	    <property name="password" value="123456"></property>
	</bean>
</beans>
```
### 步骤3：加一个体验类
创建的bean对象，可以通过AnnotationConfigApplicationContext加载进spring ioc 容器中。
```
public class SpringBootConfigurationSource {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean bean = applicationContext.getBean("user1", UserBean.class);
        System.out.println(bean);
    }
}
```
结果
```
MyConfiguration容器启动初始化。。。
UserBean(id=null, username=xiaoyu, password=123456)
```