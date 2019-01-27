package com.bignerdranch.android.CLearning;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.Type.Chap;
import com.Type.Record;
import com.Type.Retprorec;
import com.google.gson.Gson;

import java.util.List;

import Database.DBUtil;

import static com.bignerdranch.android.CLearning.MainLayout.getContext;
import static com.bignerdranch.android.CLearning.PracticeActivity.chapter_data;
import static com.bignerdranch.android.CLearning.PracticeActivity.user;

public class PracticingActivity extends AppCompatActivity {

  private Button button_a;
  private Button button_b;
  private Button button_c;
  private Button button_d;
  private Button button_pre;
  private Button button_nxt;
  private TextView test_num;
  private TextView test_question;
  private TextView test_option_a;
  private TextView test_option_b;
  private TextView test_option_c;
  private TextView test_option_d;
  private TextView test_ans;
  private TextView test_ana;
  private String question_name="";
  private int question_ans=0;
  private String question_analysis="";
  private String question_a="";
  private String question_b="";
  private String question_c="";
  private String question_d="";
  private ACache acache;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_practicing);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.img_back);
    }
    acache=ACache.get(this);//创建ACache组件
    button_a =(Button) findViewById(R.id.button_a);
    button_b =(Button) findViewById(R.id.button_b);
    button_c =(Button) findViewById(R.id.button_c);
    button_d =(Button) findViewById(R.id.button_d);
    button_pre =(Button) findViewById(R.id.button_pre);
    button_nxt =(Button) findViewById(R.id.button_nxt);
    button_a.setOnClickListener(onclick);
    button_b.setOnClickListener(onclick);
    button_c.setOnClickListener(onclick);
    button_d.setOnClickListener(onclick);
    button_pre.setOnClickListener(onclick);
    button_nxt.setOnClickListener(onclick);
    test_num = (TextView) findViewById(R.id.test_num);
    test_question = (TextView) findViewById(R.id.question);
    test_option_a = (TextView) findViewById(R.id.option_a);
    test_option_b = (TextView) findViewById(R.id.option_b);
    test_option_c = (TextView) findViewById(R.id.option_c);
    test_option_d = (TextView) findViewById(R.id.option_d);
    test_ans = (TextView) findViewById(R.id.ans);
    test_ana = (TextView) findViewById(R.id.ana);
    chapter_data.set_now_question(chapter_data.get_chapter_progress((chapter_data.get_now_chapter()))+1);
    update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
  }

  View.OnClickListener onclick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.button_a:
          if(chapter_data.get_now_question()<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())) break;
          if(question_ans==1)print(1,1);
          else print(0,1);
          work();
          break;
        case R.id.button_b:
          if(chapter_data.get_now_question()<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())) break;
          if(question_ans==2)print(1,2);
          else print(0,2);
          work();
          break;
        case R.id.button_c:
          if(chapter_data.get_now_question()<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())) break;
          if(question_ans==3)print(1,3);
          else print(0,3);
          work();
          break;
        case R.id.button_d:
          if(chapter_data.get_now_question()<=chapter_data.get_chapter_progress(chapter_data.get_now_chapter())) break;
          if(question_ans==4)print(1,4);
          else print(0,4);
          work();
          break;
        case R.id.button_pre:
          if(chapter_data.get_now_question()==1)
            Toast.makeText(PracticingActivity.this,"已经是最前一题咯~",Toast.LENGTH_SHORT).show();
          else
            jump(-1);
          break;
        case R.id.button_nxt:
          if(chapter_data.get_now_question()==chapter_data.get_chapter_max_num(chapter_data.get_now_chapter()))
            Toast.makeText(PracticingActivity.this,"已经是最后一题啦~",Toast.LENGTH_SHORT).show();
          else if(chapter_data.get_now_question()-1==chapter_data.get_chapter_progress(chapter_data.get_now_chapter()))
            Toast.makeText(PracticingActivity.this,"回答该题后解锁下一题哦~",Toast.LENGTH_SHORT).show();
          else
            jump(1);
          break;
      }
    }
  };

  private void jump(int type){
    if(type==1){
      chapter_data.set_now_question(chapter_data.get_now_question()+1);
      update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
    }
    else{
      chapter_data.set_now_question(chapter_data.get_now_question()-1);
      update_question(chapter_data.get_now_chapter(),chapter_data.get_now_question());
    }
  }

  private void work(){
    test_ana.setText("解析:\n "+question_analysis);
    int x=chapter_data.get_now_chapter();
    int y=chapter_data.get_chapter_progress(x);
    chapter_data.set_chapter_progress(x,y+1);
    updata_user_input(x,y);
  }

  private void print(int key, int option){
    if(key==1){
      test_ans.setText("答案:\n"+" 您选择的答案是"+(char)('A'+option-1)+",标准答案是"+(char)(question_ans-1+'A')+"\n 回答正确咯`~");
      Toast.makeText(PracticingActivity.this,"回答正确啦~",Toast.LENGTH_SHORT).show();
    }
    else {
      test_ans.setText("答案:\n"+" 您选择的答案是"+(char)('A'+option-1)+",标准答案是"+(char)(question_ans-1+'A')+"\n 错啦~错啦~继续加油!`~");
      Toast.makeText(PracticingActivity.this, "错啦,错啦~", Toast.LENGTH_SHORT).show();
    }
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

        Toast.makeText(PracticingActivity.this,"更新记录失败",Toast.LENGTH_SHORT).show();
        Looper.loop();
        //操作错误
      }
    });
  }

  private void update_question(int chapter_num,int question_num){
    if(question_num>chapter_data.get_chapter_max_num(chapter_num))
      question_num--;//避免最后一题做完再次打开出现下一题的bug
    SQLiteDatabase myDateBase = DBUtil.openDatabase(PracticingActivity.this);
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
      test_ans.setText("答案:\n 该题您已完成咯~\n 正确选项是:"+(char)(question_ans-1+'A'));
      test_ana.setText("解析:\n "+question_analysis);
    }
    else{
      test_ans.setText(" ");
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
