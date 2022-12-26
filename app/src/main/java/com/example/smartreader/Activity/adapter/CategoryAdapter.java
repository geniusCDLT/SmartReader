package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartreader.R;
import com.example.smartreader.entity.Chapter;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    List<Chapter> chapterList;
    public CategoryAdapter(Context context, List<Chapter>chapterList){
        this.context=context;
        this.chapterList=chapterList;
    }
    @Override
    public int getCount() {
        return chapterList.size();
    }

    @Override
    public Object getItem(int i) {
        return chapterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_category,null);
        TextView read=view.findViewById(R.id.category_tv_chapter);
        read.setText(chapterList.get(i).getTitle());
        return view;
    }
}
