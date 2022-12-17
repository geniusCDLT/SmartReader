package com.example.smartreader.Service.impl;

import com.example.smartreader.entity.Book;
import com.example.smartreader.Service.RankingListService;

import java.util.ArrayList;

public class RankingListServiceImpl implements RankingListService {
    /**
     * 获取分类排行榜
     * @param category 类别名称，若是总榜（全部类别）即为
     * @return 类别对应收藏数前百排行榜
     */
    @Override
    public ArrayList<Book> GetCategoryRank(String category) {
        return null;
    }
}
