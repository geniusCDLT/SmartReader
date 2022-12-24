package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartreader.R;
import com.example.smartreader.Utils.BlobAndBase64Utils;
import com.example.smartreader.entity.Book;

import java.util.List;


/**
 * 小说排行榜适配器
 */
public class BookListAdapter extends BaseAdapter {
    Context context;
    List<Book> Bs;

    public BookListAdapter(Context context,List<Book>Bs) {
        this.context=context;
        this.Bs=Bs;

    }

    public void setBs(List<Book> bs) {
        this.Bs=Bs;
    }

    /**
     * 决定ListView展示行数
     * @return
     */
    @Override
    public int getCount() {
        System.out.println("BS+"+Bs.size());
        return Bs.size();
    }

    /**
     * 返回指定位置数据
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return
     */
    @Override
    public Object getItem(int position) {
        return Bs.get(position);
    }

    /**
     * 返回指定位置对应ID
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookListAdapter.ViewHolder holder=null;
        if(convertView==null){
            //将布局转换成view对象
            convertView= LayoutInflater.from(context).inflate(R.layout.item_book,null);
            holder=new BookListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(BookListAdapter.ViewHolder) convertView.getTag();
        }
        //加载控件显示的内容
        //获取集合显示的数据
        Book b=Bs.get(position);
        holder.title.setText(b.getTitle());
        holder.author.setText(b.getAuthor());
        holder.type.setText(b.getNovelType());
        holder.desc.setText(b.getDesc());
        BlobAndBase64Utils blob=new BlobAndBase64Utils();
       // String Base64=blob.getBase64InBlob(b.getCover());
        //holder.cover.setImageBitmap(blob.StringToBitmap(b.getCover()));
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView author;
        TextView type;
        TextView desc;
        ImageView cover;

        public ViewHolder(View view){
            title=view.findViewById(R.id.rl_tv_book_title);
            author=view.findViewById(R.id.rl_tv_book_author);
            type=view.findViewById(R.id.rl_tv_book_type);
            desc=view.findViewById(R.id.rl_tv_book_desc);
            cover=view.findViewById(R.id.rl_iv_book_cover);
        }
    }
}
