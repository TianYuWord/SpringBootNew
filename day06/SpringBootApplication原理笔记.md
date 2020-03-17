# 解密SpringBootApplication启动原理
### 一：本课程目标
学习Spring Boot的核心注解@SpringBootApplication，掌握@SpringBootApplication的原理
### 二：剖析@SpringBootApplication源码
首先我们来分析SpringBoot的启动注解@SpringBootApplication
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication 
```
@Target({ElementType.TYPE}) 注解的目标位置：接口，类，枚举
@Retention(RetentionPolicy.RUNTIME) 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented 用于生产JavaDoc ，默认情况下，javadoc是不包含注解的，但是如果声明注解时指定了 @Documented ，则会被 JavaDoc 之类的工具处理，
 所以注解类型信息也会包括在生成的文档中。
@Inherited 作用：在类继承关系中，如果子类要继承父类的注解，那么要该注解必须被@Inherited修饰的注解
除了以上常规的几个注解，剩下的几个就是springboot的核心注解了。
@SpringBootApplication就是一个复合注解，包括@ComponentsScan，和@SpringBootConfiguration，@EnableAutoConfiguration

### 三：@SpringBootApplication就只干了一件事
以上所有注解就只干了一件事：把Bean注册到SpringIoc容器
通过3种方式来实现

1，@SpringBootConfiguration 通过@Configuration 与@Bean结合，注册带SpringIoc容器。
2，@ComponentsScan 通过范围的方式，扫描特定注解类，将其注册到Spring Ioc 容器
3，@SpringBootApplication 通过spring.factories的配置，来实现Bean的注册。


### 课后练习题
这节课，通过解读@SpringBootApplication，知道他是一个复合注解，<br/>
那请建一个springboot的项目，然后删除@SpringBootApplication，<br/>
用@ComponentScan，和SpringBootConfiguration，@EnableAutoConfiguration代替，然后启动springboot，看能不能启动成功？<br/>
