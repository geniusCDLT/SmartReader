package com.example.smartreader.entity;

import java.io.Serializable;
import java.sql.Blob;

public class Book implements Serializable {
    private Integer id;
    private String title;//标题
    private String desc;//简介
    private String author;//作者
    private String novelType;//小说类型
    private Blob cover;//封面图片
    private int cltNum;//收藏人数

    public Book(){};
    public Book(int id, String title, String desc, String author, String novelType, Blob cover, int cltNum){
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.author=author;
        this.novelType=novelType;
        this.cover=cover;
        this.cltNum=cltNum;
    }

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

    public int getCltNum() {
        return cltNum;
    };

    public void setCltNum(int cltNum) {
        this.cltNum = cltNum;
    }
    //收藏数+1
    public void OnePlusCltNum(){
        this.cltNum += 1;
    }
    //收藏数-1
    public void OneMinusCltNum(){
        this.cltNum -= 1;
    }
}
