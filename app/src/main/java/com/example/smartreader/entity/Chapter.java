package com.example.smartreader.entity;

import java.io.Serializable;
import java.sql.Time;

//小说章节实体类
public class Chapter implements Serializable {
    private Integer id;
    private String title;
    private Integer novelId;
    private Time cTime;//创建时间
    private Time mTime;//修改时间
    private String content;//章节内容

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
    public Integer getNovelId(){
        return this.novelId;
    }
    public void setNovelId(Integer novelId){
        this.novelId=novelId;
    }
    public Time getcTime(){
        return this.cTime;
    }
    public void setcTime(Time cTime){
        this.cTime=cTime;
    }
    public Time getmTime(){
        return this.mTime;
    }
    public void setmTime(Time mTime){
        this.mTime=mTime;
    }
    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content=content;
    }
}
