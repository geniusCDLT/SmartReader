package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;
import com.example.smartreader.Service.MainService;

import java.sql.Blob;
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
            pst.setInt(1,user.getUserid());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                String folderName=rs.getString(1);
                FolderNames.add(folderName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FolderNames;
    }

    /**
     * 将小说加入对应收藏夹中
     * @param user 用户信息
     * @param folderName 收藏夹名
     * @param book 小说信息
     * @return 返回是否成功
     */
    @Override
    public boolean CollectBooks(User user,String folderName,Book book){
        String sql="insert into collect(userid,novelId,folderName) values (?,?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setInt(2,book.getId());
            pst.setString(3,folderName);
            int value = pst.executeUpdate();
            if(value>0){
                System.out.println("加入成功");
                return true;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户某书架分类下的小说列表
     * @param user 用户
     * @param folderName 收藏夹（即书架分类）名称
     * @return 返回小说列表
     */
    @Override
    public ArrayList<Book> GetFolderBooks(User user, String folderName) {
        ArrayList<Book> FolderBooks=new ArrayList<>();
        String sql="select * from novel where id in (select novelId from collect where userid = ? and folderName = ?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            pst.setString(2,folderName);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                System.out.println("hhhh1");
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String author = rs.getString(4);
                String novelType = rs.getString(5);
                String cover = rs.getString(6);
                int cltNum = rs.getInt(7);
                Book book=new Book(id, title, desc, author, novelType, cover, cltNum);
                FolderBooks.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FolderBooks;
    }

    /**
     * 判断用户是否收藏了该小说
     * @param user 用户信息
     * @param book 小说信息
     * @return 返回boolean
     */
    public boolean ifCollectBooks(User user,Book book){
        String sql="select * from collect where userid=? and novelId=?";
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
     * 创建收藏夹（不能重名）
     * @param user 用户
     * @param folderName 创建的收藏夹名称
     * @return 是否创建成功
     */
    @Override
    public Boolean CreateNewFolder(User user, String folderName) {
        String sql = "insert into folder(UserId, folderName) values (?,?)";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
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
     * 修改收藏夹名称（不能重名）
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
            pst.setInt(2,user.getUserid());
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
            pst.setInt(1,user.getUserid());
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
     * 获取用户某收藏夹中小说总数
     * @param user 用户
     * @param folderName 收藏夹名称
     * @return
     */
    @Override
    public Integer GetXFolderBookNum(User user, String folderName) {
        String sql="select count(folderName) as b from folder where UserId=? and folderName=?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getUserid().toString());
            pst.setString(2,folderName);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取书库中某类小说的数量
     * @param type
     * @return
     */
    @Override
    public Integer GetXBookNum(String type) {
        String sql="select count(noveltype=?) as a from novel";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,type);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * 取消收藏
     * @param user 用户
     * @param book 小说信息
     * @return 返回boolean
     */
    public boolean deleteCollectBooks(User user,Book book){
        String sql="delete from collect where userid=? and novelId=?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,user.getUserid());
            pst.setInt(2,book.getId());
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
     * 修改分类
     * @param user 用户
     * @param book 小说信息
     * @param folderName 新书架
     * @return 返回修改结果是否成功
     */
    public boolean updateCollectBooks(User user,Book book,String folderName){
        String sql = "update collect set folderName = ? where userid = ? and novelId = ?";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,folderName);
            pst.setInt(2,user.getUserid());
            pst.setInt(3,book.getId());
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
