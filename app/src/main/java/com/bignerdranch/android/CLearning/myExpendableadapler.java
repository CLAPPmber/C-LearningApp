package com.bignerdranch.android.CLearning;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class myExpendableadapler extends BaseExpandableListAdapter {
  // 组名称
  private String[] group;
  // 子选项的名字
  private String[][] child;
  private Context context = null;
  // 构造函数
  public myExpendableadapler(Context context) {
    getString();
    this.context = context;
  }

  public Object getChild(int groupPosition, int childPosition) {

    return this.child[groupPosition][childPosition];
  }

  public long getChildId(int groupPosition, int childPosition) {

    return childPosition;
  }

  private TextView buildGroupTextView() {
    //LayoutParams AbsListView扩展提供一个位置来保存视图类型。
    AbsListView.LayoutParams params = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT,130);
    TextView textView = new TextView(this.context);
    textView.setLayoutParams(params);
    //大小
    textView.setTextSize(21f);
    textView.setGravity(Gravity.LEFT+3);
    textView.setPadding(110, 15, 5, 15);
    return textView;
  }

  private TextView buildChildTextView(){
    AbsListView.LayoutParams params = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT,90);
    TextView textView = new TextView(this.context);
    textView.setLayoutParams(params);
    //属性
    textView.setTextSize(18f);
    textView.setGravity(Gravity.LEFT);
    textView.setPadding(100,3,5,3);
    return textView;
  }

  public View getChildView(int groupPosition, int childPosition,
                           boolean isLastChild, View convertView, ViewGroup parent) {
    TextView textView = this.buildChildTextView();
    //得到每组的子选项并转换成字符串
    textView.setText(this.getChild(groupPosition, childPosition).toString());

    return textView;
  }
  //统计子选项的个数
  public int getChildrenCount(int groupPosition) {
    // TODO Auto-generated method stub
    return this.child[groupPosition].length;
  }
  //得到复选项的位置
  public Object getGroup(int groupPosition) {
    // TODO Auto-generated method stub
    return this.group[groupPosition];
  }
  //得到复选项的个数
  public int getGroupCount() {
    // TODO Auto-generated method stub
    return this.group.length;
  }
  //得到复选项的id
  public long getGroupId(int groupPosition) {
    // TODO Auto-generated method stub
    return groupPosition;
  }

  public View getGroupView(int groupPosition, boolean isExpanded,
                           View convertView, ViewGroup parent) {
    // TODO Auto-generated method stub
    TextView textView = this.buildGroupTextView();
    textView.setText(this.getGroup(groupPosition).toString());
    return textView;
  }
  //是否子选项和父选项id是稳定在改变底层数据。
  public boolean hasStableIds() {
    // TODO Auto-generated method stub
    return true;
  }
  //p判断子选项是否可以选择
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    // TODO Auto-generated method stub
    return true;
  }

  public void getString(){
    group = new String[] { "【1章】Hello,World","【2章】控制台输入与输出",
            "【3章】初识变量与常量","【4章】数组","【5章】字符与字符数组",
            "【6章】表达式与操作符","【7章】语句", "【8章】函数","【9章】指针",
            "【10章】结构体","【11章】动态分配内存","【12章】链表","【13章】引用",
            "【14章】字符串","【15章】ANSI C 标准函数库","【16章】文件操作",
            "【17章】多文件项目及编译过程","【18章】面向对象的设计方法",
            "【19章】类","【20章】构造函数与析构函数","【21章】动态创建对象","【22章】类的继承",
            "【23章】拷贝构造函数","【24章】静态成员","【25章】朋友成员","【26章】重载操作符",
            "【27章】内部类与名字空间","【28章】模板","【29章】STL 标准模板库"
    };
    child = new String[][] { {"        开发平台VS2008的安装","        创建第一个程序" },
            { "        使用输出" ,"        使用输入","        输入多个数"},
            { "        2进制10进制16进制","        初识变量与常量","        变量与内存" },
            { "        数组","        二维数组"},
            {"        字符的概念与使用","        字符数组"},
            {"        算术运算与赋值运算","        关系表达式与条件表达式","        逻辑表达式与逗号表达式","        类型转换与优先级"},
            {"        if的用法","        switch的用法","        for语句的各种变形","        for语句的相关例题","        while语句的语法",
                    "        第七章补充例题","        单步调试（1）编译错误与运行错误","        单步调试（2）学会描述问题", "        单步调试（3）学会定位错误"},
            {"        函数是什么","        函数的更多形式","        函数的传值","        全局变量与局部变量","        变量的作用域和生命期",
                    "        函数的更多用法","        函数名重载","        函数的递归调用","        单步调试（4）单步调试的操作方法",
                    "        单步调试（5）观察变量与内存","        单步调试（6）单元测试的原理"},
            {"        指针的定义和基本使用","        指针与数组","        指针作为函数的参数","        const指针的用法","        如何安全地使用指针"},
            {"        结构体的定义和基本使用","        结构体的更多使用方法","        结构体的深层次认识","        简单输入API示例","        结构体的项目应用示例"},
            {"        动态分配内存","        malloc和free的具体用法举例","        单步调试（7）程序崩溃的调试方法","        单步调试（8）程序崩溃的原因分类"},
            {"        链表的概念","        有头链表的构造","        插入与删除节点"},
            {"        引用的基本概念","        引用的更多用法"},
            {"        深入认识字符串","        字符串的插入与删除","        字符串的分割"},
            {"        随机函数rand","        时间相关函数库time.h"},
            {"        文件操作—创建与写入数据","        文件操作—写入数据","        文件操作—读取数据","        文件操作—数据的存储格式",
                    "        文件的随机访问fseek","        文件操作—按行存储数据"},
            {"        多文件项目，extern的方法","        头文件及include的用法","        #define宏定义的解释","        main函数的参数和返回值"} };
  }
}