#剖析springboot的@ComponentScan注解
## 一：本节课目标
1：理解SpringBoot的@ComponentScan注解作用
2，学会用@ComponentScan

## 二，剖析springboot的@ComponentScan注解
```
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
```
excludeFilters : 过滤不需要扫描的类型。
@Filter 过滤注解
FilterType.CUSTOM 过滤类型为自定义规则，即指特定的class
classes：过滤指定的class，即剔除了TypeExcludeFilter.class,AutoConfigurationExcludeFilter.class

从以上源码，我们可以得出结论：
1：@SpringBootApplication的源码包含了@ComponentScan，故，只要@SpringBootApplication注解所在的包，
及其下级包，都会将class扫描并装入springbean容器
2，如果你自定义一个SpringBean，不再@SpringBootApplication注解所在的包及其下级包，
都必须手动加上@ComponentScan注解并指定哪个bean所在的包

## 三，为什么要用@ComponentScan？他解决了什么问题？
1，为什么要用@ComponentScan？
定义一个SpringBean 一般实在类上加上注解 @Service 或#Controller 或@Component就可以，但是，spring怎么知道你这个Bean抵得存在呢？
故，我们必须告诉spring去哪里找这个bean类。
@ComponentScan就是用来告诉spring去哪里找这个bean类。
2，@componentscan的作用
作用：告诉spring去扫描@ComponentScan指定包下所有的注解类，然后将扫描到的类装入springbean容器。可以很好的区分
例如：@ComponentScan("cn.boot.controller")，就只能扫描cn.boot.controller包下的注解类。
如果不写？就像@SpringBootApplication的@ComponentScan没有指定路径？它去那里找？
@SpringBootApplication注解所在的包及其下级包，都会将class扫描并装入spring ioc容器

## 四 案例实战： 体验@ComponentScan的作用
### 步骤1：在包名为cn.boot.service建一个ComponentScan测试类
```
package cn.boot.componentscansource.service;

import cn.boot.componentscansource.model.UserBean;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //模拟业务场景
    public UserBean getUserById(){
        UserBean userBean = new UserBean();
        userBean.setUsername("xiaoyu");
        userBean.setPassword("123456");
        return userBean;
    }
}

```
### 步骤2：在cn.boot.componentscansource.Main 下面建一个启动类
```
package cn.boot.componentscansource.Main;

import cn.boot.componentscansource.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@ComponentScan("cn.boot.componentscansource.service")
public class ComponentscansourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ComponentscansourceApplication.class, args);
        UserService bean = run.getBean(UserService.class);
        System.out.println(bean.getUserById());
    }

}

```
启动报错：
```
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'cn.boot.componentscansource.service.UserService' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:351)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:342)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1126)
	at cn.boot.componentscansource.Main.ComponentscansourceApplication.main(ComponentscansourceApplication.java:14)
```
以上报错的意思是：找不到cn.boot.componentscansource.service.UserService 这个bean，那怎么办呢？
这要加这行代码重新运行即可
手工指定包路径
```
@ComponentScan("cn.boot.componentscansource.service")
```
整体如下：
```
@SpringBootApplication
@ComponentScan("cn.boot.componentscansource.service")
public class ComponentscansourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ComponentscansourceApplication.class, args);
        UserService bean = run.getBean(UserService.class);
        System.out.println(bean.getUserById());
    }

}
```
以上启动正常

## 如果不想手工指定包路径如何做？
```
package cn.boot.componentscansource;

import cn.boot.componentscansource.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//默认扫描平级包和下级包
@SpringBootApplication
public class ComponentscansourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ComponentscansourceApplication.class, args);
        UserService bean = run.getBean(UserService.class);
        System.out.println(bean.getUserById());
    }

}
```
注意包名，实在不行就到项目内查看

## 五：课后练习
在本项目的基础上，隔包，使用@ComponentScan来指定扫描包路径
由于之前做过很多，这里就不作演示了。
如有问题联系QQ2949852842