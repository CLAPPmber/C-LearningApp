package com.bignerdranch.android.CLearning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class vedio extends Fragment {
    // 创建一个上下文菜单的方法

    public vedio(){
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        // 一个垂直滚动的两级列表。取得菜单项
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        // 获得这个类型 的位置
        int type = ExpandableListView
                .getPackedPositionType(info.packedPosition);
        // 取得所在组的索引
        int group = ExpandableListView
                .getPackedPositionGroup(info.packedPosition);
        // 取得子菜单的索引
        int child = ExpandableListView
                .getPackedPositionGroup(info.packedPosition);



    }
    private ExpandableListView eListView = null;
    private ExpandableListAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 去除标题,this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 取消状态栏，充满全屏
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             //   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = inflater.inflate(R.layout.activity_vedio,container,false);
        super.onCreate(savedInstanceState);
        // 实例化组件
        this.eListView = (ExpandableListView) v.findViewById(R.id.elistview);

        // 声明一个adapter对象
        adapter = new myExpendableadapler(this.getActivity());
        // 设置适配器提供了数据
        this.eListView.setAdapter(this.adapter);

        // 　注册一个上下文菜单显示给定的视图(多个视图可以显示上下文菜单)。
        super.registerForContextMenu(this.eListView);

        // 设置点击时候触发的事件 1，子选项点击事件 2。父选项单击事件 3.分组打开事件 4.分组关闭事件
        this.eListView.setOnChildClickListener(new ChildClickListener());
        this.eListView.setOnGroupClickListener(new GroupClickListener());
        this.eListView.setOnGroupExpandListener(new GroupExpandListener());
        this.eListView.setOnGroupCollapseListener(new GroupCollapseListener());
        return v;
    }

    // /1，子选项点击事件
    private class ChildClickListener implements OnChildClickListener {

        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            return false;
        }
    }

    // 2。父选项单击事件
    private class GroupClickListener implements OnGroupClickListener {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            return false;
        }
    }

    // 3.分组打开事件
    private class GroupExpandListener implements OnGroupExpandListener {

        public void onGroupExpand(int groupPosition) {
            // TODO Auto-generated method stub
        }
    }

    // 4.分组关闭事件
    private class GroupCollapseListener implements OnGroupCollapseListener {

        public void onGroupCollapse(int groupPosition) {
        }

    }

}