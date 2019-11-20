package com.learn.dao;

import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    public void query() {
        System.out.println("query");
    }
}
