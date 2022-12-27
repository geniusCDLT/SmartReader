package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.Activity.adapter.ReferralAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.RankingListServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    //控件
    private EditText SrhEt;
    private ImageView SrhIv;
    private Spinner spinner;
    private TextView TypeTv;
    private ListView rfLv;
    //推荐书籍列表
    private List<Book> books=new ArrayList<>();
    //搜索类别
    private String type;
    //用户
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //找到控件
        rfLv=findViewById(R.id.lv_referral_list);
        SrhEt=findViewById(R.id.search_et_input);
        SrhIv=findViewById(R.id.search_iv_search);
        spinner=(Spinner)findViewById(R.id.search_spinner);
        TypeTv=findViewById(R.id.search_tv_type);

        //每个activity都应具有并向下一个activity传递user
        user=(User)getIntent().getSerializableExtra("user");

        String[]city=getResources().getStringArray(R.array.search_select);//建立数据源
        ArrayAdapter<String>adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city);
        spinner.setAdapter(adapter);//绑定Adapter到控件

        //下拉选择框监听
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=SearchActivity.this.getResources().getStringArray(R.array.search_select)[position];
                TypeTv.setText(type);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //搜索
        SrhIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                String srh = SrhEt.getText().toString().trim();
                intent.putExtra("search", srh);
                intent.putExtra("type",type);
                //每个activity都应具有并向下一个activity传递user
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        new Thread(new SearchActivity.MyRunnableDisplay()).start();
    }

    /**
     * 热门推荐搜索
     */
    class MyRunnableDisplay implements Runnable{
        @Override
        public void run() {
            RankingListServiceImpl rankingListService=new RankingListServiceImpl();
            books=rankingListService.GetCategoryRank("总榜", 10);
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
                rfLv.setAdapter(new ReferralAdapter(getApplicationContext(),books));
                rfLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                        Intent intent=null;
                        Toast.makeText(getApplicationContext(),"进入详情页成功！", Toast.LENGTH_SHORT).show();
                        intent=new Intent(getApplicationContext(), BookDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", (Serializable) books.get(i));
                        //每个activity都应具有并向下一个activity传递user
                        bundle.putSerializable("user", user);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        }
    };
}