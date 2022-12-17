package com.example.smartreader.Service.impl;

import com.example.smartreader.Service.InfoService;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

public class InfoServiceImpl implements InfoService {
    /**
     * 用户是否收藏某小说
     * @param user 用户
     * @param book 小说
     * @return
     */
    @Override
    public Boolean IfColleted(User user, Book book) {
        return null;
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
        return null;
    }

    /**
     * 用户取消收藏
     * @param user 用户
     * @param book 小说
     * @return 是否取消收藏成功
     */
    @Override
    public Boolean DeleteCollection(User user, Book book) {
        return null;
    }
}
