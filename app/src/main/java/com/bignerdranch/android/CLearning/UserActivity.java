package com.bignerdranch.android.CLearning;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.FileImageTool;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.ImgTool.BitMapTool;
import com.ImgTool.RoundAngleImg;
import com.Type.Retp_User_Progress;
import com.Type.Retprorec;
import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.HttpTool.API.Url_GetUserHeadImage;
import static com.bignerdranch.android.CLearning.MainLayout.UPDATE_TEXT;
import static com.bignerdranch.android.CLearning.RegistActivity.CHOOSE_PHOTO;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static java.lang.Integer.valueOf;

public class UserActivity extends Fragment {
    private ACache acache;//缓存框架
    private RoundAngleImg userHeadImg;
    private TextView modification_userImg;
    private Button button_modification;
    private Button button_quit;
    private ImageView feedback_button;
    private Context mContext;
    private TextView userAccount;
    private TextView practiceProgress;
    private Integer dataSize = 0;
    private Integer datatotal = 0;
    private String Account;
    public UserActivity() { }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mContext = this.getContext();
            acache = ACache.get(this.getActivity());//创建ACache组件
            View v = inflater.inflate(R.layout.activity_user, container, false);
            userHeadImg = v.findViewById(R.id.user_head_img);//用户头像
            practiceProgress = v.findViewById(R.id.practice_progress);
            modification_userImg = v.findViewById(R.id.modification_userImg);
            feedback_button = v.findViewById(R.id.feedback_button);
            modification_userImg.getPaint().setAntiAlias(true);
            userAccount = v.findViewById(R.id.user_account);
            userHeadImg.setmOuterRing(0);
            userHeadImg.setColor(Color.WHITE);
            userHeadImg.setOuterRingAlpha(0);

            button_modification = v.findViewById(R.id.modification_button);
            button_quit = v.findViewById(R.id.quit_button);
            button_modification.setOnClickListener(onclick);
            modification_userImg.setOnClickListener(onclick);
            button_quit.setOnClickListener(onclick);
            feedback_button.setOnClickListener(onclick);
            Account = acache.getAsString("Login");
            userAccount.setText(Account);
            SetuserHeadImg();
            getPracticeprogress();
            return v;
        }

        @Override
        public void onStart() {
            super.onStart();
            practiceProgress.setText(dataSize + "/"+datatotal);
        }

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.modification_button:
                        skip();
                        break;
                    case R.id.quit_button:
                        showNormalDialog();
                        break;
                    case R.id.modification_userImg:
                        ChangeuserHeadImg();//修改用户头像
                        break;
                    case R.id.feedback_button:
                        SkipFeedBack();
                        break;
                }
            }
        };

    private void skip() {
        Intent intent = new Intent(getActivity(), ChangPasswordActivity.class);
        startActivity(intent);
    }

    /**
        * 获取已做记录，返回每一个章节的进度
        */
    public void getPracticeprogress() {

        String user = acache.getAsString("Login");
        dataSize = 0;
        datatotal= 0;
        String URL=API.Url_Get_User_Progress + "?account=" + user;
        HttpUtil.sendOkHttpGetRequest(URL, new OnServerCallBack<FeedBack<Retp_User_Progress>, Retp_User_Progress>() {
            @Override
            public void onSuccess(Retp_User_Progress data) {//操作成功
                dataSize=valueOf(data.getCompleted());
                datatotal=valueOf(data.getTotal());
                acache.put(acache.getAsString("Login"),dataSize.toString()); //将进度存入缓存
                acache.put(acache.getAsString("Login"),datatotal.toString()); //将进度存入缓存
                restart();
            }

            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                if(code == 404){
                    Toast.makeText(mContext, "无法连接网络", Toast.LENGTH_LONG).show();
                    if(acache.getAsString(acache.getAsString("Login")) == null){
                        dataSize = 0;
                        datatotal= 0;
                        return;
                    }
                    dataSize = Integer.parseInt(acache.getAsString(acache.getAsString("Login")));//网络出错，尝试从缓存中获取进度
                    datatotal = Integer.parseInt(acache.getAsString(acache.getAsString("Login")));//网络出错，尝试从缓存中获取进度
                }
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

    private void restart() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }


    /**
     * 对话框
     */
    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this.getContext());
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定退出吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acache.remove("Login");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    //todo 获取用户头像并设置
    private void SetuserHeadImg(){
        userHeadImg.setBitmap(BitMapTool.GetDefaultHeadImageBitMap(this.getContext()));
       //尝试从缓存获取BitMap
        Bitmap bitmap;
        bitmap = acache.getAsBitmap(BitMapTool.USserHeadImageCcachePRE+Account);
        if (bitmap!=null){
            userHeadImg.setBitmap(bitmap);
            return;
        }

        //尝试从本地文件获取
        bitmap = BitMapTool.GetLocalBitmap(Account);
        if (bitmap!=null){
            userHeadImg.setBitmap(bitmap);
            return;
        }

        //没有再从网络获取
        String Url = acache.getAsString("UserImageUrl");
        if(Url==null){
            Toast.makeText(this.getContext(),"获取头像失败",Toast.LENGTH_SHORT).show();
//            return;
        }
        new GetImg(Url).start();
    }

    class GetImg extends Thread{
        String Url;
        GetImg(String url){
            this.Url = url;
        }

        public void run(){
            Bitmap userBitmap = FileImageTool.DownloadImg(Url);
            if (userBitmap!=null){
                userHeadImg.setBitmap(userBitmap);
                acache.put(BitMapTool.USserHeadImageCcachePRE+Account,userBitmap);
                BitMapTool.SaveImageToLocal(userBitmap,Account);
            }else{
                Looper.prepare();
                Toast.makeText(mContext,"从服务器获取图片失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }

    }



    //todo 修改用户头像
    private void ChangeuserHeadImg(){
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            openAlbum();
        }
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    //选择图片后
//    @Override


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{

                    }
                }
        }
    }

    //授权
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this.getActivity(),"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //处理图片
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this.getContext(),uri)){
            //如果是document 类型的uri，则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出文字格式的id
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public" +
                        "_downloads"),Long.valueOf(docId));
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content類型的Uri，則是用普通方式處理
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file類型的Uri，直接獲取圖片路徑即可
            imagePath=uri.getPath();
        }
        displayImage(imagePath);//根據圖片路徑顯示圖片
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = this.getActivity().getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            //获取图片并改变尺寸
            Bitmap newBitMap = BitMapTool.ChangeBitmapSize(BitmapFactory.decodeFile(imagePath));
            userHeadImg.setBitmap(newBitMap);

            //更新缓存
            acache.remove(BitMapTool.USserHeadImageCcachePRE+Account);
            acache.put(BitMapTool.USserHeadImageCcachePRE+Account,newBitMap);
            HttpUtil.sendOkHttpUploadImageFile(API.Url_UploadImageFile + "?account=" + Account,
                    BitMapTool.SaveImageToLocal(newBitMap, Account),
                    new OnServerCallBack<FeedBack<String>,String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("UploadImage","更换头像成功");
                            acache.remove("UserImage");
                            acache.put("UserImage",data);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            Log.e("UploadImage","更换图片失败,code:"+code+" msg:"+msg);
                        }
                    }
            );


        }else{
            Toast.makeText(MainActivity.getContext(),"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    //跳转到反馈页面
    private void SkipFeedBack(){
        Intent intent=new Intent(this.getActivity(), com.bignerdranch.android.CLearning.FeedBack.class);
        startActivity(intent);
    }

}
