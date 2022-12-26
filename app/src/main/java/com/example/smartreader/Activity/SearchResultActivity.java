package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.smartreader.Activity.adapter.BookListAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.RankingListServiceImpl;
import com.example.smartreader.Service.impl.SearchServiceImpl;
import com.example.smartreader.entity.Book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    //控件
    private EditText SrhEt;
    private ImageView SrhIv;
    private Spinner spinner;
    private TextView TypeTv;
    private ListView SrhLv;
    //搜索书籍列表
    private List<Book> books=new ArrayList<>();
    //搜索类别
    private String type;
    //搜索内容
    private String srh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //找到控件
        SrhLv=findViewById(R.id.lv_search_list);
        SrhEt=findViewById(R.id.search_et_input);
        SrhIv=findViewById(R.id.search_iv_search);
        spinner=(Spinner)findViewById(R.id.search_spinner);
        TypeTv=findViewById(R.id.search_tv_type);

        String[]city=getResources().getStringArray(R.array.search_select);//建立数据源
        ArrayAdapter<String>adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city);
        spinner.setAdapter(adapter);//绑定Adapter到控件

        //获得SearchActivity的搜索内容和类型
        srh=getIntent().getStringExtra("search");
        type=getIntent().getStringExtra("type");
        SrhEt.setText(srh);
        TypeTv.setText(type);

        //下拉选择框监听
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=SearchResultActivity.this.getResources().getStringArray(R.array.search_select)[position];
                TypeTv.setText(type);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //新的搜索
        SrhIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                srh = SrhEt.getText().toString().trim();
                new Thread(new SearchResultActivity.MyRunnableDisplay()).start();
            }
        });

        new Thread(new SearchResultActivity.MyRunnableDisplay()).start();
    }

    class MyRunnableDisplay implements Runnable{
        @Override
        public void run() {
            SearchServiceImpl searchService = new SearchServiceImpl();
            books=searchService.GetSearchBooks(srh, type);
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
                SrhLv.setAdapter(new BookListAdapter(getApplicationContext(),books));
                SrhLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                        Intent intent=null;
                        Toast.makeText(getApplicationContext(),"进入详情页成功！", Toast.LENGTH_SHORT).show();
                        intent=new Intent(getApplicationContext(), BookDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", (Serializable) books.get(i));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        }
    };
}