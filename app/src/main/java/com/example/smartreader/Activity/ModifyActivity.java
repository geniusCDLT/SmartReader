package com.example.smartreader.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.Activity.adapter.BookListAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MineServiceImpl;
import com.example.smartreader.Service.impl.RankingListServiceImpl;
import com.example.smartreader.entity.User;

import java.io.Serializable;

public class ModifyActivity extends AppCompatActivity {
    //控件
    private EditText OldPwdEt;
    private EditText NewPwdEt;
    private EditText CfgPwdEt;
    private Button ConfirmBtn;
    //用户
    private User user;
    //新密码
    String NewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        //找到控件
        OldPwdEt=findViewById(R.id.change_password);
        NewPwdEt=findViewById(R.id.change_newpassword);
        CfgPwdEt=findViewById(R.id.change_configpassword);
        ConfirmBtn=findViewById(R.id.modify_btn_confirm);
        //每个activity都应具有并向下一个activity传递user
        user=(User)getIntent().getSerializableExtra("user");

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OldPwd=OldPwdEt.getText().toString();
                NewPwd=NewPwdEt.getText().toString();
                String CfgPwd=CfgPwdEt.getText().toString();
                String tip="";
                if(!OldPwd.equals(user.getUserPassword())){
                    tip="密码错误！";
                } else if (!NewPwd.equals(CfgPwd)) {
                    tip="两次输入的新密码不一致!";
                }else{
                    new Thread(new ModifyActivity.MyRunnableDisplay()).start();
                }
                Toast failToast = Toast.makeText(getApplicationContext(), tip, Toast.LENGTH_SHORT);
                failToast.setGravity(Gravity.CENTER, 0, 0);
                failToast.show();
            }
        });
    }

    class MyRunnableDisplay implements Runnable{
        @Override
        public void run() {
            MineServiceImpl mineService=new MineServiceImpl();
            Boolean isModified=mineService.ModifyPwd(user, NewPwd);
            int msg=0;
            if(isModified){
                msg=1;
            }else{
                msg=2;
            }
            Display.sendEmptyMessage(msg);
        }
    }

    private Handler Display=new Handler(){
        public  void handleMessage(android.os.Message msg){
            String tip="";
            if(msg.what==1){
                tip="密码修改成功！";
            } else if (msg.what==2) {
                tip="密码修改失败！请稍后重试！";
            }
            Toast toast = Toast.makeText(getApplicationContext(), tip, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    };
}