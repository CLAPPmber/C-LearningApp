package com.bignerdranch.android.CLearning;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.Type.NotSlidingViewPage;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/*
    app主页面，设置底部菜单导航栏。并使其他显示内容为Fragment，放置在ViewPage中，点击下部按钮显示对应的Fragment
 */
public class HomePage extends AppCompatActivity  implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener{

    private NotSlidingViewPage viewPager;
    private BottomNavigationBar bottomNavigationBar;
    private List<Fragment> mList; //ViewPager的数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initBottomNavigationBar();
        initViewPager();

    }

    //初始化ViewPager
    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new BookActivity());
        mList.add(new PracticeActivity());
        mList.add(new CompileActivity());
        mList.add(new vedio_card());
        mList.add(new UserActivity());

        viewPager = (NotSlidingViewPage) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        HomeApater homeAdapter = new HomeApater(getSupportFragmentManager(), mList);
        viewPager.setAdapter(homeAdapter); //视图加载适配器
    }
    //初始化底部导航条
    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        /**
         * 设置模式
         * 1、BottomNavigationBar.MODE_DEFAULT 默认
         * 如果Item的个数<=3就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式
         *
         * 2、BottomNavigationBar.MODE_FIXED 固定大小
         * 填充模式，未选中的Item会显示文字，没有换挡动画。
         *
         * 3、BottomNavigationBar.MODE_SHIFTING 不固定大小
         * 换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
         */
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        /**
         * 设置背景的样式
         * 1、BottomNavigationBar.BACKGROUND_STYLE_DEFAULT 默认
         * 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。
         * 如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
         *
         * 2、BottomNavigationBar.BACKGROUND_STYLE_STATIC ，导航条的背景色是白色
         * 加上setBarBackgroundColor（）可以设置成你所需要的任何背景颜色
         * 点击的时候没有水波纹效果
         *
         * 3、BottomNavigationBar.BACKGROUND_STYLE_RIPPLE 导航条的背景色是你设置的处于选中状态的
         * Item的颜色（ActiveColor），也就是setActiveColorResource这个设置的颜色
         * 点击的时候有水波纹效果
         */
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //设置导航条背景颜色
        //在BACKGROUND_STYLE_STATIC下，表示整个容器的背景色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示选中Item的图标和文本颜色。默认 Color.WHITE
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        //选中时的颜色,在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示整个容器的背景色。默认Theme's Primary Color
        bottomNavigationBar.setActiveColor("#ffadad");
        //未选中时的颜色，表示未选中Item中的图标和文本颜色。默认为 Color.LTGRAY
        bottomNavigationBar.setInActiveColor("#333333");


        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_book, "书籍").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.ic_practice, "练习").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.ic_compile, "编译").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.ic_video, "视频").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.ic_user,"用户").setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        //如果使用颜色的变化不足以展示一个选项Item的选中与非选中状态，
        // 可以使用BottomNavigationItem.setInActiveIcon()方法来设置。
//        new BottomNavigationItem(R.drawable.ic_home_white_24dp, "公交")//这里表示选中的图片
//                .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_launcher));//非选中的图片
    }

    @Override
    public void onTabSelected(int position) {
        //tab被选中
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
//        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
