package com.serena.springbootmall.controller;

import com.serena.springbootmall.dto.UserLoginRequest;
import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import com.serena.springbootmall.server.UserServer;
import jakarta.validation.Valid;
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
    public ResponseEntity<User> getById(@PathVariable Integer userId){
        User user = userServer.getById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userServer.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
