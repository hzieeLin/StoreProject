package com.hziee.store.service;

import com.hziee.store.entity.User;
import com.hziee.store.mapper.UserMapper;
import com.hziee.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//Runwith 表示启动这个单元测试
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("user02");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass().getSimpleName());
        }
    }
    @Test
    public void login() {
        User user = userService.login("19858104405", "123456");
        System.out.println(user);
    }

    @Test
    public void ChangePassword() {
        userService.changePassword(11,"管理员","test03","123456");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(11));
    }
    @Test
    public void updateUserInfo() {
        User user = new User();
        user.setPhone("19858104405");
        user.setEmail("3232@qq.com");
        user.setGender(0);
        userService.updateUserInfo(11,"管理员",user);
    }
    @Test
    public void updateAvatar() {
        userService.updateAvatar(11,"/upload/xx","sda");
    }
}
