package com.example.smartreader.Activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import com.example.smartreader.Activity.RankingListActivity;
import com.example.smartreader.Activity.ReadActivity;
import com.example.smartreader.Activity.SearchActivity;
import com.example.smartreader.R;
import com.example.smartreader.entity.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookMallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookMallFragment extends Fragment  implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //控件
    private EditText searchET;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    public BookMallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookMallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookMallFragment newInstance(String param1, String param2) {
        BookMallFragment fragment = new BookMallFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_mall, container, false);
        //找到控件
        searchET=view.findViewById(R.id.search_text);
        btn1=view.findViewById(R.id.bm_btn_zongbang);
        btn2=view.findViewById(R.id.bm_btn_gudai);
        btn3=view.findViewById(R.id.bm_btn_dushi);
        btn4=view.findViewById(R.id.bm_btn_huanxiang);
        btn5=view.findViewById(R.id.bm_btn_chaunyue);
        btn6=view.findViewById(R.id.bm_btn_qihuan);
        btn7=view.findViewById(R.id.bm_btn_weixuanyou);
        //设置监听器
        searchET.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        Intent intent=new Intent();
        String category="";
        switch(v.getId()){
            case R.id.search_text:
                intent=new Intent(getActivity(), SearchActivity.class);
                break;
            case R.id.bm_btn_zongbang:
                category="总榜";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_gudai:
                category="古代";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_dushi:
                category="都市";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_huanxiang:
                category="幻想";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_chaunyue:
                category="穿越";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_qihuan:
                category="奇幻";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
            case R.id.bm_btn_weixuanyou:
                category="未来悬疑游戏";
                intent=new Intent(getActivity(), RankingListActivity.class);
                break;
        }
        //每个activity都应具有并向下一个activity传递user
        User user=(User)(getActivity()).getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        //传递排行榜类别
        if(category!="") {
            intent.putExtra("RKcategory", category);
        }
        startActivity(intent);
    }
}