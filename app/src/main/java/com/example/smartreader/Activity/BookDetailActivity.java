package com.example.smartreader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartreader.R;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;
    private TextView Tv_bookName;
    private TextView Tv_bookAuthor;
    private TextView Tv_bookType;
    private TextView Tv_bookDesc;
    private Button btn_read;
    private Button btn_Category;
    private List<Chapter> chapterList;

    //评论
    private ArrayList<String> comments = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("book");
        chapterList= (List<Chapter>) intent.getSerializableExtra("chapterList");
        initView();
        Tv_bookType.setText(book.getNovelType());
        Tv_bookAuthor.setText(book.getAuthor());
        Tv_bookName.setText(book.getTitle());
        Tv_bookDesc.setText(book.getDesc());
        setModuleTitle();

        btn_read.setOnClickListener(this::getToRead);
        btn_Category.setOnClickListener(this::getToCategory);

    }

    private void initView()
    {
        Tv_bookName=findViewById(R.id.detail_book_name);
        Tv_bookAuthor=findViewById(R.id.detail_book_author);
        Tv_bookType=findViewById(R.id.detail_book_type);
        Tv_bookDesc=findViewById(R.id.detail_plot);
        btn_read=findViewById(R.id.detail_book_read);
        btn_Category=findViewById(R.id.detail_directory);
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

    public void getToRead(View view){
        Intent intent=null;
        intent=new Intent(this,ReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("books", book);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入简介页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void getToCategory(View view){
        Intent intent=null;
        intent=new Intent(this,CategoryActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("books", book);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入简介页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}