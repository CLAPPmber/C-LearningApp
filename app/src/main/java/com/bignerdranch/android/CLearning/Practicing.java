package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.Type.Chap;
import com.Type.Get_Test_Msg;
import com.Type.Get_user_ans;
import com.Type.Input_flag_tN_qN;
import com.Type.Input_get_rec;
import com.Type.Put_Doing_Ans;
import com.Type.Record;
import com.Type.Retprorec;
import com.Type.User_Put_Doing_Ans;
import com.google.gson.Gson;

import java.util.List;

import Database.DBUtil;

import static com.bignerdranch.android.CLearning.MainLayout.getContext;
import static com.bignerdranch.android.CLearning.MainLayout.mContext;
import static com.bignerdranch.android.CLearning.Practice.chapter_data;
import static com.bignerdranch.android.CLearning.Practice.user;
import static com.bignerdranch.android.CLearning.ExerciseSystem.flag;
import static java.lang.Integer.valueOf;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Create by Fushicho on 2019/1/24
 * 习题系统 - 做题界面
 * 作为课后习题和试题训练的共同做题界面
 */


public class Practicing extends AppCompatActivity {

    private ImageView bt_a;
    private ImageView bt_b;
    private ImageView bt_c;
    private ImageView bt_d;
    private ACache acache;//缓存框架
    private TextView test_num;
    private TextView test_question;
    private TextView test_option_a;
    private TextView test_option_b;
    private TextView test_option_c;
    private TextView test_option_d;
    private TextView test_ana;
    private String question_name="";
    private int question_ans=0;
    private int user_ans=0;
    private String question_analysis="";
    private String question_a="";
    private String question_b="";
    private String question_c="";
    private String question_d="";
//    static boolean threah_key;

    //设置左右滑动监听起点终点坐标
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practicing_layout);
        acache = ACache.get(Practicing.this);

