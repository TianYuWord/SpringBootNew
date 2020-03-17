package cn.boot.config;

import cn.boot.model.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
