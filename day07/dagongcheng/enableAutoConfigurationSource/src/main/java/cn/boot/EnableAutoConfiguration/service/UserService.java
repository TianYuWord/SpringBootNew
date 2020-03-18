package cn.boot.EnableAutoConfiguration.service;

import cn.boot.EnableAutoConfiguration.annotation.EnableUserBean;
import cn.boot.EnableAutoConfiguration.model.RoleBean;
import cn.boot.EnableAutoConfiguration.model.UserBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
//@Import({UserBean.class, RoleBean.class})
@EnableUserBean
public class UserService {
}
