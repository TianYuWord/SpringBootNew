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
