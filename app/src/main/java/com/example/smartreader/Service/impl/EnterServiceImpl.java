package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.User;
import com.example.smartreader.Service.EnterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterServiceImpl implements EnterService {
    private Connection con=null ;
    public EnterServiceImpl(){
        con = JDBCUtils.getConn();
    }

    //登录
    @Override
    public User login(String username, String pwd) {
        String sql="select * from user where username = ? and UserPassword = ?";
        User user=new User();
        if (con ==null){
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(""+con);
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,pwd);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                Integer userid=rs.getInt(1);
                String email=rs.getString(4);
                user.setUsername(username);
                user.setUserid(userid);
                user.setEmail(email);
                user.setUserPassword(pwd);
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean register(String username, String pwd) {

        return null;
    }
}
