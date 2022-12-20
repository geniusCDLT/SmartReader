package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.smartreader.Activity.fragment.BookMallFragment;
import com.example.smartreader.Activity.fragment.BookshelfFragment;
import com.example.smartreader.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView register;
    /***************Object*********************/
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

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

    }
}