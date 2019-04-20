package com.bignerdranch.android.CLearning;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.ImgTool.BitMapTool;
import com.Type.Retprorec;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

public class RegistActivity extends AppCompatActivity {
    private ImageView register_back;
    private TextView Reg_Account;
    private TextView Reg_Password;
    private TextView Reg_RePassword;
    private Button Reg_Button;
    private TextView Reg_Select_Head_image;
    private ImageView Reg_User_Image;
    private String UserHeadImageString;
    private Bitmap UserHeadImageBitMap;
    private ACache aCache;
    public static final int CHOOSE_PHOTO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        register_back = (ImageView) findViewById(R.id.regist_back);
        Reg_Account = (TextView) findViewById(R.id.reg_account);
        Reg_Password = (TextView) findViewById(R.id.reg_password);
        Reg_RePassword = (TextView) findViewById(R.id.reg_repassword);
        Reg_Button = (Button) findViewById(R.id.reg_butto);
        Reg_User_Image = (ImageView) findViewById(R.id.reg_user_image);
        Reg_Button.setOnClickListener(onclick);
        Reg_Select_Head_image = (TextView)findViewById(R.id.select_head_image);
        register_back.setOnClickListener(onclick);
        Reg_Select_Head_image.setOnClickListener(onclick);
        aCache = ACache.get(this);
        //获取默认Image图片
        Drawable drawable = Reg_User_Image.getDrawable();
        Bitmap DefaultImage = BitMapTool.GetBitmapFromDrawable(drawable);
        UserHeadImageString = BitMapTool.BitmapToString(DefaultImage);
        UserHeadImageBitMap = DefaultImage;
    }


    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.regist_back:
                    Back();
                    break;
                case R.id.reg_butto:
                    //注册
                    if(!IsRepPassword()){
                        return;
                    }
                    Register();
                    break;
                case R.id.select_head_image:
                    //处理选择照片
                    SelectImage();
                    break;

            }
        }
    };

    public boolean IsRepPassword(){
        String password = Reg_Password.getText().toString();
        String repassword = Reg_RePassword.getText().toString();
        if(password.equals(repassword)){
            return true;
        }
        Toast toast =Toast.makeText(RegistActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT);
        toast.show();
        return false;
    }
    public void Register(){
        final String Account = Reg_Account.getText().toString();
        final String Password = Reg_Password.getText().toString();
        final User RegUser = new User(Account,Password);
//        RegUser.setuserHeadImage(UserHeadImageString);
        HttpUtil.sendOkHttpPostRequest(API.Url_Regist, new Gson().toJson(RegUser), new OnServerCallBack<FeedBack<String>,String>() {
            @Override
            public void onSuccess(String data) {
                Looper.prepare();
                Toast toast =Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT);
                toast.show();
                Looper.loop();
                //设置进缓存和本地
                aCache.put(BitMapTool.USserHeadImageCcachePRE+RegUser.account,UserHeadImageBitMap);
//                BitMapTool.SaveImageToLocal(UserHeadImageBitMap,RegUser.account);
                Back();
            }

            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                Toast toast =Toast.makeText(RegistActivity.this,msg,Toast.LENGTH_SHORT);
                toast.show();
                Looper.loop();
            }
        });
        //上传图片到服务器
        HttpUtil.sendOkHttpUploadImageFile(API.Url_UploadImageFile+"?account="+Account,
                BitMapTool.SaveImageToLocal(UserHeadImageBitMap, Account),
                new OnServerCallBack<FeedBack<String>,String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("UploadImage","上传图片成功");
                        aCache.put("UserImageUrl",data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Log.e("UploadImage","上传图片失败"+msg);
                    }
                }
        );

    }



    public void Back(){
        Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void SelectImage(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //处理图片
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
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
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
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
            UserHeadImageBitMap = BitMapTool.ChangeBitmapSize(BitmapFactory.decodeFile(imagePath));//获取到图片bitmap
            Reg_User_Image.setImageBitmap(UserHeadImageBitMap);
        }else{
            Toast.makeText(MainActivity.getContext(),"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
}