//        threah_key=false;
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0){ //如果消息是刚才发送的标识
                   set_test();
                }
                else if(msg.what == 1){
                    if(question_ans==user_ans)
                        print(1,user_ans);
                    else
                        print(0,user_ans);
                }
            };
        };


            //创建返回行
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_practicing);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        //为各个ui控件添加id
        init_ui();

        //更新当前章节信息
        chapter_data.set_now_question(chapter_data.get_chapter_progress((chapter_data.get_now_chapter()))+1);
        net_update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
    }

    void init_ui(){
        bt_a =(ImageView) findViewById(R.id.button_a_r);
        bt_a.setOnClickListener(onclick);
        bt_b =(ImageView) findViewById(R.id.button_b_r);
        bt_b.setOnClickListener(onclick);
        bt_c =(ImageView) findViewById(R.id.button_c_r);
        bt_c.setOnClickListener(onclick);
        bt_d =(ImageView) findViewById(R.id.button_d_r);
        bt_d.setOnClickListener(onclick);
        test_question = (TextView) findViewById(R.id.question_r);
        test_option_a = (TextView) findViewById(R.id.option_a_r);
        test_option_b = (TextView) findViewById(R.id.option_b_r);
        test_option_c = (TextView) findViewById(R.id.option_c_r);
        test_option_d = (TextView) findViewById(R.id.option_d_r);

        test_num = (TextView) findViewById(R.id.test_num_r);


    }


    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_a_r:
                        if (chapter_data.get_now_question() <= chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
                            break;
                        if (question_ans == 1) print(1, 1);
                        else print(0, 1);
                        user_ans = 1;
                        work();
                    break;
                case R.id.button_b_r:
                        if (chapter_data.get_now_question() <= chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
                            break;
                        if (question_ans == 2) print(1, 2);
                        else print(0, 2);
                        user_ans = 2;
                        work();
                    break;
                case R.id.button_c_r:
                        if (chapter_data.get_now_question() <= chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
                            break;
                        if (question_ans == 3) print(1, 3);
                        else print(0, 3);
                        user_ans = 3;
                        work();
                    break;
                case R.id.button_d_r:
                        if (chapter_data.get_now_question() <= chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
                            break;
                        if (question_ans == 4) print(1, 4);
                        else print(0, 4);
                        user_ans = 4;
                        work();
                    break;
//                case R.id.button_pre:
//                    if(chapter_data.get_now_question()==1)
//                        Toast.makeText(Practicing.this,"已经是最前一题咯~",Toast.LENGTH_SHORT).show();
//                    else
//                        jump(-1);
//                    break;
//                case R.id.button_nxt:
//                    if(chapter_data.get_now_question()==chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()))
//                        Toast.makeText(Practicing.this,"已经是最后一题啦~",Toast.LENGTH_SHORT).show();
//                    break;
            }
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(x1 - x2 > 50) {
//                Toast.makeText(Practicing.this, "向左滑", Toast.LENGTH_SHORT).show();
                    if (chapter_data.get_now_question() == chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()))
                        ;
//                    Toast.makeText(PracticingActivity.this,"已经是最后一题啦~",Toast.LENGTH_SHORT).show();
                    else if (chapter_data.get_now_question() - 1 == chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
                        ;
//                    Toast.makeText(PracticingActivity.this,"回答该题后解锁下一题哦~",Toast.LENGTH_SHORT).show();
                    else
                        jump(1);
            }
            else if(x2 - x1 > 50) {
//                Toast.makeText(Practicing.this, "向右滑", Toast.LENGTH_SHORT).show();
                    if (chapter_data.get_now_question() != 1)
                        jump(-1);
            }
        }
        return super.onTouchEvent(event);
    }

    private void set_test(){  //配置新题目
        test_question.setText(question_name);
        test_option_a.setText(question_a);
        test_option_b.setText(question_b);
        test_option_c.setText(question_c);
        test_option_d.setText(question_d);
    }

    private void jump(int type){
        if(type==1){
            change_img_init();
            chapter_data.set_now_question(chapter_data.get_now_question()+1);
//            update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
            net_update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
        }
        else{
            change_img_init();
            chapter_data.set_now_question(chapter_data.get_now_question()-1);
//            update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
            net_update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
        }
    }


    //联网更新题目信息
    private void net_update_question(int chapter_num,int question_num){
        //todo 拿到之后应该是json格式的字符串,转为对于的类型然后直接先是就好.



      //  String datajson = acache.getAsString("pre:t1:q1");
        if(question_num>chapter_data.get_chapter_max_num(chapter_num))
            question_num--;//避免最后一题做完再次打开出现下一题的bug
        question_name="";
        question_ans=1;
        question_a="";
        question_b="";
        question_c="";
        question_d="";
        test_question.setText(question_name);
        test_option_a.setText(question_a);
        test_option_b.setText(question_b);
        test_option_c.setText(question_c);
        test_option_d.setText(question_d);
        user_ans=1;

//        Input_flag_tN_qN input=new Input_flag_tN_qN(flag,chapter_num,question_num);

        String URL=API.Url_Get_Test_Msg+"?flag="+flag+"&test_num="+chapter_num+"&question_num="+question_num;

        HttpUtil.sendOkHttpGetRequest(URL,new OnServerCallBack<FeedBack<Get_Test_Msg>,Get_Test_Msg>(){
            @Override
            public void onSuccess(Get_Test_Msg data) {//操作成功
                question_name=data.getQuestion_name();
                char c=data.getQuestion_ans().charAt(0);
                if(c>='a'&&c<='d')
                    question_ans=(int)(c-'a'+1);
                else if(c>='A'&&c<='D')
                    question_ans=(int)(c-'A'+1);
                question_a=data.getQuestion_a();
                question_b=data.getQuestion_b();
                question_c=data.getQuestion_c();
                question_d=data.getQuestion_d();
//                restart();
                handler.sendEmptyMessage(0); //工作线程的handler发送消息

//                acache.put("pre:t1:q1",new Gson().toJson(data));
            }
            @Override
            public void onFailure(int code, String msg) {//操作错误

                Log.e("err",msg);
            }
        });



        chapter_data.set_now_question(question_num);
        if(chapter_data.get_now_question()>chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()))
            chapter_data.set_now_question(chapter_data.get_now_question()-1);
        test_num.setText(chapter_data.get_now_question()+"/"+chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()));
     //   set_test();



        if(question_num<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())){//对已回答的问题进行判断
//            test_ans.setText("答案:\n 该题您已完成咯~\n 正确选项是:"+(char)(question_ans-1+'A'));
//            Toast.makeText(Practicing.this,"该题您已回答过咯~",Toast.LENGTH_SHORT).show();
//            change_img_yes(question_ans);
//            test_ana.setText("解析:\n "+question_analysis);


//            Input_get_rec inp=new Input_get_rec(user,flag,chapter_num,question_num);



            String URL1=API.Url_Get_Rec+"?account="+user+"&flag="+flag+"&test_num="+chapter_num+"&question_num="+question_num;

            HttpUtil.sendOkHttpGetRequest(URL1,new OnServerCallBack<FeedBack<Get_user_ans>,Get_user_ans>(){
                @Override
                public void onSuccess(Get_user_ans data) {//操作成功

                    user_ans=(int)(data.getQuestion_ans().charAt(0)-'A'+1);
//                    restart();
                    handler.sendEmptyMessage(1); //工作线程的handler发送消息


                }
                @Override
                public void onFailure(int code, String msg) {//操作错误
                    Log.e("err",msg);
                }
            });
        }
