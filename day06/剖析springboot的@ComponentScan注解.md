#����springboot��@ComponentScanע��
## һ�����ڿ�Ŀ��
1�����SpringBoot��@ComponentScanע������
2��ѧ����@ComponentScan

## ��������springboot��@ComponentScanע��
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
excludeFilters : ���˲���Ҫɨ������͡�
@Filter ����ע��
FilterType.CUSTOM ��������Ϊ�Զ�����򣬼�ָ�ض���class
classes������ָ����class�����޳���TypeExcludeFilter.class,AutoConfigurationExcludeFilter.class

������Դ�룬���ǿ��Եó����ۣ�
1��@SpringBootApplication��Դ�������@ComponentScan���ʣ�ֻҪ@SpringBootApplicationע�����ڵİ���
�����¼��������Ὣclassɨ�貢װ��springbean����
2��������Զ���һ��SpringBean������@SpringBootApplicationע�����ڵİ������¼�����
�������ֶ�����@ComponentScanע�Ⲣָ���ĸ�bean���ڵİ�

## ����ΪʲôҪ��@ComponentScan���������ʲô���⣿
1��ΪʲôҪ��@ComponentScan��
����һ��SpringBean һ��ʵ�����ϼ���ע�� @Service ��#Controller ��@Component�Ϳ��ԣ����ǣ�spring��ô֪�������Bean�ֵô����أ�
�ʣ����Ǳ������springȥ���������bean�ࡣ
@ComponentScan������������springȥ���������bean�ࡣ
2��@componentscan������
���ã�����springȥɨ��@ComponentScanָ���������е�ע���࣬Ȼ��ɨ�赽����װ��springbean���������Ժܺõ�����
���磺@ComponentScan("cn.boot.controller")����ֻ��ɨ��cn.boot.controller���µ�ע���ࡣ
�����д������@SpringBootApplication��@ComponentScanû��ָ��·������ȥ�����ң�
@SpringBootApplicationע�����ڵİ������¼��������Ὣclassɨ�貢װ��spring ioc����

## �� ����ʵս�� ����@ComponentScan������
### ����1���ڰ���Ϊcn.boot.service��һ��ComponentScan������
```
package cn.boot.componentscansource.service;

import cn.boot.componentscansource.model.UserBean;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //ģ��ҵ�񳡾�
    public UserBean getUserById(){
        UserBean userBean = new UserBean();
        userBean.setUsername("xiaoyu");
        userBean.setPassword("123456");
        return userBean;
    }
}

```
### ����2����cn.boot.componentscansource.Main ���潨һ��������
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
��������
```
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'cn.boot.componentscansource.service.UserService' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:351)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:342)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1126)
	at cn.boot.componentscansource.Main.ComponentscansourceApplication.main(ComponentscansourceApplication.java:14)
```
���ϱ������˼�ǣ��Ҳ���cn.boot.componentscansource.service.UserService ���bean������ô���أ�
��Ҫ�����д����������м���
�ֹ�ָ����·��
```
@ComponentScan("cn.boot.componentscansource.service")
```
�������£�
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
������������

## ��������ֹ�ָ����·���������
```
package cn.boot.componentscansource;

import cn.boot.componentscansource.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//Ĭ��ɨ��ƽ�������¼���
@SpringBootApplication
public class ComponentscansourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ComponentscansourceApplication.class, args);
        UserService bean = run.getBean(UserService.class);
        System.out.println(bean.getUserById());
    }

}
```
ע�������ʵ�ڲ��о͵���Ŀ�ڲ鿴

## �壺�κ���ϰ
�ڱ���Ŀ�Ļ����ϣ�������ʹ��@ComponentScan��ָ��ɨ���·��
����֮ǰ�����ܶ࣬����Ͳ�����ʾ�ˡ�
����������ϵQQ2949852842