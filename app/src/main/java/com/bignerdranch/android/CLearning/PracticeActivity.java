package com.bignerdranch.android.CLearning;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Handler;
import android.os.Message;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.HttpTool.API;
import com.HttpTool.Chap;
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

public class PracticeActivity extends AppCompatActivity {

    private List<Chapter> mChapterList = new ArrayList<>();
    static Chapter_data chapter_data=new Chapter_data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
        }
        testpost();
        importChapter();
        updata_adapter();
    }

    private void updata_adapter(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(mChapterList);
        recyclerView.setAdapter(adapter);
        updata_user_input();
    }

    //测试Post请求
    public void testpost(){
        User use = new User("usertwo","123123"); //查询的用户账号,密码随意
        HttpUtil.sendOkHttpPostRequest(API.Url_GetAllRec,new Gson().toJson(use),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
            @Override
            public void onSuccess(List<Retprorec> data) {//操作成功
                if (data == null){//为空说明是第一次访问
                    for (int i = 1; i <= 11; i++)
                        chapter_data.set_chapter_progress(i,1);
                    updata_user_input();
                }
                else {//老用户,直接读取数据
                    for (int i = 0; i < 11; i++)
                        chapter_data.set_chapter_progress(valueOf(data.get(i).chapter_num), valueOf(data.get(i).chapter_rec));
                }
            }
            @Override
            public void onFailure(int code, String msg) {

                //操作错误
            }
        });
    }


    private void updata_user_input(){ //更新
     /*   int[] pro=new int[15];
        for(int i=1;i<=11;i++) pro[i]=chapter_data.get_chapter_progress(i);
        for(int i=1;i<=11;i++){
            Chap chap = new  Chap("usertwo",pro);
            HttpUtil.sendOkHttpPostRequest(API.Url_Prarecord,new Gson().toJson(chap),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
                        @Override
                        public void onSuccess(List<Retprorec> data) {//操作成功
                               int x=1;
                               int y=2;
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            int x=1;
                            int y=2;
                            //操作错误
                        }
                    });
        }
        */
    }

    private void importChapter(){
        for(int i=1 ;i<=11;i++) {
            SQLiteDatabase myDateBase = DBUtil.openDatabase(PracticeActivity.this);
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
    protected void onStart(){
       super.onStart();
       updata_adapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
