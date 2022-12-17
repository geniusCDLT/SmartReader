package com.example.smartreader.entity;

//用户类
public class User {
    private Integer userid;
    private String username;
    private String userPassword;
    private String email;

    public Integer getUserid(){
        return this.userid;
    }
    public void setUserid(Integer id){
        this.userid=id;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUserPassword(){
        return this.userPassword;
    }
    public void setUserPassword(String password){
        this.userPassword=password;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }
}
