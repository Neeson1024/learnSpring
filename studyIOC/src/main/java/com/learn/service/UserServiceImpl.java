package com.learn.service;

import com.learn.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void find() {
        System.out.println("service");
        userDao.query();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
