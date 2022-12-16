package com.example.smartreader.entity;

import java.sql.Blob;

//小说实体类
public class Novel {
    private Integer id;
    private String title;//标题
    private String desc;//简介
    private String author;//作者
    private String novelType;//小说类型
    private Blob cover;//封面图片

    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setDesc(String desc){
        this.desc=desc;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public String getNovelType(){
        return this.novelType;
    }
    public void setNovelType(String novelType){
        this.novelType=novelType;
    }
    public Blob getCover(){
        return this.cover;
    }
    public void setCover(Blob cover){
        this.cover=cover;
    }
}
