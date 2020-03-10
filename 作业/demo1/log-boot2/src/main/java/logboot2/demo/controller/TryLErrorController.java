package logboot2.demo.controller;

import logboot2.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TryLErrorController {

    @Value("${xiaoyu.msg}")
    private String msg;

    //private static final Logger logger = LoggerFactory.getLogger(TryLErrorController.class);

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }

    @RequestMapping("/user")
    public User userinfo(){
        User user = new User();
        user.setId(1);
        user.setUsername("tianyu");
        user.setSex((byte) 1);
        return user;
    }

    @GetMapping("/errorpage")
    public String Error(){

        try {
            int i = 9/0;
            System.out.println(i);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return "error";
    }
}
