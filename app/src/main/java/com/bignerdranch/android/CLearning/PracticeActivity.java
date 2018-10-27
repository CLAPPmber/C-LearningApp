package com.bignerdranch.android.CLearning;



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
    public PracticeActivity(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acache=ACache.get(this.getContext());//创建ACache组件
        View v = inflater.inflate(R.layout.activity_practice,container,false);
        testpost();
        importChapter();
        updata_adapter(v);
        return v;
    }

    private void updata_adapter(View v){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(mChapterList);
        recyclerView.setAdapter(adapter);
//        updata_user_input();
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
//                    updata_user_input();
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

    /**
     * 可以正常使用，但是具体数据还有在什么时机调用在具体更改
     */
    private void updata_user_input(){
        Record record[] = {  //Record 类型，将章节编号和试题编号存入，组成数组
                new Record(1,1),
                new Record(1,2),
                new Record(1,3),
                new Record(1,4)};
        ///

        Chap chaps = new Chap("usertwo",record);
//        Chap chaps = new Chap(acache.getAsString("Login"),record);//acache.getAsString("Login")获取当前登录的用户账号,可以直接使用

        /**
         HttpUtil.sendOkHttpPostRequest(URL,new Gson().toJson(params1),new OnServerCallBack<FeedBack<fbdata>,fbdata>(){}
         @URL string 请求的URL地址,进入API文件中增添查看所有API接口
         @params1 Object 某个类的实例，将转化为对应的json格式数据
         @fbdata any 接受响应返回的data，可以是单个实例，也可以是数组，对应的数组格式为<Feedback<List<fbdata>>,List<fbdata>>
         onSuccess(fadata data) 这里的fbdata跟前面的fadata是一样的，然后data就是实际返回的数据
         onFailure(code ,msg) code:返回的状态码 msg:返回的消息
          */

        HttpUtil.sendOkHttpPostRequest(API.Url_Prarecord,new Gson().toJson(chaps),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
                @Override
                    public void onSuccess(List<Retprorec> data) {//操作成功
                        Looper.prepare();
                        Toast.makeText(getContext(),"更新记录成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        Looper.prepare();
                        Toast.makeText(getContext(),"更新记录失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        //操作错误
                    }
                });
    }

    private void importChapter(){
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
