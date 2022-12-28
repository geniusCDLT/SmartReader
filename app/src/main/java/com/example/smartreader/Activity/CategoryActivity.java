package com.example.smartreader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartreader.Activity.adapter.CategoryAdapter;
import com.example.smartreader.Activity.adapter.ReadCatalogAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;
import com.example.smartreader.entity.User;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private TextView tv_bookTitle;
    private ListView lv_category;
    private Book book;
    private List<Chapter> chapterList;
    private int position=0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("books");
        System.out.println(book.getTitle());
        user= (User) intent.getSerializableExtra("user");
        tv_bookTitle.setText(book.getTitle());
        new Thread(new CategoryActivity.MyRunnableChapter()).start();

        lv_category.setOnItemClickListener(this::onItemClick);

    }

    private void initView(){
        tv_bookTitle=findViewById(R.id.file_category_tv_path);
        lv_category=findViewById(R.id.file_category_rv_content);
    }

    public void onItemClick(AdapterView<?> pAdapterView, View pView, int pI, long pL) {
            position=pI;
            Intent intent=null;
        intent=new Intent(this,ReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("books", book);
        bundle.putSerializable("user",user);
        // bundle.putSerializable("chapterList", (Serializable) chapterList);
        bundle.putSerializable("position",position);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入阅读页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    class MyRunnableChapter implements  Runnable{
        @Override
        public void run() {
            CatalogServiceImpl catalogService=new CatalogServiceImpl();
            chapterList=catalogService.GetAllChapter(book.getId());
            //chapter=catalogService.GetChapter("第一章 红药堂",book.getId());
            int msg=0;
            if(chapterList!=null){
                msg=1;
            }
            ContentShowHandle.sendEmptyMessage(msg);
        }
    }

    private Handler ContentShowHandle=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                lv_category.setAdapter(new CategoryAdapter(CategoryActivity.this,chapterList));
            }

        }
    };
}