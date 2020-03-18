package cn.boot.EnableAutoConfiguration.main;

import cn.boot.EnableAutoConfiguration.model.RoleBean;
import cn.boot.EnableAutoConfiguration.model.UserBean;
import cn.boot.EnableAutoConfiguration.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("cn.boot.EnableAutoConfiguration")
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        UserBean userBean = applicationContext.getBean(UserBean.class);
        RoleBean roleBean = applicationContext.getBean(RoleBean.class);
        System.out.println(userService.toString());
        System.out.println(userBean.toString());
        System.out.println(roleBean);
    }
}