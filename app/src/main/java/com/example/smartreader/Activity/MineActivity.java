package com.example.smartreader.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        LogoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.mine_ll_pwd://跳转修改密码
                intent=new Intent(getApplicationContext(), ModifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                System.out.println(user.getUsername());
                intent.putExtras(bundle);
                break;
            case R.id.user_logout:
                intent = new Intent(getApplicationContext(), EnterActivity.class);
                //下面2个flags ,可以将原有任务栈清空
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //清除token
                SharedPreferences sp = getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token",null);
                editor.commit();
                //附带账号标记
                //intent.putExtra(EXTRA_LOGIN_OUT_KEY, true);
                break;
        }
        startActivity(intent);
    }
}