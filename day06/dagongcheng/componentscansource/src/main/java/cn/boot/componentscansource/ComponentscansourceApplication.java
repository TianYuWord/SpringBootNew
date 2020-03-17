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
