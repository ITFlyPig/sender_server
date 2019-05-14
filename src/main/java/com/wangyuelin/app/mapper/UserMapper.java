package com.wangyuelin.app.mapper;

import com.wangyuelin.app.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserByPhone(@Param("phone") String phone);//校验手机号是否已被注册

    void save(User user);//保存用户信息

    User getUserByName(@Param("name") String userName);//获取用户的信息，用于登陆校验
}
