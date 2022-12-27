package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.smartreader.Activity.ui.HorizontalListView;
import com.example.smartreader.R;
import com.example.smartreader.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class Parent_Adapter extends BaseAdapter {

    private LayoutInflater inflater=null;
    private ArrayList<String> folderName;
    private ArrayList<List<Book>> books;
    private Child_Adapter adapter;

    public Parent_Adapter(Context context,ArrayList<String>folderName,ArrayList<List<Book>>books){
        inflater=LayoutInflater.from(context);
        this.books=books;
        this.folderName=folderName;

    }
    @Override
    public int getCount() {
        return folderName.size();
    }

    @Override
    public Object getItem(int i) {
        return folderName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.layout_rack_list,null);
        TextView title=view.findViewById(R.id.main_tv_rack_name);
        title.setText(folderName.get(i));
        HorizontalListView mListView=view.findViewById(R.id.main_lv_rack);

        adapter=new Child_Adapter(inflater.getContext(), (ArrayList<Book>) books.get(i));
        mListView.setAdapter(adapter);
        return view;
    }
}
