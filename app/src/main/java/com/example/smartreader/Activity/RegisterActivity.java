package com.example.smartreader.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.EnterServiceImpl;
import com.example.smartreader.Utils.EmailMatch;
import com.example.smartreader.entity.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText EtUsername;
    private EditText EtPwd;
    private EditText EtPwd2;
    private EditText EtEmail;
    private Button BRegister;
    private User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EtUsername=findViewById(R.id.register_user);
        EtPwd=findViewById(R.id.register_pass);
        EtPwd2=findViewById(R.id.register_pass_config);
        EtEmail=findViewById(R.id.register_email);
        BRegister=findViewById(R.id.login_button);
        BRegister.setOnClickListener(this::register);
    }

    public void register(View view){
        new Thread(){
            @Override
            public void run() {
                int msg = 0;
                EmailMatch emailMatch=new EmailMatch();
                EnterServiceImpl enter=new EnterServiceImpl();
                String username = EtUsername.getText().toString();
                String password = EtPwd.getText().toString();
                String password2= EtPwd2.getText().toString();
                String email=EtEmail.getText().toString();
                //存在空
                if(Objects.equals(username, "") || password.equals("") ||password2.equals("")||email.equals("")){
                    msg=1;
                }
                //用户名设置太短
                else if(username.length()<2||username.length()>15){
                    msg=2;
                }
                else if(password.length()<6||password.length()>12){
                    msg=3;
                }
                //两次密码输入不一致
                else if(!password.equals(password2)){
                    msg=4;
                }
                //邮箱格式不正确
                else if(!emailMatch.isValidEmail(email)){
                    msg=5;
                }
                //昵称已存在
                else if(enter.FindUser(username)){
                    msg=6;
                }
                else
                {
                    user.setUsername(username);
                    user.setUserPassword(password);
                    user.setEmail(email);
                    if(!enter.register(user)){
                        msg=7;//注册失败
                    }

                }
                if(msg==0){
                   boolean x= enter.CreateInitialFolder(user.getUsername(),user.getUserPassword());
                   if(x){
                       System.out.println("创建成功");
                   }
                }
                hand.sendEmptyMessage(msg);
            }
        }.start();
    }

    final Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

                Intent intent = new Intent();
                //将想要传递的数据用putExtra封装在intent中
                intent.putExtra("a", "註冊");
                setResult(RESULT_CANCELED, intent);
                finish();
            }
            if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "用户名设置太短或太长，请保持在2-15个字符之间！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 3) {
                Toast.makeText(getApplicationContext(), "密码长度太短或太长，请保持在6-12个字符之间！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 4) {
                Toast.makeText(getApplicationContext(), "两次密码输入不一致，请再次确认！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 5) {
                Toast.makeText(getApplicationContext(), "邮箱格式错误，请重新输入！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 6) {
                Toast.makeText(getApplicationContext(), "昵称已存在，请重新输入！", Toast.LENGTH_LONG).show();
            }
            if (msg.what == 7) {
                Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_LONG).show();
            }

        }
    };

}