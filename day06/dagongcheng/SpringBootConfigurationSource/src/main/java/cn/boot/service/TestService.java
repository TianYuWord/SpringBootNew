package cn.boot.service;

import cn.boot.model.UserBean;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    //模拟业务场景
    public UserBean getUserById(){
        UserBean userBean = new UserBean();
        userBean.setUsername("xiaoyu");
        userBean.setPassword("123456");
        return userBean;
    }

}