package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.util.ArrayList;

public interface SearchService {
    //获取最热搜索书籍，直接用RankingListImpl的GetCategoryRank吧
    //不用推荐搜索了，，，好麻烦，咱也没有那么多用户支撑去建相似度矩阵，后期有时间再说吧，，，
    //ArrayList<Book> GetHotBooks();
    //获取推荐搜索书籍
    //ArrayList<Book> GetSuitableBooks(User user);
    //获取搜索书籍（最优and匹配）
    ArrayList<Book> GetSearchBooks(String scontent, int swhat);
}
