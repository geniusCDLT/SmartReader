package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;
import com.example.smartreader.Service.MainService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainServiceImpl implements MainService {
    //连接数据库
    private Connection con=null ;
    public MainServiceImpl(){
        con = JDBCUtils.getConn();
    }

    /**
     * 获取用户书架分类
     * @param user 用户
     * @return 用户收藏夹名称列表
     */
    @Override
    public ArrayList<String> GetFolderNames(User user) {
        ArrayList<String> FolderNames=new ArrayList<>();
        String sql="select folderName from folder where UserId = ?";
        if (con ==null){
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(""+con);
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String folderName=rs.getString(2);
                FolderNames.add(folderName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FolderNames;
    }

    /**
     * 获取用户某书架分类下的小说列表
     * @param user 用户
     * @param folderName 收藏夹（即书架分类）名称
     * @return
     */
    @Override
    public ArrayList<Integer> GetFolderBookIds(User user, String folderName) {
        ArrayList<Integer> FolderBookIds=new ArrayList<>();
        String sql="select novelId from collect where userid = ? and folderName = ?";
        if (con ==null){
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(""+con);
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            pst.setString(2,folderName);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int novelId=rs.getInt(3);
                FolderBookIds.add(novelId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 创建收藏夹
     * @param user 用户
     * @param folderName 创建的收藏夹名称
     * @return 是否创建成功
     */
    @Override
    public Boolean CreateNewFolder(User user, String folderName) {
        String sql = "insert into folder(UserId, folderName) values (?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            pst.setString(2,folderName);
            int value = pst.executeUpdate();
            if(value>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 修改收藏夹名称
     * @param user 用户
     * @param OldName 旧收藏夹名称
     * @param NewName 新收藏夹名称
     * @return
     */
    @Override
    public Boolean RenameFolder(User user, String OldName, String NewName) {
        String sql = "update folder set folderName = ? where UserId = ? and folderName = ?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,NewName);
            pst.setString(2,user.getUserid().toString());
            pst.setString(3,OldName);
            int value = pst.executeUpdate();
            if(value>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 删除收藏夹（涉及收藏夹表和收藏小说表，级联删除）
     * @param user 用户
     * @param folderName 收藏夹名称
     * @return 是否删除成功
     */
    @Override
    public Boolean DeleteFolder(User user, String folderName) {
        String sql = "delete from folder where UserId = ? and folderName = ?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            pst.setString(2,folderName);
            int value = pst.executeUpdate();
            if(value>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 获取书库中某类小说的数量
     * @param type
     * @return
     */
    @Override
    public Integer GetXBookNum(String type) {
        return null;
    }


}
