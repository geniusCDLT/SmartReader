package com.example.smartreader.Activity.fragment;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.smartreader.Activity.MainActivity;
import com.example.smartreader.Activity.ReadActivity;
import com.example.smartreader.Activity.adapter.BookListAdapter;
import com.example.smartreader.Activity.adapter.MyAdapter;
import com.example.smartreader.R;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.User;

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

    private ListView listView=getActivity().findViewById(R.id.lv_list);

    private List<Book> books;

    private BookListAdapter adapter=new BookListAdapter(getActivity());
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

        new Thread(new BookshelfFragment.MyRunnableDisplay()).start();


    }

    class MyRunnableDisplay implements  Runnable{
        @Override
        public void run() {
            Intent intent=((MainActivity)getActivity()).getIntent();
            books= (List<Book>) intent.getSerializableExtra("books");
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

                adapter.setBs(books);
                if(books!=null){
                    System.out.println("不为空");
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), books.get(i).getTitle(),Toast.LENGTH_LONG);
                    }
                });
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookshelf, container, false);
    }
  }