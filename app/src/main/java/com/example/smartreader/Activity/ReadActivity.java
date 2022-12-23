package com.example.smartreader.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.entity.Chapter;

import java.net.ContentHandler;

public class ReadActivity extends AppCompatActivity {

    static private TextView T_chapter;
    private static Chapter chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        T_chapter=findViewById(R.id.chapter_content);
        new Thread(new MyRunnableZan()).start();
    }


    static class MyRunnableZan implements  Runnable{
        @Override
        public void run() {
            CatalogServiceImpl catalogService=new CatalogServiceImpl();
            chapter=catalogService.GetChapter("第一章",1);
            int msg=0;
            if(chapter!=null){
                msg=1;
            }
            ContentShowHandle.sendEmptyMessage(msg);
        }
    }

    private static Handler ContentShowHandle=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
               T_chapter.setText(chapter.getContent());
            }

        }
    };
}