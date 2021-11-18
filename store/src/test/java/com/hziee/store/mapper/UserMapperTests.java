package com.hziee.store.mapper;

import com.hziee.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
//Runwith 表示启动这个单元测试
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("test03");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUserName() {
        String username = "user01";
        User result = userMapper.findByUserName(username);
        System.out.println(result);
    }

    @Test
    public void UpdatePasswordByUid() {
        userMapper.updatePasswordByUid(7,"321","管理员", new Date());
    }

//    @Test
//    public void findByUid() {
////        System.out.println(userMapper.findByUid(7));
//    }
    @Test
    public void updateUserInfoByUid() {
        User user = new User();
        user.setUid(11);
        user.setPhone("19858104405");
        user.setEmail("1309@qq.com");
        user.setGender(1);
        userMapper.updateUserInfoByUid(user);

    }
}
