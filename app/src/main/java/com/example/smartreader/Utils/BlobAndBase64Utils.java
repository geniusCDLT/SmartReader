package com.example.smartreader.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

/**
 * blob格式转换类
 */
public class BlobAndBase64Utils {
    /**
     *转换为base64格式
     * @param pic
     * @return
     */
    public String getBase64InBlob(Blob pic){
        String result= "";
        try {
            InputStream is = pic.getBinaryStream();
            int len = -1;
            byte[] buf = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            is.close();
            baos.close();
            byte[] bytes = baos.toByteArray();
            result= Base64.getEncoder().encodeToString(bytes);
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将base64转换为Bitmap格式，方便后续直接进行展示  使用 imageView.setImageBitmap(StringToBitmap(base64))
     * @param base64
     * @return
     */
    public Bitmap StringToBitmap(String base64){
        Bitmap bitmap=null;

        try {
            byte[] bitmapArray;
            bitmapArray=Base64.getDecoder().decode(base64);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
