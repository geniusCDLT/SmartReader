package com.example.smartreader.Service;

import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;

import java.util.List;

public interface CatalogService {


    //获取小说对应的所有章节列表
    List<Chapter> GetAllChapter(Integer novelId);

    //获取章节信息
    Chapter GetChapter(String title,Integer id);



}
