package com.example.smartreader.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getConn(){
        Connection conn=null;
        try{
            conn= DriverManager.getConnection("jdbc:mysql://43.143.213.47/novelread","root","20020906txl");
            System.out.println("success connect");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
