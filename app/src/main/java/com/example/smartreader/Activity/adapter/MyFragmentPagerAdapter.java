package com.example.smartreader.Activity.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smartreader.Activity.MainActivity;
import com.example.smartreader.Activity.fragment.*;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;

    private BookshelfFragment bookshelfFragment;
    private BookMallFragment bookMallFragment;
    private MineFragment MineFragment;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        bookshelfFragment =new BookshelfFragment();
        bookMallFragment =new BookMallFragment();
        MineFragment =new MineFragment();


    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = bookshelfFragment;

                break;
            case MainActivity.PAGE_TWO:
                fragment = bookMallFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = MineFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
