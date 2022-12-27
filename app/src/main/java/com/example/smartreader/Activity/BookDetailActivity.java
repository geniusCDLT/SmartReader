package com.example.smartreader.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartreader.Activity.adapter.DialogItemAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.CatalogServiceImpl;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.Chapter;
import com.example.smartreader.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;
    private TextView Tv_bookName;
    private TextView Tv_bookAuthor;
    private TextView Tv_bookType;
    private TextView Tv_bookDesc;
    private Button btn_read;
    private Button btn_Category;
    private Button btn_addToShelf;
    private int size=1;
    private List<Chapter> chapterList;
    private ArrayList<String> folderName;
    private User user;
    private boolean ifCollect;
    private String clickFolderName;
    private boolean ifCollectSuccess;
    private  EditText editText ;

    //评论
    private ArrayList<String> comments = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent=this.getIntent();
        book= (Book) intent.getSerializableExtra("book");
        user= (User) intent.getSerializableExtra("user");
        chapterList= (List<Chapter>) intent.getSerializableExtra("chapterList");
        initView();
        Tv_bookType.setText(book.getNovelType());
        Tv_bookAuthor.setText(book.getAuthor());
        Tv_bookName.setText(book.getTitle());
        Tv_bookDesc.setText(book.getDesc());
        editText=new EditText(this);
        new Thread(new BookDetailActivity.MyRunnableCollectName()).start();
        setModuleTitle();
        btn_read.setOnClickListener(this::getToRead);
        btn_Category.setOnClickListener(this::getToCategory);
        btn_addToShelf.setOnClickListener(this::addToShelf);

    }

    private void initView()
    {
        Tv_bookName=findViewById(R.id.detail_book_name);
        Tv_bookAuthor=findViewById(R.id.detail_book_author);
        Tv_bookType=findViewById(R.id.detail_book_type);
        Tv_bookDesc=findViewById(R.id.detail_plot);
        btn_read=findViewById(R.id.detail_book_read);
        btn_Category=findViewById(R.id.detail_directory);
        btn_addToShelf=findViewById(R.id.detail_add_bookshelf);
    }

    //设置每个模块的标题
    private void setModuleTitle(){
        TextView plotTitle = findViewById(R.id.detail_plot_title).findViewById(R.id.module_title);
        plotTitle.setText("简介");

        TextView commentText = findViewById(R.id.detail_comment_title).findViewById(R.id.module_title);
        commentText.setText("评论");

    }

    //获取评论
    private void getCommentList(){
        RecyclerView comment_view = findViewById(R.id.detail_comment_recycler_view);

        comments.add("暂无数据");

    }

    public void getToRead(View view){
        Intent intent=null;
        intent=new Intent(this,ReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("books", book);
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入简介页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void getToCategory(View view){
        Intent intent=null;
        intent=new Intent(this,CategoryActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("books", book);
        intent.putExtras(bundle);
        Toast.makeText(this, "进入简介页成功！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void addToShelf(View view){
        DialogItemAdapter adapter=new DialogItemAdapter(this,folderName);
        AlertDialog dialog = null;
        if(ifCollect){
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("该书已加入书架，确定要取消收藏吗")
                    .setNegativeButton("修改分类", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //dialog.dismiss();
                            AlertDialog dialog2 = null;
                            AlertDialog.Builder builder=new AlertDialog.Builder(BookDetailActivity.this)
                                    .setTitle("加入书架")
                                    .setSingleChoiceItems(adapter,0,new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            clickFolderName=folderName.get(i);
                                            new Thread(new BookDetailActivity.MyRunnableUpdateCollect()).start();
                                            Toast.makeText(getApplicationContext(),"修改分类成功！",Toast.LENGTH_LONG).show();
                                            dialogInterface.dismiss();
                                        }
                                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //dialog.dismiss();
                                        }
                                    }).setPositiveButton("新建分类", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            AlertDialog dialog1 = null;
                                            AlertDialog.Builder builder=new AlertDialog.Builder(BookDetailActivity.this)
                                                    .setTitle("新建分类")
                                                    .setView(editText)
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            new Thread(new BookDetailActivity.MyRunnableNewFolder()).start();
                                                            Toast.makeText(getApplicationContext(),"新建分类成功！",Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                            dialog1=builder.create();
                                            dialog1.show();
                                        }
                                    });
                            dialog2=builder.create();
                            dialog2.show();
                        }
                    }).setPositiveButton("取消收藏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new BookDetailActivity.MyRunnableDeleteCollect()).start();
                            Toast.makeText(getApplicationContext(),"取消收藏成功！",Toast.LENGTH_LONG).show();
                        }
                    });
            dialog=builder.create();
            dialog.show();
        }

        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("加入书架")
                    .setSingleChoiceItems(adapter,0,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!ifCollect){
                                clickFolderName=folderName.get(i);
                                new Thread(new BookDetailActivity.MyRunnableCollect()).start();
                                Toast.makeText(getApplicationContext(),"加入书架成功！",Toast.LENGTH_LONG).show();
                                dialogInterface.dismiss();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"已加入！",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //dialog.dismiss();
                        }
                    }).setPositiveButton("新建分类", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog dialog1 = null;
                            AlertDialog.Builder builder=new AlertDialog.Builder(BookDetailActivity.this)
                                    .setTitle("新建分类")
                                    .setView(editText)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            new Thread(new BookDetailActivity.MyRunnableNewFolder()).start();
                                            Toast.makeText(getApplicationContext(),"新建分类成功！",Toast.LENGTH_LONG).show();
                                        }
                                    });
                            dialog1=builder.create();
                            dialog1.show();
                        }
                    });
            dialog=builder.create();
            dialog.show();
        }

    }

    class MyRunnableCollectName implements  Runnable{
        @Override
        public void run() {
            MainServiceImpl mainService=new MainServiceImpl();
            folderName=mainService.GetFolderNames(user);
            ifCollect=mainService.ifCollectBooks(user,book);
            System.out.println(folderName.size());

            int msg=0;
            if(ifCollect){
                msg=1;
            }
            ContentShowHandle.sendEmptyMessage(msg);
        }
    }

    class MyRunnableCollect implements Runnable{
        @Override
        public void run(){
            MainServiceImpl mainService=new MainServiceImpl();
            ifCollectSuccess=mainService.CollectBooks(user,clickFolderName,book);
            int msg=0;
            if(ifCollectSuccess){
                msg=1;
                ifCollect=true;
            }
            ContentShowHandle.sendEmptyMessage(msg);
        }
    }
    class MyRunnableDeleteCollect implements Runnable{
        @Override
        public void run(){
            MainServiceImpl mainService=new MainServiceImpl();
            boolean delete;
            delete=mainService.deleteCollectBooks(user,book);
            int msg=0;
            if(delete){
                msg=1;
                ifCollect=false;
            }
            DeleteContentShowHandle.sendEmptyMessage(msg);
        }
    }
    class MyRunnableNewFolder implements Runnable{
        @Override
        public void run(){
            MainServiceImpl mainService=new MainServiceImpl();
            boolean new1;
            String folder=editText.getText().toString();
            if(!folder.equals("")){
                new1=mainService.CreateNewFolder(user,folder);
                folderName.add(folder);
            }

            int msg=0;

           // DeleteContentShowHandle.sendEmptyMessage(msg);
        }
    }
    class MyRunnableUpdateCollect implements Runnable{
        @Override
        public void run(){
            MainServiceImpl mainService=new MainServiceImpl();
            boolean update;
            update=mainService.updateCollectBooks(user,book,clickFolderName);


            // DeleteContentShowHandle.sendEmptyMessage(msg);
        }
    }

    private  Handler ContentShowHandle=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                    btn_addToShelf.setText("已加入");

            }

        }
    };
    private  Handler DeleteContentShowHandle=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                btn_addToShelf.setText("加入书架");
            }
        }
    };





}