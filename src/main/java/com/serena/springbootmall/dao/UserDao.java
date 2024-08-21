package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;

public interface UserDao {
    public Integer register(UserRegisterRequest userRegisterRequest);

    public User getById(Integer userId);
}
