package com.example.smartreader.Activity;

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
import com.example.smartreader.R;
import com.example.smartreader.entity.Book;

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

    //private BookListAdapter adapter=new BookListAdapter(this);
    private ListView listView;

    protected List<Fragment> createTabFragments() {
        initFragment();
        return mFragmentList;
    }

    private void initFragment() {
        Fragment BookshelfFragment = new BookshelfFragment();
        Fragment BookMallFragment = new BookMallFragment();
        mFragmentList.add(BookshelfFragment);
        mFragmentList.add(BookMallFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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