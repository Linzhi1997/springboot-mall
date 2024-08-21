package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;

public interface UserServer {
    public Integer register(UserRegisterRequest userRegisterRequest);

    public User getById(Integer userId);
}
