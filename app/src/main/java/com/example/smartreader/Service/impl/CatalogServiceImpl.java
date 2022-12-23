package com.example.smartreader.Service.impl;

import com.example.smartreader.Service.CatalogService;
import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogServiceImpl implements CatalogService {
    private Connection con=null ;
    public CatalogServiceImpl(){
        con = JDBCUtils.getConn();
    }

    /**
     *
     * @param title 章节名，即第几章***
     *
     * @return
     */
    @Override
    public Chapter GetChapter(String title,Integer id) {
        Chapter chapter=new Chapter();
        String sql="select * from chapter where title = ? and novel_id = ?";
        if (con ==null){
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(""+con);
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,title);
            pst.setInt(2,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                chapter.setId(rs.getInt(1));
                chapter.setTitle(rs.getString(2));
//                chapter.setcTime(rs.getTime(3));
//                chapter.setmTime(rs.getTime(4));
                chapter.setContent(rs.getString(7));
                System.out.println(rs.getString(7));
                System.out.println(chapter.getContent());
                return chapter;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
