package com.boot.springfactories.config;

import com.boot.springfactories.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public User user(){
        System.out.println("configuration");
        return new User();
    }

}
