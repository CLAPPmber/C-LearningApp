package com.bignerdranch.android.CLearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Practicing_Activity extends AppCompatActivity {

    private Button btnOne, btnTwo, btnThree, btnFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practicing_);
        initView();
    }

    // 方法：初始化View
    private void initView() {
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
    }

    //方法：控件View的点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                Toast.makeText(Practicing_Activity.this, "btn1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnTwo:
                Toast.makeText(Practicing_Activity.this, "btn2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnThree:
                Toast.makeText(Practicing_Activity.this, "btn3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnFour:
                Toast.makeText(Practicing_Activity.this, "btn4", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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


