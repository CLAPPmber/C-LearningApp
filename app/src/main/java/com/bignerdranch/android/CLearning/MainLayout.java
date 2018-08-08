package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.*;

import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.Type.Cluser;
import com.Type.VedioCard;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.DBUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainLayout extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private int[] imgIdArray = {R.drawable.imgb1, R.drawable.imgb2, R.drawable.imgb3};
    private SliderLayout mDemoSlider  ;
    private ImageView Draweropen;
    private ImageButton book_button;
    private ImageButton vedio_button;
    private ImageButton practice_button;
    private ImageButton user_button;
    private Cluser cluser;
    public static Context mContext;
    private String responseData;
                public static final int UPDATE_TEXT = 1;
                private Handler handler = new Handler(){
                    public void handleMessage(Message msg){
                        switch(msg.what){
                case UPDATE_TEXT:
                    Getcardata(msg.obj.toString());
                    Toast.makeText(MainLayout.this, cluser.getAccount()+cluser.getPassword(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Draweropen = (ImageView) findViewById(R.id.DrawerOpen);
        slidepicture();
        book_button = (ImageButton)findViewById(R.id.book_button);
        vedio_button = (ImageButton)findViewById(R.id.vedio_button);
        practice_button = (ImageButton)findViewById(R.id.practice_button);
        user_button = (ImageButton)findViewById(R.id.user_button);
        book_button.setOnClickListener(onclick);
        vedio_button.setOnClickListener(onclick);
        practice_button.setOnClickListener(onclick);
        user_button.setOnClickListener(onclick);
    }
        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.book_button:
                        Intent intentbook = new Intent(MainLayout.this,BookActivity.class);
                        startActivity(intentbook);
                        break;
                    case R.id.vedio_button:
                        Intent intentvedio = new Intent(MainLayout.this,vedio_card.class);
                        startActivity(intentvedio);
                        break;
                    case R.id.practice_button:

                        Intent intentparctice = new Intent(MainLayout.this,PracticeActivity.class);
                        startActivity(intentparctice);
                        break;
                    case R.id.user_button:
                        Toast.makeText(MainLayout.this, "User", Toast.LENGTH_SHORT).show();
                        SQLiteDatabase myDateBase = DBUtil.openDatabase(MainLayout.this);
                        String sql = "SELECT * FROM COMPANY ";
                        try{
                            Cursor c = myDateBase.rawQuery(sql,null);
                            if (  c.moveToFirst()){
                                do{
                                    String getname = c.getString(c.getColumnIndex("NAME"));
                                    Toast.makeText(MainLayout.this,getname,Toast.LENGTH_LONG).show();
                                }while (c.moveToNext());
                            }
                            if (!c.isClosed()){
                                c.close();
                            }
                            if(myDateBase.isOpen()){
                                myDateBase.close();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        HttpUtil.sendOkHttpRequest("http://www.ish2b.cn:9090/gsqls",new okhttp3.Callback(){
                            @Override
                            public void onResponse(Call call,Response response) throws IOException{
                                String fb = response.body().string();
                                HttpUtil.getRequest(fb, null, new OnServerCallBack<FeedBack<List<User>>,List<User>>(){
                                    @Override
                                    public void onSuccess(List<User> data) {
                                        Log.e("test",data.get(0).account);
                                    }
                                    @Override
                                    public void onFailure(int code, String msg) {
                                    }
                                });
                                Message message = new Message();
                                message.what = UPDATE_TEXT;
                                message.obj = responseData;
                                handler.sendMessage(message);
                            }
                            @Override
                            public void onFailure(Call call,IOException e){
                                //
                            }
                        });
                }
            }
        };
       public void slidepicture() {
           mDemoSlider = (SliderLayout) findViewById(R.id.slider);
           mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
           for (int i = 0; i < imgIdArray.length; i++) {
               TextSliderView textSliderView = new TextSliderView(this);
               textSliderView
                       .image(imgIdArray[i])//image方法可以传入图片url、资源id、File
                       .setScaleType(BaseSliderView.ScaleType.Fit);//图片缩放类型
               textSliderView.bundle(new Bundle());
               textSliderView.getBundle().putString("extra", "imgb" + i);//传入参数
               mDemoSlider.addSlider(textSliderView);//添加一个滑动页面
           }
           mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
           mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator2));//自定义指示器
           mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
           mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
           Draweropen.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mDrawerLayout.openDrawer(GravityCompat.START);

               }
           });

       }
    class GetTestjson extends Thread{
        public void run(){
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.ish2b.cn:9090/gsqls")
                        .build();
                Response response = client.newCall(request).execute();
                String vediojson = response.body().string();
                Getcardata(vediojson);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //将json格式转化为String
    public  void Getcardata(String vediojson){
        try{
            JSONArray jsonArray = new JSONArray(vediojson);
            for(int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cluser = new Cluser(jsonObject.getString("account"),jsonObject.getString("password"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //return mContext
    public static Context getContext() {
        return mContext;
    }

}