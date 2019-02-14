package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Create by Fushicho on 2019/1/24
 * 习题系统活动主界面
 */

import com.CacheTool.ACache;

public class ExerciseSystem extends Fragment {

    private ACache acache;
    static String user;
    private View v;
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.exercise_system_layout,container,false);
        //设置按钮图片的大小
        set_button_size();

        return v;
    }

    //设置按钮图片的大小
    void set_button_size(){
        //添加button1(课后习题),并设置图片的大小(无法在xml中设置图片大小,只能在此设置
        button1=(Button) v.findViewById(R.id.exercise_button_1);
        Drawable drawable1=getResources().getDrawable(R.drawable.exercise_book_pic);
        drawable1.setBounds(0,0,200,200);
        button1.setCompoundDrawables(drawable1,null,null,null);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Practice.class);
                startActivity(intent);
            }
        });
        //添加button2(试题训练),并设置图片的大小
        button2=(Button) v.findViewById(R.id.exercise_button_2);
        Drawable drawable2=getResources().getDrawable(R.drawable.exercise_test_pic);
        drawable2.setBounds(0,0,200,200);
        button2.setCompoundDrawables(drawable2,null,null,null);
        //添加button3(社区答疑),并设置图片的大小
        button3=(Button) v.findViewById(R.id.exercise_button_3);
        Drawable drawable3=getResources().getDrawable(R.drawable.city_pic);
        drawable3.setBounds(0,0,200,200);
        button3.setCompoundDrawables(drawable3,null,null,null);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CitySystem.class);
                startActivity(intent);
            }
        });
    }
/*
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {                       //为所有按钮创建点击事件
            switch (view.getId()) {
                case R.id.exercise_button_1:
                    Intent intent1 = new Intent(getActivity(), PracticeActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.exercise_button_2:
//                    Intent intent2 = new Intent(getActivity(),Test.class);
//                    startActivity(intent2);
                    break;

            }
        }
    };
    */
}
