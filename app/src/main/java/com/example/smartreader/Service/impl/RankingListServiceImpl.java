package com.example.smartreader.Service.impl;

import com.example.smartreader.Utils.JDBCUtils;
import com.example.smartreader.entity.Book;
import com.example.smartreader.Service.RankingListService;

import java.sql.*;
import java.util.ArrayList;

public class RankingListServiceImpl implements RankingListService {
    //连接数据库
    private Connection con=null ;
    public RankingListServiceImpl(){
        con = JDBCUtils.getConn();
    }
    /**
     * 获取分类排行榜
     * @param category 类别名称，若是总榜（全部类别）即为
     * @param k 排行榜小说数量
     * @return 类别对应收藏数前k排行榜
     */
    @Override
    public ArrayList<Book> GetCategoryRank(String category, int k) {
        ArrayList<Book> RankingBooks = new ArrayList<>();
        String sql="select * from novel ";
        //sql语句执行顺序：先where,再order by,再limit
        if(category!="总榜"){
            sql+="where noveltype='"+category+"' ";
        }
        sql+=("order by cltNum desc limit "+k);

        try {
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String author = rs.getString(4);
                String novelType = rs.getString(5);
                String cover = rs.getString(6);
                int cltNum = rs.getInt(7);

                Book book=new Book(id, title, desc, author, novelType, cover, cltNum);
                RankingBooks.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return RankingBooks;
    }

}
