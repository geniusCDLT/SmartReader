package com.example.smartreader.entity;

//收藏小说类
public class CltBook {
    private Integer userid;//用户id
    private String folderName;//收藏夹名称
    private Integer novelId;//小说id

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

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer bookid) {
        this.novelId = bookid;
    }
}
