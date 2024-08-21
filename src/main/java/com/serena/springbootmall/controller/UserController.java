package com.serena.springbootmall.controller;

import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import com.serena.springbootmall.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserServer userServer;
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Validated UserRegisterRequest userRegisterRequest){
        Integer userId = userServer.register(userRegisterRequest);

        User user = userServer.getById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/users/{userId}")
    public User getById(@PathVariable Integer userId){
        return userServer.getById(userId);
    }
}
