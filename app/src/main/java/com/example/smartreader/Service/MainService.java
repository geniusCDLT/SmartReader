package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.util.ArrayList;

public interface MainService {
    //获取用户书架分类
    ArrayList<String> GetFolderNames(User user);
    //获取用户某书架分类下的小说id列表
    ArrayList<Integer> GetFolderBookIds(User user, String folderName);
    //创建收藏夹
    Boolean CreateNewFolder(User user, String folderName);
    //修改收藏夹名称
    Boolean RenameFolder(User user, String OldName, String NewName);
    //删除收藏夹
    Boolean DeleteFolder(User user, String folderName);
    //获取书库中某类小说的数量
    Integer GetXBookNum(String type);
}
