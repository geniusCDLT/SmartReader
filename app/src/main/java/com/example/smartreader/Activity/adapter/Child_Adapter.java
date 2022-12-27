package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.helper.widget.Layer;

import com.example.smartreader.R;
import com.example.smartreader.entity.Book;

import java.util.ArrayList;

public class Child_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> books;

    public Child_Adapter(Context context,ArrayList<Book>books){
        this.context=context;
        this.books=books;
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder;
       if(view==null){
           view= LayoutInflater.from(context).inflate(R.layout.item_rack,null);
           viewHolder=new ViewHolder();
           viewHolder.rack_book=view.findViewById(R.id.main_tv_rack_book_name);
           view.setTag(viewHolder);
       }
       else{
           viewHolder= (ViewHolder) view.getTag();
       }
       viewHolder.rack_book.setText(books.get(i).getTitle());
        return view;
    }
    class ViewHolder{
        TextView rack_book;
    }
}
