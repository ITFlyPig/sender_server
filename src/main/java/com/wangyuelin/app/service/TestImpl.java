package com.wangyuelin.app.service;

import com.wangyuelin.app.bean.Usert;
import com.wangyuelin.app.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestImpl {

    private UserDao userDao;

    public Usert getUser() {
        Usert user = userDao.getUserById(2);
        return user;
    }

    public List<Usert> getAll() {
        return userDao.selectAll();
    }
}
