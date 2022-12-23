package com.example.smartreader.Service.impl;

import com.example.smartreader.Service.InfoService;
import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoServiceImpl implements InfoService {
    private Connection con=null ;
    public InfoServiceImpl(){
        con = JDBCUtils.getConn();
    }

    /**
     * 用户是否收藏某小说
     * @param user 用户
     * @param book 小说
     * @return
     */
    @Override
    public Boolean IfCollected(User user, Book book) {

        String sql="select * from collect where userid = ? and novelId = ?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setInt(2,book.getId());
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
     * 用户收藏某小说到某书架收藏夹中
     * @param user 用户
     * @param book 小说
     * @param folderName 收藏夹名称
     * @return 是否收藏成功
     */
    @Override
    public Boolean CollectBook(User user, Book book, String folderName) {
        Boolean isSuccess1=false;
        Boolean isSuccess2=false;
        String sql = "insert into collect(userid,novelId,folderName) values (?,?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setInt(2,book.getId());
            pst.setString(3,folderName);
            int value = pst.executeUpdate();
            if(value>0){
                book.OnePlusCltNum();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //小说表中的收藏数+1
        String sql2 = "update novel set cltNum=cltNum+1 where id=?";
        try {
            PreparedStatement pst=con.prepareStatement(sql2);
            pst.setInt(1,book.getId());
            int value = pst.executeUpdate();
            if(value>0){
                book.OneMinusCltNum();
                isSuccess2=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (isSuccess1&&isSuccess2);
    }

    /**
     * 用户取消收藏
     * @param user 用户
     * @param book 小说
     * @return 是否取消收藏成功
     */
    @Override
    public Boolean DeleteCollection(User user, Book book) {
        Boolean isSuccess1=false;
        Boolean isSuccess2=false;
        String sql = "delete from collect where userid = ? and novelId = ?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setInt(2,book.getId());
            int value = pst.executeUpdate();
            if(value>0){
                book.OneMinusCltNum();
                isSuccess1=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //小说表中的收藏数-1
        String sql2 = "update novel set cltNum=cltNum-1 where id=?";
        try {
            PreparedStatement pst=con.prepareStatement(sql2);
            pst.setInt(1,book.getId());
            int value = pst.executeUpdate();
            if(value>0){
                book.OneMinusCltNum();
                isSuccess2=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (isSuccess1&&isSuccess2);
    }
}
