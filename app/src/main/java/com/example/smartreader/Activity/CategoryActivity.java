package com.example.smartreader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartreader.Activity.adapter.CategoryAdapter;
import com.example.smartreader.Activity.adapter.ReadCatalogAdapter;
import com.example.smartreader.R;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private TextView tv_bookTitle;
    private ListView lv_category;
    private Book book;
    private List<Chapter> chapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("books");
        chapterList= (List<Chapter>) intent.getSerializableExtra("chapterList");

        tv_bookTitle.setText(book.getTitle());
        lv_category.setAdapter(new CategoryAdapter(this,chapterList));
        lv_category.setOnItemClickListener(this::onItemClick);

    }

    private void initView(){
        tv_bookTitle=findViewById(R.id.file_category_tv_path);
        lv_category=findViewById(R.id.file_category_rv_content);
    }

    public void onItemClick(AdapterView<?> pAdapterView, View pView, int pI, long pL) {

    }
}