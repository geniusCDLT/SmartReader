package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartreader.R;

import java.util.ArrayList;

public class DialogItemAdapter extends BaseAdapter {
    public ArrayList<String> folderName;
    LayoutInflater inflater;

    public DialogItemAdapter(Context context,ArrayList<String> folderName){
        this.folderName=folderName;
        inflater=LayoutInflater.from(context);
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
        view=inflater.inflate(R.layout.dialog_collect,null);
        TextView folder=view.findViewById(R.id.tv_dialog);
        folder.setText(folderName.get(i));
        return view;
    }
}
