package com.example.smartreader.Service;


import com.example.smartreader.entity.User;

public interface EnterService {
    //登录
    User login(String username, String pwd);
    //注册
    Boolean register(String username, String pwd);
}
