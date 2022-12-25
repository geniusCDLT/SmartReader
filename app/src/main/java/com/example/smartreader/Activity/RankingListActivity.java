package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.Activity.adapter.BookListAdapter;
import com.example.smartreader.Activity.fragment.BookshelfFragment;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.RankingListServiceImpl;
import com.example.smartreader.entity.Book;

import java.io.Serializable;
import java.util.ArrayList;

public class RankingListActivity extends AppCompatActivity {
    //控件
    private TextView cgyTv;
    private ListView rkLv;
    //排行榜类别
    private String category;
    //排行榜小说
    private ArrayList<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        //找到控件
        cgyTv=findViewById(R.id.tv_rl_category);
        rkLv=findViewById(R.id.lv_rklist);

        //获取排行榜类别
        category=getIntent().getStringExtra("RKcategory");
        cgyTv.setText(category);

        new Thread(new RankingListActivity.MyRunnableDisplay()).start();

    }

    class MyRunnableDisplay implements Runnable{
        @Override
        public void run() {
            RankingListServiceImpl rankingListService=new RankingListServiceImpl();
            books=rankingListService.GetCategoryRank(category, 50);
            int msg=0;
            if(books!=null){
                msg=1;
            }
            Display.sendEmptyMessage(msg);
        }
    }

    private Handler Display=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                rkLv.setAdapter(new BookListAdapter(getApplicationContext(),books));
                rkLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                        Intent intent=null;
                        Toast.makeText(getApplicationContext(),"进入详情页成功！", Toast.LENGTH_SHORT).show();
                        intent=new Intent(getApplicationContext(), ReadActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("books", (Serializable) books.get(i));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        }
    };
}