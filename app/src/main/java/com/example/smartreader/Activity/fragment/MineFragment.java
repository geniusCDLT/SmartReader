package com.example.smartreader.Activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.smartreader.Activity.MainActivity;
import com.example.smartreader.Activity.MineActivity;
import com.example.smartreader.Activity.ReadActivity;
import com.example.smartreader.Activity.SearchActivity;
import com.example.smartreader.Activity.adapter.Parent_Adapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.ResultEntity;
import com.example.smartreader.entity.User;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //控件
    private TextView NameTv;
    private ImageView EditIv;
    //用户
    private User user;
    private Integer userid;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookrackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //onAttach(getContext());
        new Thread(new MineFragment.MyRunnable()).start();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        //找到控件
        NameTv=view.findViewById(R.id.username);
        EditIv=view.findViewById(R.id.icon_edit);
        //每个activity都应具有并向下一个activity传递user
        //user=(User)(getActivity()).getIntent().getSerializableExtra("user");

        //跳转编辑个人信息
        EditIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MineActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    //获取用户
    class MyRunnable implements  Runnable{
        @Override
        public void run() {
            MainServiceImpl mainService=new MainServiceImpl();
            user=mainService.GetUserById(userid);
            int msg=0;
            if(user!=null){
                msg=1;
            }
            SetNameTv.sendEmptyMessage(msg);
        }
    }

    //设置用户名
    private Handler SetNameTv=new Handler(){
        public  void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                NameTv.setText(user.getUsername());
            }
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid=((MainActivity)context).GetUserId();
    }

}