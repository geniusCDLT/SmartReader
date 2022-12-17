package com.example.smartreader.entity;

//收藏夹类
public class CltFolder {
    private Integer userid;//用户id
    private String folderName;//收藏夹名称

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
