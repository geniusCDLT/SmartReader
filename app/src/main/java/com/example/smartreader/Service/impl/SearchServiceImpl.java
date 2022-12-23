package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;
import com.example.smartreader.Service.SearchService;

import java.sql.*;
import java.util.ArrayList;

public class SearchServiceImpl implements SearchService {
    //连接数据库
    private Connection con=null ;
    public SearchServiceImpl(){
        con = JDBCUtils.getConn();
    }

    /**
     * 获取搜索书籍（最优and匹配）
     * 对于id和作者查询采取明确查询，对于书名查询采取模糊查询
     * @param scontent 查询内容
     * @param swhat 查询类别：1为书名，2为作者，3为id
     * @return
     */
    @Override
    public ArrayList<Book> GetSearchBooks(String scontent, int swhat) {
        ArrayList<Book> SearchBooks = new ArrayList<>();
        String sql="select * from novel where ";
        switch (swhat){
            case 1://书名
                sql+=("title like '%"+scontent+"%'");
                break;
            case 2://作者
                sql+=("author = '"+scontent+"'");
                break;
            case 3://id
                sql+=("id = '"+scontent+"'");
                break;
        }
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String author = rs.getString(4);
                String novelType = rs.getString(5);
                Blob cover = rs.getBlob(6);
                int cltNum = rs.getInt(7);

                Book book=new Book(id, title, desc, author, novelType, cover, cltNum);
                SearchBooks.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return SearchBooks;
    }
}
