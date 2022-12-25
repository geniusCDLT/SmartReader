package com.example.smartreader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smartreader.R;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    //评论
    private ArrayList<String> comments = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        setModuleTitle();
    }

    //设置每个模块的标题
    private void setModuleTitle(){
        TextView plotTitle = findViewById(R.id.detail_plot_title).findViewById(R.id.module_title);
        plotTitle.setText("简介");

        TextView commentText = findViewById(R.id.detail_comment_title).findViewById(R.id.module_title);
        commentText.setText("评论");
    }

    //获取评论
    private void getCommentList(){
        RecyclerView comment_view = findViewById(R.id.detail_comment_recycler_view);

        comments.add("暂无数据");

    }

}