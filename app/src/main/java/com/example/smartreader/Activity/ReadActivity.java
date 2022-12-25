package com.example.smartreader.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
    private SeekBar seekBar;

    private FrameLayout mFlContent;
    private RelativeLayout mRlLeft;
    private ListView mLvContent;
    private TextView mTvLeft;
    private DrawerLayout dra;
    private int clickTimes=0;//点击次数
    private String[] leftMenuNames =new String[60];

    private TextView mTvReadNight;
    private RelativeLayout rl_main;
    private int nightOrWhite=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();

        seekBar.setMax(100);
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("books");
        System.out.println(book.getTitle());
        new Thread(new MyRunnableChapter()).start();

        PreChapter.setOnClickListener(this::PreChapter);
        NextChapter.setOnClickListener(this::NextChapter);
        mTvLeft.setOnClickListener(this::slideMenu);
        mLvContent.setOnItemClickListener(this::onItemClick);
        mTvReadNight.setOnClickListener(this::changeNight);
      //  mRlLeft.setOnClickListener(this::SlideMenuClose);

    }

    private void initView(){
        T_chapter=findViewById(R.id.chapter_content);
        PreChapter=findViewById(R.id.read_tv_pre_chapter);
        NextChapter=findViewById(R.id.read_tv_next_chapter);
        seekBar=findViewById(R.id.read_sb_chapter_progress);
        //mFlContent=findViewById(R.id.fl_content);
        mRlLeft=findViewById(R.id.rl_left);
        mLvContent=findViewById(R.id.lv_left);
        mTvLeft=findViewById(R.id.read_tv_category);
        mTvReadNight=findViewById(R.id.read_tv_night_mode);
        rl_main=findViewById(R.id.Rl_main);

        dra = findViewById(R.id.draw_l);



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

            seekBar.setProgress((chapterPosition/chapterList.size())*100);
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
            seekBar.setProgress((chapterPosition/chapterList.size())*100);
        }
        else{

            Toast.makeText(getApplicationContext(),"已到最后一章！",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * 侧滑栏点击响应事件
     * @param view
     */
    public void slideMenu(View view){
        dra.setScrimColor(Color.TRANSPARENT);
        for(int i=0;i<chapterList.size();i++){
            leftMenuNames[i]=chapterList.get(i).getTitle();
        }
        mLvContent.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, leftMenuNames));//给左边菜单写入数据

        if(clickTimes==0){
            dra.openDrawer(Gravity.LEFT);
            clickTimes=1;
        }
        else{
            dra.closeDrawers();
            clickTimes=0;
        }
    }

    public void onItemClick(AdapterView<?> pAdapterView, View pView, int pI, long pL) {
        T_chapter.setText(chapterList.get(pI).getContent());
        chapterPosition=pI;
    }

    public void changeNight(View view){
      if(nightOrWhite==0){
          rl_main.setBackgroundColor(Color.BLACK);
          T_chapter.setTextColor(Color.WHITE);
          nightOrWhite=1;
      }
      else{
          rl_main.setBackgroundColor(Color.WHITE);
          T_chapter.setTextColor(Color.GRAY);
          nightOrWhite=0;
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