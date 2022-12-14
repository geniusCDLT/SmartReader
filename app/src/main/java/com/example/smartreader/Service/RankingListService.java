package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;

import java.util.ArrayList;

public interface RankingListService {
    //获取分类排行榜
    ArrayList<Book> GetCategoryRank(String category);
}
