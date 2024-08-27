package com.serena.springbootmall.dao.impl;

import com.serena.springbootmall.dao.UserDao;
import com.serena.springbootmall.dto.UserRegisterRequest;
import com.serena.springbootmall.model.User;
import com.serena.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO `user` (email,password,created_date,last_modified_date) " +
                " VALUES (:email,:password,:createdDate,:lastModifiedDate) ";
        Map<String,Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date now=new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer userId = keyHolder.getKey().intValue();
        return userId;
    }

    public User getById(Integer userId){
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM `user` " +
                "WHERE user_id=:userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0); // productList是搜尋後的結果
        }
        else {
            return null;
        }
    }

    public User getByEmail(String email){
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM `user` " +
                "WHERE email=:email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0); // productList是搜尋後的結果
        }
        else {
            return null;
        }
    }
}
