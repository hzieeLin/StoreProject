package com.hziee.store.mapper;

import com.hziee.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块的持久层接口
public interface UserMapper {
    Integer insert(User user);
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);
    User findByUserName(String username);
    User findByUid(Integer uid);
    Integer updateUserInfoByUid(User user);
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
