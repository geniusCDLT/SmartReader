package com.example.smartreader.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Spinner spinner=(Spinner)findViewById(R.id.search_spinner);//初始化控件
        String[]city=getResources().getStringArray(R.array.search_select);//建立数据源
        ArrayAdapter<String>adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city);
        spinner.setAdapter(adapter);//绑定Adapter到控件
    }
}