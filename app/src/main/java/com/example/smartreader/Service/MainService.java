package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.util.ArrayList;

public interface MainService {
    //获取用户书架分类
    ArrayList<String> GetFolderNames(User user);

    //获取用户某书架分类下的小说列表
    ArrayList<Book> GetFolderBooks(User user, String folderName);
    //创建收藏夹
    Boolean CreateNewFolder(User user, String folderName);
    //修改收藏夹名称
    Boolean RenameFolder(User user, String OldName, String NewName);
    //删除收藏夹
    Boolean DeleteFolder(User user, String folderName);
    //获取用户某收藏夹中小说总数
    Integer GetXFolderBookNum(User user, String folderName);
    //获取书库中某类小说的数量
    Integer GetXBookNum(String type);
    //收藏小说
    boolean CollectBooks(User user,String folderName,Book book);
    //判断用户是否收藏该小说
    boolean ifCollectBooks(User user,Book book);
    //取消该书收藏
    boolean deleteCollectBooks(User user,Book book);
    //修改分类
    boolean updateCollectBooks(User user,Book book,String folderName);
}
