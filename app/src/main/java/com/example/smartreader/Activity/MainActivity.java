package com.example.smartreader.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartreader.Activity.adapter.MyFragmentPagerAdapter;
import com.example.smartreader.Activity.fragment.BookMallFragment;
import com.example.smartreader.Activity.fragment.BookshelfFragment;
import com.example.smartreader.Activity.fragment.MineFragment;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {
    private TextView register;

    private RadioGroup rg_tab_bar;
    private RadioButton rb_bookshelf;
    private RadioButton rb_mall;
    private RadioButton rb_user;
    private ViewPager vpager;

    private MyFragmentPagerAdapter mAdapter;

    //代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    /***************Object*********************/
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ImageView imageView;
    private GridView mGridView;
    private List<Book> books;

    //用户id
    private Integer userid;

    //private BookListAdapter adapter=new BookListAdapter(this);
    private ListView listView;

    protected List<Fragment> createTabFragments() {
        initFragment();
        return mFragmentList;
    }

    private void initFragment() {
        Fragment BookshelfFragment = new BookshelfFragment();
        Fragment BookMallFragment = new BookMallFragment();
        Fragment MineFragment = new MineFragment();
        mFragmentList.add(BookshelfFragment);
        mFragmentList.add(BookMallFragment);
        mFragmentList.add(MineFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去取登录的token，在第一运行应用时，token是空，
        SharedPreferences sp = getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sp.getString("token","");
        if (token.isEmpty() || token == null){
            //为空时会直接跳转到登录界面
            this.startActivity(new Intent(this.getApplicationContext(),EnterActivity.class));
            finish();
        }else {
            //实现token登录请求
            userid=Integer.valueOf(token);
            System.out.println("二次登录验证O(∩_∩)O哈哈~");
            //new Thread(new MainActivity.MyRunnable()).start();
        }
        super.onCreate(savedInstanceState);
        initFragment();
        setContentView(R.layout.activity_main);
        //new Thread(new MainActivity. MyRunnableDisplay()).start();
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initViews();
        rb_bookshelf.setChecked(true);
        //listView=getFragmentManager().findFragmentById(R.id.rb_bookshelf).getView().findViewById(R.id.lv_list);
       // new Thread(new MainActivity.MyRunnableDisplay()).start();


    }

    public Integer GetUserId(){
        System.out.print("GetUser啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");
        return this.userid;
    }

    private void initViews() {
        rg_tab_bar =  findViewById(R.id.rg_tab_bar);
        rb_bookshelf =  findViewById(R.id.rb_bookshelf);
        rb_mall =  findViewById(R.id.rb_mall);
        rb_user =  findViewById(R.id.rb_user);
        rg_tab_bar.setOnCheckedChangeListener(this);
        vpager = findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }


    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_bookshelf:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_mall:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_user:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }




    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    public void onPageSelected(int position) {

    }


    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_bookshelf.setChecked(true);

                    break;
                case PAGE_TWO:
                    rb_mall.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_user.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}