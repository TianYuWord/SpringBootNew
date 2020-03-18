package cn.boot.springfactoriescore.springfactoriescore;

import com.boot.springfactories.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringfactoriesCoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringfactoriesCoreApplication.class, args);
        User user = run.getBean(User.class);
        System.out.println(user.info());
    }

}
