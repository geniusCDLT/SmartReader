package com.example.smartreader.Service;

import com.example.smartreader.entity.User;

public interface MineService {
    //修改密码
    Boolean ModifyPwd(User user, String NewPwd);

}
