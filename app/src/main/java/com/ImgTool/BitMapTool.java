package com.ImgTool;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.bignerdranch.android.CLearning.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


//处理bitmap获取，压缩，转换为byte[]
public class BitMapTool {
    //缓存前缀
    public static final String USserHeadImageCcachePRE = "USERHEADIMAGE";
    public static String FileSavePath = Environment.getExternalStorageDirectory().getPath()+"/Clapp";
    private static Bitmap DefaultHeadImageBitmap;
    private static Bitmap DefaultImageBitmap;

    //获取默认头像
    public static Bitmap GetDefaultHeadImageBitMap(Context mContext){
        if (DefaultHeadImageBitmap==null){
            Resources resources = mContext.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.init_head_image);
            DefaultHeadImageBitmap = GetBitmapFromDrawable(drawable);
        }
        return DefaultHeadImageBitmap;
    }

    //获取加载图片img
    public static Bitmap GetDefailtImageBitMap(Context mContext){
        if (DefaultImageBitmap==null){
            Resources resources = mContext.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.default_image);
            DefaultImageBitmap = GetBitmapFromDrawable(drawable);
        }
        return DefaultImageBitmap;
    }

    //将bitmap转化为String todo 即将废弃
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
        String headPicture = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);//加密转换成String
//        int n =headPicture.length();
        return headPicture;
    }

    //将String转化为bitmap
    public static Bitmap StringToBitmap(String string){
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    //通过drawable获取bitmap
    public static Bitmap GetBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //保存图片到本地目录
    /**
     * 保存位图到本地
     * @param bitmap
     * @param  Account 用户名
     * @return void
     */
    public static File SaveImageToLocal(Bitmap bitmap,String Account){
        File file=new File(FileSavePath);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(FileSavePath+"/"+Account+"head_image"+".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File bitfile=new File(FileSavePath+"/"+Account+"head_image"+".png");
        return bitfile;
    }

    /**
     * 从本地获取图片
     * @param  Account 用户名
     * @return void
     */
    //从本地获取图片
    public static Bitmap GetLocalBitmap(String Account) {
            FileInputStream fis = null;
            Log.d("Watch Url",FileSavePath);
            try {
                fis = new FileInputStream(FileSavePath+"/"+Account+"head_image"+".png");
                return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis = null;
                }
            }
    }

    //压缩图片尺寸
    public static Bitmap ChangeBitmapSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e("image_size", "width:" + width);
        Log.e("image_size", "height:" + height);
        Log.e("image_size", "size:" + bitmap.getByteCount());
        int newWidth = 100;
        int newheight = 100;
        float scaleWidth = ((float) newWidth / width);
        float scaleHeight = ((float) newheight / height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        Log.e("image_size", "new width:" + bitmap.getWidth());
        Log.e("image_size", "new height:" + bitmap.getHeight());
        Log.e("image_size", "new size:" + bitmap.getByteCount());
        return bitmap;
    }

    //bitmap转file

}
