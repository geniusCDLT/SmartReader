package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;

import java.net.ContentHandler;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private TextView T_chapter;
    private Chapter chapter;
    private List<Chapter> chapterList;
    private Book book;
    private int chapterPosition=0;//章节阅读位置
    private TextView PreChapter;
    private TextView NextChapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        T_chapter=findViewById(R.id.chapter_content);
        PreChapter=findViewById(R.id.read_tv_pre_chapter);
        NextChapter=findViewById(R.id.read_tv_next_chapter);
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("books");
        System.out.println(book.getTitle());
        new Thread(new MyRunnableChapter()).start();

        PreChapter.setOnClickListener(this::PreChapter);
        NextChapter.setOnClickListener(this::NextChapter);
    }

    /**
     * 前一章点击响应事件
     * @param view
     */
    public void PreChapter(View view){
        if(chapterPosition>0){
            chapterPosition=chapterPosition-1;
            // T_chapter.clearComposingText();
            T_chapter.setText(chapterList.get(chapterPosition).getContent());
            // System.out.println(chapterList.get(chapterPosition).getContent());
            //T_chapter.setText("");
        }
        else{

            Toast.makeText(getApplicationContext(),"已是第一章！",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 下一章点击响应事件
     * @param view
     */
    public void NextChapter(View view){
        if(chapterPosition<chapterList.size()-1){
            chapterPosition=chapterPosition+1;
            // T_chapter.clearComposingText();
            T_chapter.setText(chapterList.get(chapterPosition).getContent());
            // System.out.println(chapterList.get(chapterPosition).getContent());
            //T_chapter.setText("");
        }
        else{

            Toast.makeText(getApplicationContext(),"已到最后一章！",Toast.LENGTH_LONG).show();

        }
    }


     class MyRunnableChapter implements  Runnable{
        @Override
        public void run() {
            CatalogServiceImpl catalogService=new CatalogServiceImpl();
            chapterList=catalogService.GetAllChapter(book.getId());
            //chapter=catalogService.GetChapter("第一章 红药堂",book.getId());
            int msg=0;
            if(chapterList!=null){
                msg=1;
            }
            ContentShowHandle.sendEmptyMessage(msg);
        }
    }

    private  Handler ContentShowHandle=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
               T_chapter.setText(chapterList.get(0).getContent());
               System.out.println(chapterList.size());
            }

        }
    };
}