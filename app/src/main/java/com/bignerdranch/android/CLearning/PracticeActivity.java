package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.Type.Chapter;
import com.Type.Chapter_data;

import java.util.ArrayList;
import java.util.List;

import Database.DBUtil;

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
        set_cp();
        importChapter();
        updata_adapter();
    }

    private void updata_adapter(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(mChapterList);
        recyclerView.setAdapter(adapter);
    }
    private void set_cp(){//获取用户每个章节的进度
        for(int i=1;i<=11;i++)
            chapter_data.set_chapter_progress(i,1);
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
