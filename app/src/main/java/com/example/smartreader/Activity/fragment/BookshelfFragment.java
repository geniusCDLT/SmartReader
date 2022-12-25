package com.example.smartreader.Activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.smartreader.Activity.MainActivity;
import com.example.smartreader.Activity.ReadActivity;
import com.example.smartreader.Activity.adapter.BookListAdapter;
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



    private BookListAdapter adapter;
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

//        Intent intent=((MainActivity)getActivity()).getIntent();
//        User user= (User) intent.getSerializableExtra("user");
//        MainServiceImpl mainService=new MainServiceImpl();
//        ArrayList<Integer> folder;
//        books=mainService.GetFolderBooks(user,"尚未分类");
//        adapter.setBs(books);
//        if(books!=null){
//            System.out.println(books.size());
//        }
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
//            }
//        });

        new Thread(new BookshelfFragment.MyRunnableDisplay()).start();
        return view;
    }

    class MyRunnableDisplay implements  Runnable{
        @Override
        public void run() {
            Intent intent=((MainActivity)getActivity()).getIntent();
            User user= (User) intent.getSerializableExtra("user");
            MainServiceImpl mainService=new MainServiceImpl();
            ArrayList<Integer> folder;
            books=mainService.GetFolderBooks(user,"尚未分类");
            int msg=0;
            if(books!=null){
                msg=1;
            }
            Display.sendEmptyMessage(msg);
        }
    }

    private Handler Display=new Handler(){
        public  void handleMessage(android.os.Message msg){
            if(msg.what==1){
                listView.setAdapter(new BookListAdapter(getActivity(),books));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                        Intent intent=null;
                        Toast.makeText(getActivity(), "进入详情页成功！", Toast.LENGTH_SHORT).show();
                        intent=new Intent(getActivity(), ReadActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("books", (Serializable) books.get(i));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        }
    };
  }