//        else{
////            test_ans.setText(" ");
//            test_ana.setText(" ");
//        }
    }

    private void get_user_ans(){

    }

    private void work(){
//        test_ana.setText("解析:\n "+question_analysis);
        int x=chapter_data.get_now_chapter();
        int y=chapter_data.get_chapter_progress(x);
        chapter_data.set_chapter_progress(x,y+1);
        input_user_ans(x,y,user_ans);
//        updata_user_input(x,y);
    }

    private void print(int key, int option){
        if(key==1){
            change_img_yes(option);
//            test_ans.setText("答案:\n"+" 您选择的答案是"+(char)('A'+option-1)+",标准答案是"+(char)(question_ans-1+'A')+"\n 回答正确咯`~");
//            Toast.makeText(Practicing.this,"回答正确啦~",Toast.LENGTH_SHORT).show();
        }
        else {
            change_img_yes(question_ans);
            change_img_no(option);
//            test_ans.setText("答案:\n"+" 您选择的答案是"+(char)('A'+option-1)+",标准答案是"+(char)(question_ans-1+'A')+"\n 错啦~错啦~继续加油!`~");
//            Toast.makeText(Practicing.this, "错啦,错啦~", Toast.LENGTH_SHORT).show();
        }
    }

    //初始化选项图标和颜色
    private void change_img_init(){
        ImageView imageview=(ImageView)findViewById(R.id.button_a_r);
        imageview.setImageResource(R.drawable.a_pic);
        test_option_a.setTextColor(Color.parseColor("#000000"));
        imageview=(ImageView)findViewById(R.id.button_b_r);
        imageview.setImageResource(R.drawable.b_pic);
        test_option_b.setTextColor(Color.parseColor("#000000"));
        imageview=(ImageView)findViewById(R.id.button_c_r);
        imageview.setImageResource(R.drawable.c_pic);
        test_option_c.setTextColor(Color.parseColor("#000000"));
        imageview=(ImageView)findViewById(R.id.button_d_r);
        imageview.setImageResource(R.drawable.d_pic);
        test_option_d.setTextColor(Color.parseColor("#000000"));
    }

    //修改正确选项的图标和字体颜色
    private void change_img_yes(int option){
        ImageView imageview;
        switch (option){
            case 1:
                imageview=(ImageView)findViewById(R.id.button_a_r);
                imageview.setImageResource(R.drawable.yes_pic);
                test_option_a.setTextColor(Color.parseColor("#87CEEB"));
                break;
            case 2:
                imageview=(ImageView)findViewById(R.id.button_b_r);
                imageview.setImageResource(R.drawable.yes_pic);
                test_option_b.setTextColor(Color.parseColor("#87CEEB"));
                break;
            case 3:
                imageview=(ImageView)findViewById(R.id.button_c_r);
                imageview.setImageResource(R.drawable.yes_pic);
                test_option_c.setTextColor(Color.parseColor("#87CEEB"));
                break;
            case 4:
                imageview=(ImageView)findViewById(R.id.button_d_r);
                imageview.setImageResource(R.drawable.yes_pic);
                test_option_d.setTextColor(Color.parseColor("#87CEEB"));
                break;
        }
    }

    //修改错误选项的图标和字体颜色
    private void change_img_no(int option){
        ImageView imageview;
        switch (option){
            case 1:
                imageview=(ImageView)findViewById(R.id.button_a_r);
                imageview.setImageResource(R.drawable.no_pic);
                test_option_a.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 2:
                imageview=(ImageView)findViewById(R.id.button_b_r);
                imageview.setImageResource(R.drawable.no_pic);
                test_option_b.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 3:
                imageview=(ImageView)findViewById(R.id.button_c_r);
                imageview.setImageResource(R.drawable.no_pic);
                test_option_c.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 4:
                imageview=(ImageView)findViewById(R.id.button_d_r);
                imageview.setImageResource(R.drawable.no_pic);
                test_option_d.setTextColor(Color.parseColor("#FF0000"));
                break;
        }
    }

    private void input_user_ans(int chapter_num,int question_num,int ans){
        String str;
        if(ans==1)
            str="A";
        else if(ans==2)
            str="B";
        else if(ans==3)
            str="C";
        else
            str="D";
        Put_Doing_Ans input[]={
                new Put_Doing_Ans(0,0,0,"A")
        };
        input[0].setFlag(flag);
        input[0].setTest_num(chapter_num);
        input[0].setQuestion_num(question_num+1);
        input[0].setQuestion_ans(str);

        User_Put_Doing_Ans post=new User_Put_Doing_Ans(user,input);

        HttpUtil.sendOkHttpPostRequest(API.Url_Put_Doing_Ans,new Gson().toJson(post),new OnServerCallBack<FeedBack<String>,String>(){
            @Override
            public void onSuccess(String data) {//操作成功
                Looper.prepare();
//        Toast.makeText(PracticingActivity.this,"更新记录成功",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();

                Looper.loop();
                //操作错误
            }
        });
    }


    private void updata_user_input(int chapter_num,int question_num){
        Record record[] = {  //Record 类型，将章节编号和试题编号存入，组成数组
                new Record(0,0)
        };
        record[0].setChapter_num(chapter_num);
        record[0].setQuestion_num(question_num);
        Chap chaps = new Chap(user,record);
//        Chap chaps = new Chap(acache.getAsString("Login"),record);//acache.getAsString("Login")获取当前登录的用户账号,可以直接使用
        /**
         HttpUtil.sendOkHttpPostRequest(URL,new Gson().toJson(params1),new OnServerCallBack<FeedBack<fbdata>,fbdata>(){}
         @URL string 请求的URL地址,进入API文件中增添查看所有API接口
         @params1 Object 某个类的实例，将转化为对应的json格式数据
         @fbdata any 接受响应返回的data，可以是单个实例，也可以是数组，对应的数组格式为<Feedback<List<fbdata>>,List<fbdata>>
         onSuccess(fadata data) 这里的fbdata跟前面的fadata是一样的，然后data就是实际返回的数据
         onFailure(code ,msg) code:返回的状态码 msg:返回的消息
         */
        HttpUtil.sendOkHttpPostRequest(API.Url_Prarecord,new Gson().toJson(chaps),new OnServerCallBack<FeedBack<String>,String>(){
            @Override
            public void onSuccess(String data) {//操作成功
                Looper.prepare();
//        Toast.makeText(PracticingActivity.this,"更新记录成功",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                Toast.makeText(Practicing.this,"更新记录失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
                //操作错误
            }
        });
    }

    private void update_question(int chapter_num,int question_num){
        if(question_num>chapter_data.get_chapter_max_num(chapter_num))
            question_num--;//避免最后一题做完再次打开出现下一题的bug
        SQLiteDatabase myDateBase = DBUtil.openDatabase(Practicing.this);
        String sql = "SELECT * FROM question WHERE chapter_num ="+chapter_num+" AND question_num ="+question_num;
        question_name="";
        question_ans=1;
        question_analysis="";
        question_a="";
        question_b="";
        question_c="";
        question_d="";
        try{
            Cursor c = myDateBase.rawQuery(sql,null);
            if (  c.moveToFirst()){
                do{
                    question_name = c.getString(c.getColumnIndex("question_name"));
                    question_ans = c.getInt(c.getColumnIndex("question_ans"));
                    question_analysis = c.getString(c.getColumnIndex("question_analysis"));
                    question_a = c.getString(c.getColumnIndex("question_a"));
                    question_b = c.getString(c.getColumnIndex("question_b"));
                    question_c = c.getString(c.getColumnIndex("question_c"));
                    question_d = c.getString(c.getColumnIndex("question_d"));
                }while (c.moveToNext());
            }
            if (!c.isClosed()){
                c.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        chapter_data.set_now_question(question_num);
        if(chapter_data.get_now_question()>chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()))
            chapter_data.set_now_question(chapter_data.get_now_question()-1);
        test_num.setText(chapter_data.get_now_question()+"/"+chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()));
        test_question.setText(question_name);
        test_option_a.setText(question_a);
        test_option_b.setText(question_b);
        test_option_c.setText(question_c);
        test_option_d.setText(question_d);
        if(question_num<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())){
//            test_ans.setText("答案:\n 该题您已完成咯~\n 正确选项是:"+(char)(question_ans-1+'A'));
            Toast.makeText(Practicing.this,"该题您已回答过咯~",Toast.LENGTH_SHORT).show();
            change_img_yes(question_ans);
            test_ana.setText("解析:\n "+question_analysis);
        }
        else{
//            test_ans.setText(" ");
            test_ana.setText(" ");
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
