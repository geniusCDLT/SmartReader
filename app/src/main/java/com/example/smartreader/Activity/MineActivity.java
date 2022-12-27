package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;
import com.example.smartreader.entity.User;

public class MineActivity extends AppCompatActivity implements View.OnClickListener {
    //控件
    private LinearLayout PwdLL;
    private Button LogoutBtn;
    //用户
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        //找到控件
        PwdLL=findViewById(R.id.mine_ll_pwd);
        LogoutBtn=findViewById(R.id.user_logout);
        //每个activity都应具有并向下一个activity传递user
        user=(User)getIntent().getSerializableExtra("user");

        PwdLL.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.mine_ll_pwd://跳转修改密码
                intent=new Intent(getApplicationContext(), ModifyActivity.class);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        System.out.println(user.getUsername());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}