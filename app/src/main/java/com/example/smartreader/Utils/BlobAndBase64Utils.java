package com.example.smartreader.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

/**
 * blob格式转换类
 */
public class BlobAndBase64Utils {
    /**
     * 将base64转换为Bitmap格式，方便后续直接进行展示  使用 imageView.setImageBitmap(StringToBitmap(base64))
     * @param Url
     * @return
     */
    public Bitmap StringToBitmap(String Url){
        Bitmap bitmap=null;
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
                URL myurl=new URL(Url);
            //获得连接
            HttpsURLConnection connection=(HttpsURLConnection) myurl.openConnection();
            connection.setConnectTimeout(6000);//设置超时
            connection.setDoInput(true);
            connection.setUseCaches(true);//不缓存
            connection.connect();
            InputStream is=connection.getInputStream();//获得图片的数据流
            bitmap=BitmapFactory.decodeStream(is);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
