package cn.boot.bootapplication.bootapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootConfiguration
@EnableAutoConfiguration
public class BootapplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootapplicationApplication.class, args);
    }

}