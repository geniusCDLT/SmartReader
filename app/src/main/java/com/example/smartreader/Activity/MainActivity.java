package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smartreader.Activity.adapter.MyFragmentPagerAdapter;
import com.example.smartreader.Activity.fragment.BookMallFragment;
import com.example.smartreader.Activity.fragment.BookshelfFragment;
import com.example.smartreader.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener{
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initViews();
        rb_bookshelf.setChecked(true);
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

    @Override
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
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