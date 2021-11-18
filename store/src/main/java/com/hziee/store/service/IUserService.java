package com.hziee.store.service;

import com.hziee.store.entity.User;

//用户模块业务接口
public interface IUserService {
    void reg(User user);
    User login(String username, String password);
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);
    User getByUid(Integer uid);

    void updateUserInfo(Integer uid, String username, User user);

    /**
     * @param uid
     * @param avatar
     * @param username
     */
    void updateAvatar(Integer uid, String avatar, String username);

}
