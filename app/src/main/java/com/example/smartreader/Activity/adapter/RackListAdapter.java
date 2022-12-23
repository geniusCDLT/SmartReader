package com.example.smartreader.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartreader.R;
import com.example.smartreader.Service.MainService;
import com.example.smartreader.Service.impl.MainServiceImpl;
import com.example.smartreader.entity.Book;
import com.example.smartreader.entity.CltFolder;
import com.example.smartreader.entity.User;

import java.util.List;

/**
 * 线小说说收藏夹适配器（就晋江刚打开看到的）
 */
public class RackListAdapter extends BaseAdapter {
    Context context;
    List<CltFolder> Fs;
    User user;

    public RackListAdapter(Context context, List<CltFolder> Fs, User user) {
        this.context = context;
        this.Fs = Fs;
        this.user = user;
    }

    public void setBs(List<Book> bs) {
        this.Fs = Fs;
    }

    /**
     * 决定ListView展示行数
     *
     * @return
     */
    @Override
    public int getCount() {
        return Fs.size();
    }

    /**
     * 返回指定位置数据
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return
     */
    @Override
    public Object getItem(int position) {
        return Fs.get(position);
    }

    /**
     * 返回指定位置对应ID
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RackListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            //将布局转换成view对象
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_rack_list, null);
            holder = new RackListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RackListAdapter.ViewHolder) convertView.getTag();
        }
        //加载控件显示的内容
        //获取集合显示的数据
        CltFolder f = Fs.get(position);
        String folder=f.getFolderName();
        //获取收藏夹书籍总数
        MainServiceImpl mainService = new MainServiceImpl();
        int folderBookNum = mainService.GetXFolderBookNum(user, folder);
        folder+=("(共"+folderBookNum+"本)");
        holder.folderName.setText(folder);

        return convertView;
    }

    class ViewHolder{
        TextView folderName;

        public ViewHolder(View view){
            folderName=view.findViewById(R.id.main_tv_rack_book_name);
        }
    }

}
