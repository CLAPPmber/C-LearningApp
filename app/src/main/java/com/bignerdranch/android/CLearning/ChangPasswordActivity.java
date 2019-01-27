package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.google.gson.Gson;

public class ChangPasswordActivity extends AppCompatActivity {
  private ImageView cpback;
  private ACache acache;//缓存框架
  private Button cpsaveButton;

  private AutoCompleteTextView cp_inputpassword1;
  private AutoCompleteTextView cp_inputpassword2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chang_password);
    cpback = (ImageView) findViewById(R.id.Chang_password_back);
    acache = ACache.get(this);//创建ACache组件
    cpback.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ChangPasswordActivity.this, HomePage.class);
        startActivity(intent);
        finish();
      }
    });
    cp_inputpassword1=(AutoCompleteTextView)findViewById(R.id.Chang_password_inputpassword1);
    cp_inputpassword2=(AutoCompleteTextView)findViewById(R.id.Chang_password_inputpassword2);
    cpsaveButton = (Button) findViewById((R.id.Chang_password_saveButton));
    cpsaveButton.setOnClickListener(onclick);
  }


  View.OnClickListener onclick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      switch (view.getId()){
        case R.id.Chang_password_saveButton:
          String password1=cp_inputpassword1.getText().toString();
          String password2=cp_inputpassword2.getText().toString();
          if(!IsRepPassword(password1,password2)){
            return;
          }
          String Account = acache.getAsString("Login");
          ChangePassword(Account,password1);
          break;
      }
    }
  };

  public boolean IsRepPassword(String password1,String password2){

    //如果输入的两个密码不一致，则弹出“两次输入密码不一致”的提示框
    //如果输入的两个密码一致，则弹出“修改成功”的提示框
    if(password1.equals(password2)){
      return true;
    }
    else{
      Toast toast =Toast.makeText(ChangPasswordActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      LinearLayout toastView = (LinearLayout) toast.getView();
      ImageView imageCodeProject = new ImageView(getApplicationContext());
      imageCodeProject.setImageResource(R.drawable.fail_chang_password);
      toastView.addView(imageCodeProject, 0);
      toast.show();
      return false;
    }
  };

  public void ChangePassword(String Account,String Password){
    User user = new User(Account,Password);
    HttpUtil.sendOkHttpPostRequest(API.Url_ChangePassword, new Gson().toJson(user), new OnServerCallBack<FeedBack<String>,String>() {
      @Override
      public void onSuccess(String data) {
        Looper.prepare();
        Toast toast =Toast.makeText(ChangPasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.succeed_chang_password);
        toastView.addView(imageCodeProject, 0);
        toast.show();
        Looper.loop();
      }

      @Override
      public void onFailure(int code, String msg) {
        Looper.prepare();
        Toast toast =Toast.makeText(ChangPasswordActivity.this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.fail_chang_password);
        toastView.addView(imageCodeProject, 0);
        toast.show();
        Looper.loop();
      }
    });
  }

}
