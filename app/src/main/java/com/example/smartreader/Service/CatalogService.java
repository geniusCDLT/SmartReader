package com.example.smartreader.Service;

import com.example.smartreader.entity.Chapter;

public interface CatalogService {
    //获取章节信息
    Chapter GetChapter(String title);
}
