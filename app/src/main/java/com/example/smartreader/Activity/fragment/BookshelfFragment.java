package com.example.smartreader.Activity.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartreader.Activity.BookDetailActivity;
import com.example.smartreader.Activity.FloderMainActivity;
import com.example.smartreader.Activity.MainActivity;
import com.example.smartreader.Activity.adapter.Child_Adapter;
import com.example.smartreader.Activity.adapter.Parent_Adapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookshelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookshelfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private List<Book>books=new ArrayList<>();
    private User user;
    private Integer userid;
    private ArrayList<String> folders;
    private ArrayList<List<Book>> AllBook=new ArrayList<>();
    private Parent_Adapter adapter;
    public Child_Adapter mAdapter;
    private SwipeRefreshLayout swi;

    private String folderName;
    private String newFolderName;
    private boolean update=false;
    private boolean delete=false;

    public BookshelfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookshelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookshelfFragment newInstance(String param1, String param2) {
        BookshelfFragment fragment = new BookshelfFragment();
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
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        listView=(ListView)view.findViewById(R.id.lv_list);
        swi=(SwipeRefreshLayout) view.findViewById(R.id.swi_lv);
        //onAttach(getContext());
        new Thread(new BookshelfFragment.MyRunnableDisplay()).start();
        swi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否在刷新
                Toast.makeText(getActivity(),swi.isRefreshing()?"正在刷新":"刷新完成",Toast.LENGTH_SHORT).show();
               // new Thread(new BookshelfFragment.MyRunnableDisplay()).start();
                swi.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭刷新
                        new Thread(new BookshelfFragment.MyRunnableRefreshDisplay()).start();
                        swi.setRefreshing(false);
                    }
                },3000);
            }
        });
        //设置加载动画背景颜色
        swi.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.background_light));
//设置进度动画的颜色
        swi.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        return view;
    }

    public SwipeRefreshLayout.OnRefreshListener onRefresh() {
        new Thread(new BookshelfFragment.MyRunnableDisplay()).start();
        return null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid=((MainActivity)context).GetUserId();
    }

    class MyRunnableDisplay implements  Runnable{
        @Override
        public void run() {
            Intent intent=((MainActivity)getActivity()).getIntent();
            MainServiceImpl mainService=new MainServiceImpl();
            user=mainService.GetUserById(userid);
            folders=mainService.GetFolderNames(user);
            for(int i=0;i<folders.size();i++){
                books=mainService.GetFolderBooks(user,folders.get(i));
                if(books!=null){
                    AllBook.add(books);
                }
            }
            ArrayList<Integer> folder;
            int msg=0;
            if(books!=null){
                msg=1;
            }
            Display.sendEmptyMessage(msg);
        }
    }
    class MyRunnableRefreshDisplay implements  Runnable{
        @Override
        public void run() {

            MainServiceImpl mainService=new MainServiceImpl();
            user=mainService.GetUserById(userid);
            folders=mainService.GetFolderNames(user);
            AllBook=new ArrayList<>();
            for(int i=0;i<folders.size();i++){
                books=mainService.GetFolderBooks(user,folders.get(i));
                if(books!=null){
                    AllBook.add(books);


                }
            }
            ArrayList<Integer> folder;
            int msg=0;
            if(books!=null){
                msg=1;
            }
            refreshDisplay.sendEmptyMessage(msg);
        }
    }

    private Handler refreshDisplay=new Handler(){
        public void handleMessage(android.os.Message msg) {
          if(msg.what==1){
              listView.setAdapter(new Parent_Adapter(getActivity(),folders,AllBook));
          }
        }
    };

    private Handler Display=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                Parent_Adapter adapter1=new Parent_Adapter(getActivity(),folders,AllBook);
                //listView.setAdapter(new BookListAdapter(getActivity(),books));
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                       // Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                        Intent intent=null;
                        Toast.makeText(getActivity(), "进入详情页成功！", Toast.LENGTH_SHORT).show();
                        intent=new Intent(getActivity(), FloderMainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("books", (Serializable) AllBook.get(i));
                        bundle.putSerializable("user",user);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });


                adapter1.setOnItemDeleteClickListener(new Parent_Adapter.onItemDeleteListener() {
                    @Override
                    public void onDeleteClick(int i) {
                        final EditText editText = new EditText(getActivity());
                        folderName=folders.get(i);
                        AlertDialog dialog = null;
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                                .setTitle("收藏类别管理")
                                .setView(editText)
                                .setPositiveButton("修改类别", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if(!TextUtils.isEmpty(editText.getText())){
                                            newFolderName=editText.getText().toString();
                                            new Thread(new BookshelfFragment.MyRunnableUpdateFolder()).start();
                                            if(update){
                                                Toast.makeText(getActivity(),"修改类别成功！",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                        else{
                                            Toast.makeText(getActivity(),"类别名不能为空！",Toast.LENGTH_LONG).show();
                                        }

                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("删除类别", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new Thread(new BookshelfFragment.MyRunnableDeleteFolder()).start();
                                        if(delete){
                                            Toast.makeText(getActivity(),"删除类别成功！",Toast.LENGTH_LONG).show();
                                        }
                                        dialogInterface.dismiss();
                                    }
                                });
                        dialog=builder.create();
                        dialog.show();
                    }
                });

            }
        }
    };

    class MyRunnableDeleteFolder implements  Runnable{
        @Override
        public void run() {

            MainServiceImpl mainService=new MainServiceImpl();
            user=mainService.GetUserById(userid);
            delete=mainService.DeleteFolder(user,folderName);
        }
    }
    class MyRunnableUpdateFolder implements  Runnable{
        @Override
        public void run() {

            MainServiceImpl mainService=new MainServiceImpl();
            user=mainService.GetUserById(userid);

                update=mainService.RenameFolder(user,folderName,newFolderName);


        }
    }


  }