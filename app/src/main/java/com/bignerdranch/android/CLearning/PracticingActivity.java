package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Type.question;

import Database.DBUtil;

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
    private question qs;
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
        update_question(1,1);


    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_a:
                    Toast.makeText(PracticingActivity.this,"A",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_b:
                    Toast.makeText(PracticingActivity.this,"B",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_c:
                    Toast.makeText(PracticingActivity.this,"C",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_d:
                    Toast.makeText(PracticingActivity.this,"D",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_pre:
                    Toast.makeText(PracticingActivity.this,"pre",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_nxt:
                    Toast.makeText(PracticingActivity.this,"nxt",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    void update_question(int chapter_num,int question_num){
        SQLiteDatabase myDateBase = DBUtil.openDatabase(PracticingActivity.this);
        String sql = "SELECT * FROM question WHERE chapter_num ="+chapter_num+" AND question_num ="+question_num;
        String question_name="";
        int question_ans=0;
        String question_analysis="";
        String question_a="";
        String question_b="";
        String question_c="";
        String question_d="";
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

        test_num.setText("1/10");
        test_question.setText(question_name);
        test_option_a.setText(question_a);
        test_option_b.setText(question_b);
        test_option_c.setText(question_c);
        test_option_d.setText(question_d);
//          test_ans.setText(question_ans);
          test_ana.setText(question_analysis);
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
