package com.example.smartreader.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.smartreader.Activity.adapter.ReadCatalogAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

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
    private NavigationView mRlLeft;
    private ListView mLvContent;
    private TextView mTvLeft;
    private DrawerLayout dra;
    private int clickTimes=0;//点击次数
    private String[] leftMenuNames =new String[200];

    private TextView mTvReadNight;
    private RelativeLayout rl_main;
    private int nightOrWhite=0;

    private TextView TvBrief;

    private LinearLayout lr_read;
    private boolean show=true;
    private AppBarLayout  bar;
    private LinearLayout lr_bottom;




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
        mTvReadNight.setOnClickListener(this::changeNight);
        mLvContent.setOnItemClickListener(this::onItemClick);
      //  mRlLeft.setOnClickListener(this::SlideMenuClose);
        TvBrief.setOnClickListener(this::ToBrief);
        T_chapter.setOnClickListener(this::DisplayClick);

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
        TvBrief=findViewById(R.id.read_tv_brief);
        dra = findViewById(R.id.draw_l);
        lr_read=findViewById(R.id.content);
        bar=findViewById(R.id.read_abl_top_menu);
        lr_bottom=findViewById(R.id.read_ll_bottom_menu);


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
        mLvContent.setAdapter(new ReadCatalogAdapter(this,chapterList));
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

    /**
     * 夜间模式
     * @param view
     */
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

    public void ToBrief(View view){


        Intent intent=null;
        intent=new Intent(this,BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
       // bundle.putSerializable("chapterList", (Serializable) chapterList);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入简介页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void DisplayClick(View view){
        if(show){
            bar.setVisibility(view.GONE);
            lr_bottom.setVisibility(view.GONE);
            show=false;
        }
        else{
            bar.setVisibility(view.VISIBLE);
            lr_bottom.setVisibility(view.VISIBLE);
            show=true;
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