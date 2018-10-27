package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class MainActivity extends AppCompatActivity {
  EditText Accountedit;
  EditText Passwordedit;
  Button Loginbutton;
  TextView registerText;
  CheckBox rememberCB;
  ToggleButton isHpw;
  public static Context mContext;
  int COUNT;

  {
    COUNT = 0;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Accountedit = (EditText) findViewById(R.id.edit_Account);
    Passwordedit = (EditText) findViewById(R.id.edit_Password);
    Loginbutton = (Button) findViewById(R.id.button_login);
    registerText = (TextView) findViewById(R.id.register);
    rememberCB = (CheckBox) findViewById(R.id.checkbox_remember);
    isHpw = (ToggleButton)findViewById(R.id.isHPW);
    setAP();
    registerText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        register();
      }
    });

    Loginbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(login()){
          Toast.makeText(MainActivity.this, "登录", Toast.LENGTH_LONG).show();
          Intent intent = new Intent(MainActivity.this,MainLayout.class);
          startActivity(intent);
        }else {
          Toast.makeText(MainActivity.this, "输入账号或密码错误", Toast.LENGTH_SHORT).show();
        }
      }
    });
    isHpw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
          Passwordedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
          Passwordedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
    });
  }
  public void register() {
    String imAccount = Accountedit.getText().toString();
    String imPassword = Passwordedit.getText().toString();
    for(int i=0;i<COUNT;i++){
      SharedPreferences pref = getSharedPreferences("Account"+i,MODE_PRIVATE);
      if(imAccount.equals(pref.getString("account",""))){
        Toast.makeText(MainActivity.this,"账号已存在",Toast.LENGTH_SHORT).show();
        return;
      }
    }
    COUNT++;
    String AccountKey = "Account" + COUNT;
    SharedPreferences.Editor editor = getSharedPreferences(AccountKey, MODE_PRIVATE).edit();
    editor.putString("account", imAccount);
    editor.putString("password", imPassword);
    editor.apply();
    Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
  }
  public boolean login() {
    String imAccount = Accountedit.getText().toString();
    String imPassword = Passwordedit.getText().toString();
    for (int i = 1; i < COUNT; i++) {
      String Filename = "Account" + i;
      SharedPreferences pref = getSharedPreferences(Filename, MODE_PRIVATE);
      String Account = pref.getString("account", "");
      String Password = pref.getString("password", "");
      if (imAccount.equals(Account)) {
        if (imPassword.equals(Password)) {
          //进入界面；
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }
  @Override
  protected void onDestroy() {
    super.onDestroy();
    SharedPreferences.Editor editor = getSharedPreferences("LastImAP",MODE_PRIVATE).edit();
    editor.putString("lastAccount",Accountedit.getText().toString());
    editor.putBoolean("Isremember",rememberCB.isChecked());
    if(rememberCB.isChecked()){
      editor.putString("lastPassword",Passwordedit.getText().toString());
    }
    editor.apply();
  }
  public void setAP(){
    SharedPreferences pref = getSharedPreferences("LastImAP",MODE_PRIVATE);
    Accountedit.setText(pref.getString("lastAccount",""));
    boolean isremember = pref.getBoolean("Isremember",false);
    if(isremember) {
      Passwordedit.setText(pref.getString("lastPassword", ""));
      rememberCB.setChecked(true);
    }
  }

  //return mContext
  public static Context getContext() {
    return mContext;
  }
}
