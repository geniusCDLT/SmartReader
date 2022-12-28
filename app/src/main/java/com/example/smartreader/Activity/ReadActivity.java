package com.example.smartreader.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.smartreader.Activity.ui.SliderFont;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;
import com.example.smartreader.entity.User;
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

    private User user;
    private TextView setting;
    private RelativeLayout mPopupLayout;
    private int settingNum=0;

    private SliderFont sliderFont;
    private boolean mChangeFont = false;
    private float mLastX;
    private int mFontIndex;
    private int mScreenWidth;
    private RelativeLayout mFontP;

    private Button btn_bold;
    private int bold=0;

    private Button btn_italic;
    private int italic;

    private Button btn_color_change;
    private int setColor=0;

    private Button btn_line;
    private int line=0;

    private LinearLayout linearLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();

        seekBar.setMax(100);
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("books");
        System.out.println(book.getTitle());
        user= (User) intent.getSerializableExtra("user");
        chapterPosition= (int) intent.getSerializableExtra("position");

        new Thread(new MyRunnableChapter()).start();

        PreChapter.setOnClickListener(this::PreChapter);
        NextChapter.setOnClickListener(this::NextChapter);
        mTvLeft.setOnClickListener(this::slideMenu);
        mTvReadNight.setOnClickListener(this::changeNight);
        mLvContent.setOnItemClickListener(this::onItemClick);
      //  mRlLeft.setOnClickListener(this::SlideMenuClose);
        TvBrief.setOnClickListener(this::ToBrief);
        T_chapter.setOnClickListener(this::DisplayClick);
        startAnim();
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        //其实最好的是得到mFontP的宽度，但是现在mFontP还没有绘制
        sliderFont.setParentWidth(mScreenWidth);
        sliderFont.setOnTouchListener(this::onTouch);

        btn_bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bold==0){
                    T_chapter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    bold=1;
                }
                else{
                    T_chapter.setTypeface(null, Typeface.NORMAL);
                    bold=0;
                }
            }
        });


        btn_italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(italic==0){
                    T_chapter.setTypeface(null, Typeface.ITALIC);

                    italic=1;
                }
                else{
                    T_chapter.setTypeface(null, Typeface.NORMAL);
                    italic=0;
                }
            }
        });
        btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(line==0){
                   // T_chapter.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    T_chapter.setPaintFlags(T_chapter.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    line=1;
                }
                else{
                    T_chapter.setPaintFlags(T_chapter.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                    line=0;
                }
            }
        });
        btn_color_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setColor==0){
                    T_chapter.setTextColor(Color.YELLOW);
                    setColor=1;
                }
                else{
                    T_chapter.setTextColor(Color.GRAY);
                    setColor=0;
                }
            }
        });

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
        setting=findViewById(R.id.read_tv_setting);
        mPopupLayout=findViewById(R.id.start_ctrl);
        sliderFont=findViewById(R.id.slider);
        mFontP=findViewById(R.id.linear_slider);
        btn_bold=findViewById(R.id.btn_bold);
        btn_italic=findViewById(R.id.btn_italic);
        btn_color_change=findViewById(R.id.btn_color_change);
        btn_line=findViewById(R.id.btn_line);
        linearLayout=findViewById(R.id.read_dl_slide);

    }

    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE){

            //判断Touch的位置是否在SliderFont上
            if(y>linearLayout.getY() &&y<linearLayout.getY()+linearLayout.getHeight()+30 || mChangeFont){
                mChangeFont = true;
                float specX = x-mLastX;
                sliderFont.move(specX);
                mLastX = x;
                sliderFont.invalidate();
            }

        }else if(event.getAction() == MotionEvent.ACTION_DOWN){

            if(y>linearLayout.getY() &&y<linearLayout.getY()+linearLayout.getHeight()){
                mChangeFont = true;
                sliderFont.setCenter(x);
                mLastX = x;
                sliderFont.invalidate();
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP){
            //如果上面的事件确实滑动了SliderFont，就进行thumb调整
            if(mChangeFont){

                mFontIndex =  sliderFont.adJustCenter(x);
                float fontSize = sliderFont.getFontSize(mFontIndex);
                T_chapter.setTextSize(fontSize);
                mLastX = x;
            }
            mChangeFont = false;
        }
        return true;
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
        bundle.putSerializable("user",user);
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
    private void startAnim() {
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("设置");

                //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
                final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
                ctrlAnimation.setDuration(400l);     //设置动画的过渡时间
                mPopupLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       if(settingNum==0){
                           mPopupLayout.setVisibility(View.VISIBLE);
                           mPopupLayout.startAnimation(ctrlAnimation);
                           settingNum=1;
                       }
                       else
                       {
                           mPopupLayout.setVisibility(View.GONE);
                          // mPopupLayout.startAnimation(ctrlAnimation);
                           settingNum=0;
                       }
                    }
                }, 500);
            }
        });
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
               T_chapter.setText(chapterList.get(chapterPosition).getContent());
               System.out.println(chapterList.size());
            }

        }
    };
}