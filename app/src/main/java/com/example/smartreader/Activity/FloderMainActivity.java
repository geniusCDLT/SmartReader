package com.example.smartreader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartreader.Activity.adapter.BookListAdapter;
import com.example.smartreader.R;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

import java.io.Serializable;
import java.util.List;

public class FloderMainActivity extends AppCompatActivity {

    private ListView folder;
    private List<Book> bookList;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floder_main);

        folder=findViewById(R.id.folder_books);
        user=(User)getIntent().getSerializableExtra("user");
        bookList= (List<Book>) getIntent().getSerializableExtra("books");
        folder.setAdapter(new BookListAdapter(this,bookList));
        folder.setOnItemClickListener(this::onItemClick);
    }

    public void onItemClick(AdapterView<?> pAdapterView, View pView, int pI, long pL) {
        //Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
        Intent intent=null;
        Toast.makeText(this, "进入阅读页成功！", Toast.LENGTH_SHORT).show();
        intent=new Intent(this, ReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("books", (Serializable) bookList.get(pI));
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}