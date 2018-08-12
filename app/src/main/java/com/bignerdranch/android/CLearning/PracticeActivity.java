package com.bignerdranch.android.CLearning;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.Type.Chapter;

import java.util.ArrayList;
import java.util.List;

import Database.DBUtil;

public class PracticeActivity extends AppCompatActivity {

    private List<Chapter> mChapterList = new ArrayList<>();

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
        importChapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(mChapterList);
        recyclerView.setAdapter(adapter);
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
            Chapter chapter = new Chapter("第" + i + "章   "+chapter_name, 0 +"/"+ num);
            mChapterList.add(chapter);
        }
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
