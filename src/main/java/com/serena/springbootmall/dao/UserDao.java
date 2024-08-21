package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;

public interface UserDao {
    public Integer createUser(UserRegisterRequest userRegisterRequest);

    public User getById(Integer userId);

    public User getByEmail(String email);
}
