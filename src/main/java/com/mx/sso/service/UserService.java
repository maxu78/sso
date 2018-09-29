package com.mx.sso.service;

import com.mx.sso.dao.UserDao;
import com.mx.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void register(User user) {
        userDao.addUser(user);
    }

    public List<User> queryUser(User user) {
        return userDao.getAllUser(user);
    }
}
