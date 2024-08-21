package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.UserDao;
import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import com.serena.springbootmall.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserServerImpl implements UserServer {
    @Autowired
    UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.register(userRegisterRequest);
    }

    @Override
    public User getById(Integer userId) {
        return userDao.getById(userId);
    }
}
