package cn.boot.Main;

import cn.boot.config.MyConfiguration;
import cn.boot.model.UserBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 讲解SpringBootApplication注解的实现之一 @SpringBootConfiguration
 */

public class SpringBootConfigurationSource {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean bean = applicationContext.getBean("user1", UserBean.class);
        System.out.println(bean);
    }
}
