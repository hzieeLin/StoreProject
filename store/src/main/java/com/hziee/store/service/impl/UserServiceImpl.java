package com.hziee.store.service.impl;

import com.hziee.store.entity.User;
import com.hziee.store.mapper.UserMapper;
import com.hziee.store.service.IUserService;
import com.hziee.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

//用户模块业务的实现类
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUserName(username);
        if(result != null) {
            throw new UsernameDuplicatedException("用户名被占用！");
        }
        //密码加密算法 串+pwd+串
        // 盐值 + pwd + 盐值
        String oldPwd = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        //将密码和颜值作为一个整体
        String md5Password = getMD5Password(oldPwd, salt);
        user.setPassword(md5Password);
        //补全数据：is_delete 设置为0
        user.setIsDelete(0);
        //不全数据4个日志字段
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //执行注册业务的功能实现
        Integer rows = userMapper.insert(user);
        if( rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUserName(username);
        if(result == null) {
            throw new UserNotFoundException("用户数据不存在");

        }
        //1先获取到数据库中加密之后的密码比较
        String oldPassword = result.getPassword();
        //获取盐值
        String salt = result.getSalt();
        //2将用户密码按照相同的加密算法进行加密后比较
        String newPassword = getMD5Password(password,salt);
        if (!oldPassword.equals(newPassword)){

            throw new PasswordNotMatchException("用户密码错误");
        }
//        System.out.println("old"+oldPassword);
//        System.out.println("new"+newPassword);
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String Oldmd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(Oldmd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        String Newmd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, Newmd5Password, username, new Date());
        if( rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if( result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void updateUserInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if( result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateUserInfoByUid(user);
        if(rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }
    }

    @Override
    public void updateAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if( result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if( rows != 1) {
            throw new UpdateException("更新头像产生异常");
        }
    }

    //加密算法
    private String getMD5Password(String password, String salt) {
        //md5加密算法
        for (int i = 0; i < 3 ; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
