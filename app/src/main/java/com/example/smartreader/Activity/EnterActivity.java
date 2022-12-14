package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;
import com.example.smartreader.Service.impl.EnterServiceImpl;
import com.example.smartreader.entity.User;

public class EnterActivity extends AppCompatActivity {
    private EditText EtUsername;
    private EditText EtPwd;
    private Button BLogin;
    private TextView register;

    public  User user=new User();//用户信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        EtUsername=findViewById(R.id.login_username);
        EtPwd=findViewById(R.id.login_pass);
        BLogin=findViewById(R.id.login_button);
        BLogin.setOnClickListener(this::submitLogin);
        register=findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册页面
                Intent intent=null;
                intent=new Intent(EnterActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //登录
    public void submitLogin(View view) {
        new Thread(){
            @Override
            public void run(){
                if(!EtUsername.getText().toString().equals("") && !EtPwd.getText().toString().equals("")){
                    EnterServiceImpl enter=new EnterServiceImpl();
                    user=enter.login(EtUsername.getText().toString(),EtPwd.getText().toString());
                }
                int msg=0;
                if(user!=null){
                    msg=1;
                }
                if(EtUsername.getText().toString().equals("")||EtPwd.getText().toString().equals("")){
                    msg=2;
                }
                hand1.sendEmptyMessage(msg);
            }
        }.start();
    }

    final Handler hand1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1)
            {
                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                Intent intent=null;
                intent=new Intent(EnterActivity.this, MainActivity.class);
                startActivity(intent);

            }
            else if(msg.what==2){
                Toast.makeText(getApplicationContext(),"用户名或密码不能为空，请输入",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"登录失败,账号或者密码有误",Toast.LENGTH_LONG).show();
            }
        }
    };


}