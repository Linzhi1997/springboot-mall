package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getById(Integer userId);

    User getByEmail(String email);
}
