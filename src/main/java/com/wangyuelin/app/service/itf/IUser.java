package com.wangyuelin.app.service.itf;

import com.wangyuelin.app.bean.User;

public interface IUser {

    User getUser(String phone);//校验手机号是否已被注册

    void save(User user);//保存用户信息

    User getUserByName(String userName);//获取用户的信息，用于登陆校验

    User getUserById(String id);


}
