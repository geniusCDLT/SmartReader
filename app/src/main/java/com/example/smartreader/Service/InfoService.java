package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

public interface InfoService {
    //用户是否收藏某小说
    Boolean IfColleted(User user, Book book);
    //用户收藏某小说到某书架收藏夹中
    Boolean CollectBook(User user, Book book, String folderName);
    //用户取消收藏
    Boolean DeleteCollection(User user, Book book);
}
