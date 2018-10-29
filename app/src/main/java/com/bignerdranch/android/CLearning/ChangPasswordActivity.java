package com.bignerdranch.android.CLearning;

<<<<<<< HEAD
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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

public class ChangPasswordActivity extends AppCompatActivity {
  private ImageView cpback;
  private Button cpsaveButton;
  private AutoCompleteTextView cp_inputpassword1;
  private AutoCompleteTextView cp_inputpassword2;
=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChangPasswordActivity extends AppCompatActivity {
>>>>>>> 0ea9675cef190d58dac373d889c401f1f0d91c9a

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chang_password);
<<<<<<< HEAD
    cpback = (ImageView) findViewById(R.id.Chang_password_back);
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
    cpsaveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String password1=cp_inputpassword1.getText().toString();
        String password2=cp_inputpassword2.getText().toString();
        //如果输入的两个密码不一致，则弹出“两次输入密码不一致”的提示框
        //如果输入的两个密码一致，则弹出“修改成功”的提示框
        if(password1.equals(password2)){
          Toast toast =Toast.makeText(ChangPasswordActivity.this,"修改成功",Toast.LENGTH_SHORT);
          toast.setGravity(Gravity.CENTER, 0, 0);
          LinearLayout toastView = (LinearLayout) toast.getView();
          ImageView imageCodeProject = new ImageView(getApplicationContext());
          imageCodeProject.setImageResource(R.drawable.succeed_chang_password);
          toastView.addView(imageCodeProject, 0);
          toast.show();
        }
        else{
          Toast toast =Toast.makeText(ChangPasswordActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT);
          toast.setGravity(Gravity.CENTER, 0, 0);
          LinearLayout toastView = (LinearLayout) toast.getView();
          ImageView imageCodeProject = new ImageView(getApplicationContext());
          imageCodeProject.setImageResource(R.drawable.fail_chang_password);
          toastView.addView(imageCodeProject, 0);
          toast.show();
        }

      }
    });
=======
>>>>>>> 0ea9675cef190d58dac373d889c401f1f0d91c9a
  }
}
