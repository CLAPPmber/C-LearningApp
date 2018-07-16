package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.*;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class MainLayout extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private int[] imgIdArray = {R.drawable.imgb1, R.drawable.imgb2, R.drawable.imgb3};
    private SliderLayout mDemoSlider  ;
    private ImageView Draweropen;
    private ImageButton book_button;
    private ImageButton vedio_button;
    private ImageButton practice_button;
    private ImageButton user_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Draweropen = (ImageView) findViewById(R.id.DrawerOpen);
        slidepicture();
        book_button = (ImageButton)findViewById(R.id.book_button);
        vedio_button = (ImageButton)findViewById(R.id.vedio_button);
        practice_button = (ImageButton)findViewById(R.id.practice_button);
        user_button = (ImageButton)findViewById(R.id.user_button);
        book_button.setOnClickListener(onclick);
        vedio_button.setOnClickListener(onclick);
        practice_button.setOnClickListener(onclick);
        user_button.setOnClickListener(onclick);
    }


        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.book_button:
                        Intent intentbook = new Intent(MainLayout.this,BookActivity.class);
                        startActivity(intentbook);
                        break;
                    case R.id.vedio_button:
                        Intent intentvedio = new Intent(MainLayout.this,vedio_card.class);
                        startActivity(intentvedio);
                        break;
                    case R.id.practice_button:
                        Toast.makeText(MainLayout.this, "Practice", Toast.LENGTH_SHORT).show();
                        Intent intentparctice = new Intent(MainLayout.this,PracticeActivity.class);
                        startActivity(intentparctice);
                        break;
                    case R.id.user_button:
                        Toast.makeText(MainLayout.this, "User", Toast.LENGTH_SHORT).show();
                }
            }
        };
       public void slidepicture() {
           mDemoSlider = (SliderLayout) findViewById(R.id.slider);
           mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
           for (int i = 0; i < imgIdArray.length; i++) {
               TextSliderView textSliderView = new TextSliderView(this);
               textSliderView
                       .image(imgIdArray[i])//image方法可以传入图片url、资源id、File
                       .setScaleType(BaseSliderView.ScaleType.Fit);//图片缩放类型
               textSliderView.bundle(new Bundle());
               textSliderView.getBundle().putString("extra", "imgb" + i);//传入参数
               mDemoSlider.addSlider(textSliderView);//添加一个滑动页面
           }
           mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
           mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator2));//自定义指示器
           mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
           mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
           Draweropen.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mDrawerLayout.openDrawer(GravityCompat.START);

               }
           });

       }
   }


