package com.example.smartreader.Service.impl;

import com.example.smartreader.Service.MineService;
import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MineServiceImpl implements MineService {
    //连接数据库
    private Connection con=null ;
    public MineServiceImpl(){
        con = JDBCUtils.getConn();
    }

    /**
     * 修改用户密码
     * @param user 用户
     * @param NewPwd 新密码
     * @return
     */
    @Override
    public Boolean ModifyPwd(User user, String NewPwd) {
        String sql = "update user set UserPassword = ? where id = ?";
        if (con ==null){
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(""+con);
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,NewPwd);
            pst.setInt(2,user.getUserid());
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
