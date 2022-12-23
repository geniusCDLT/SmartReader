package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.User;
import com.example.smartreader.Service.EnterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterServiceImpl implements EnterService {
    //连接数据库
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
    //用户注册
    @Override
    public Boolean register(User user) {
        boolean isReg = false;
        String sql = "insert into user(username,UserPassword,email) values (?,?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getUserPassword());
            pst.setString(3,user.getEmail());
            int value = pst.executeUpdate();
            if(value>0){
                isReg = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(isReg){
            //为新用户创建初始收藏夹：“尚未分类”
            CreateInitialFolder(user.getUsername(), user.getUserPassword());
        }
        return isReg;
    }

    //寻找用户
    @Override
    public boolean FindUser(String username){
        String sql = "select * from user where username = ?";
        try {
            System.out.println(username);
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 为新用户创建初始收藏夹：“尚未分类”
     * @param username 用户用户名
     * @param pwd 用户密码
     */
    @Override
    public Boolean CreateInitialFolder(String username, String pwd) {
        User user = login(username, pwd);
        String sql = "insert into folder(UserId, folderName) values (?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setString(2,"尚未分类");
            int value = pst.executeUpdate();
            if(value>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
