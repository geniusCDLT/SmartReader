package com.example.smartreader.Activity;

import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MineServiceImpl;
import com.example.smartreader.entity.User;

public class ModifyActivity extends AppCompatActivity {
    //控件
    private EditText OldPwdEt;
    private EditText NewPwdEt;
    private EditText CfgPwdEt;
    private Button ConfirmBtn;
    //用户
    private User user;

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
                String NewPwd=NewPwdEt.getText().toString();
                String CfgPwd=CfgPwdEt.getText().toString();
                String tip="";
                if(!OldPwd.equals(user.getUserPassword())){
                    tip="密码错误！";
                } else if (!NewPwd.equals(CfgPwd)) {
                    tip="两次输入的新密码不一致!";
                }else{
                    MineServiceImpl mineService=new MineServiceImpl();
                    if(mineService.ModifyPwd(user, NewPwd)){
                        tip="密码修改成功！";
                    }else{
                        tip="密码修改失败！请稍后重试！";
                    }
                }
                Toast failToast = Toast.makeText(getApplicationContext(), tip, Toast.LENGTH_SHORT);
                failToast.setGravity(Gravity.CENTER, 0, 0);
                failToast.show();
            }
        });
    }
}