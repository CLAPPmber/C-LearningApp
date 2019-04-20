package com.HttpTool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.PREFIX;

public class FileImageTool {
    private static String TAG = "file";
    private static final int TIMEOUT = 10*10000000; //超时时间
    private static final String CHARSET = "utf-8";//设置编码
    private static final String SUCCESS = "200";
    private static final String FAILURE = "400";
    private static final String LINE_END = "\r\n";
    private static final String PREFIX = "--";

    //上传图片
    public static String UploadFile(File file){
        //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
        String BOUNDARY = UUID.randomUUID().toString();
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        try{

            URL url = new URL(API.Url_UploadImageFile);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setDoOutput(true);//允许输入流；
            conn.setDoOutput(true);//允许输出流；
            conn.setUseCaches(false);//不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset",CHARSET);
            //设置编码
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Content-Type",CONTENT_TYPE+";boundary="+BOUNDARY);
            if(file!=null){
                /* 当文件不为空，把文件包装并且上传 */
                OutputStream outputStream = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition:form-data;name=\""+TAG+"\";filename=\""+file.getName()+"\""+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0 ;
                while ((len=is.read(bytes))!=-1){
                    dos.write(bytes,0,len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                //获取成功，响应码=200
                int res = conn.getResponseCode();
                Log.e("TAG","response code:"+res);
                Log.e("TAG","response Message:"+conn.getResponseMessage());
                if(res == 200){
                    return SUCCESS;
                }
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return FAILURE;
    }

    //下载图片 ,可能还需要做设置缓存的工作
    public static Bitmap DownloadImg(String imgPath){
        Bitmap bitmap = null;
        try{
            URL imgUrl = new URL(imgPath);
            URLConnection conn = imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }




}
