package com.wangyuelin.app.service;

import com.wangyuelin.app.bean.User;
import com.wangyuelin.app.mapper.UserMapper;
import com.wangyuelin.app.service.itf.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUser {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);

    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }
}
