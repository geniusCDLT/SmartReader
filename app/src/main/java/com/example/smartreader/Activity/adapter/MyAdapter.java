package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartreader.R;
import com.example.smartreader.Utils.BlobAndBase64Utils;
import com.example.smartreader.entity.Book;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Book> bookList;

    public MyAdapter(Context context){
        mContext=context;
    }

    public void setData(List<Book> bookList){
        this.bookList=bookList;
    }

    @Override
    public int getCount() {
        return bookList.size()*10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       BlobAndBase64Utils blob=new BlobAndBase64Utils();
        //String Base64=blob.getBase64InBlob(bookList.get(i%bookList.size()).getCover());
        view= LayoutInflater.from(mContext).inflate(R.layout.grid_view_item_layout,null);
        ImageView cover=view.findViewById(R.id.iv_gridView_cover);
        TextView title=view.findViewById(R.id.tv_gridView_title);
        cover.setImageBitmap(blob.StringToBitmap(bookList.get(i%bookList.size()).getCover()));
        title.setText(bookList.get(i%bookList.size()).getTitle());
        return view;
    }
}
