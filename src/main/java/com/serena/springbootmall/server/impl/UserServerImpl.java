package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.UserDao;
import com.serena.springbootmall.dto.UserLoginRequest;
import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import com.serena.springbootmall.server.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServerImpl implements UserServer {

    private final static Logger log = LoggerFactory.getLogger(UserServerImpl.class);
    @Autowired
    UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getByEmail(userRegisterRequest.getEmail());

        if(user!=null){
            // log 先寫
            log.warn("該 E-mail : {} 已經被註冊",userRegisterRequest.getEmail()); // 記錄警告信息
            // 噴出異常，設置 HTTP 狀態碼為 400 Bad Request，表示請求不合法或有錯誤
            // 異常拋出後，會直接中斷方法的執行
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getById(Integer userId) {
        return userDao.getById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getByEmail(userLoginRequest.getEmail());

        if(user==null){
            log.warn("帳號 : {} ,尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else{
            log.warn("帳號 : {}密碼不符,登入失敗",userLoginRequest.getEmail());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
