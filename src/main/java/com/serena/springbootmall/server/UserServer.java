package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.UserLoginRequest;
import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServer {
    public Integer register(UserRegisterRequest userRegisterRequest);

    public User getById(Integer userId);

    public User login(UserLoginRequest userLoginRequest);
}
