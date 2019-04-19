package com.bignerdranch.android.CLearning;

/**
 * Create by Fushicho on 2018/8/10
 * app1.0版本习题系统界面
 * app1.0以后版本废弃该活动,改用Practice作为习题系统的子系统(课后习题)
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.Type.Chap;
import com.Type.Record;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.Type.Chapter;
import com.Type.Chapter_data;
import com.Type.Retprorec;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Database.DBUtil;

import static com.bignerdranch.android.CLearning.MainLayout.UPDATE_TEXT;
import static java.lang.Integer.valueOf;

public class PracticeActivity extends Fragment {
    private List<Chapter> mChapterList = new ArrayList<>();
    static Chapter_data chapter_data=new Chapter_data();
    private ACache acache;
    static String user;
    private View v;
    private Context mContext;
    public PracticeActivity(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acache=ACache.get(this.getContext());//创建ACache组件
        user=acache.getAsString("Login");
        mContext = getContext();
        v = inflater.inflate(R.layout.activity_practice,container,false);
        get_user_progress();                 //获取用户做题纪录（连网）
        importChapter();                     //添加章节信息
        updata_adapter(v);                   //更新章节信息
        return v;
    }

    private void updata_adapter(View v){//更新章节信息
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(mChapterList);
        recyclerView.setAdapter(adapter);
       // updata_user_input();
    }

    //测试Post请求
    public void get_user_progress(){
        User use = new User(user,"123456777"); //查询的用户账号,密码随意
        HttpUtil.sendOkHttpPostRequest(API.Url_GetAllRec,new Gson().toJson(use),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
            @Override
            public void onSuccess(List<Retprorec> data) {//操作成功
                for (int i = 1; i <= 11; i++)   //初始化数据
                    chapter_data.set_chapter_progress(i,0);
                if (data == null){
                    //为空不用操作
                }
                else {//若是成功获取数据就修改该用户的做题进度
                    for (int i = 0; i < data.size(); i++)  //更新不同的数据
                        chapter_data.set_chapter_progress(valueOf(data.get(i).chapter_num), valueOf(data.get(i).chapter_rec));
                }
            }
            @Override
            public void onFailure(int code, String msg) {
                //操作错误
                Log.e("err",msg);

                if(code==404){
                    Looper.prepare();
                    Toast.makeText(mContext,"无法连接网络",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

    @Override
    public void onStart(){  //调回时候刷新习题进度  （待修改）
        super.onStart();
        // 是否需要刷新数据
        updata_adapter(v);
    }

    private void importChapter(){//添加章节信息
        for(int i=1 ;i<=11;i++) {
            SQLiteDatabase myDateBase = DBUtil.openDatabase(PracticeActivity.this.getActivity());
            String sql = "SELECT * FROM chapter WHERE chapter_num ="+i;
            String chapter_name="";
            try{
                Cursor c = myDateBase.rawQuery(sql,null);
                if (  c.moveToFirst()){
                    do{
                        chapter_name = c.getString(c.getColumnIndex("chapter_name"));

                    }while (c.moveToNext());
                }
                if (!c.isClosed()){
                    c.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            int num=0;
            sql = "SELECT * FROM question WHERE chapter_num ="+i;
            try{
                Cursor c = myDateBase.rawQuery(sql,null);
                if (  c.moveToFirst()){
                    do{
                        num++;

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
            chapter_data.set_chapter_max_num(i,num);
            Chapter chapter = new Chapter("第" + i + "章   "+chapter_name, chapter_data.get_chapter_progress(i) +"/"+ num,i);
            mChapterList.add(chapter);
        }
    }

    @Override
        public void onDestroyView() {
            super.onDestroyView();
            mChapterList.clear();
    }
}
