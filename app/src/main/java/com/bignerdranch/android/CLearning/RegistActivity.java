package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.Type.Retprorec;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

public class RegistActivity extends AppCompatActivity {
    private ImageView register_back;
    private TextView Reg_Account;
    private TextView Reg_Password;
    private TextView Reg_RePassword;
    private Button Reg_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        register_back = (ImageView) findViewById(R.id.regist_back);
        Reg_Account = (TextView) findViewById(R.id.reg_account);
        Reg_Password = (TextView) findViewById(R.id.reg_password);
        Reg_RePassword = (TextView) findViewById(R.id.reg_repassword);
        Reg_Button = (Button) findViewById(R.id.reg_butto);
        Reg_Button.setOnClickListener(onclick);
        register_back.setOnClickListener(onclick);
    }


    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.regist_back:
                    Back();
                    break;
                case R.id.reg_butto:
                    //注册
                    if(!IsRepPassword()){
                        return;
                    }
                    Register();


            }
        }
    };

    public boolean IsRepPassword(){
        String password = Reg_Password.getText().toString();
        String repassword = Reg_RePassword.getText().toString();
        if(password.equals(repassword)){
            return true;
        }
        Toast toast =Toast.makeText(RegistActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.fail_chang_password);
        toastView.addView(imageCodeProject, 0);
        toast.show();
        return false;
    }
    public void Register(){
        final String Account = Reg_Account.getText().toString();
        final String Password = Reg_Password.getText().toString();
        User RegUser = new User(Account,Password);
        HttpUtil.sendOkHttpPostRequest(API.Url_Regist, new Gson().toJson(RegUser), new OnServerCallBack<FeedBack<String>,String>() {
            @Override
            public void onSuccess(String data) {
                Looper.prepare();
                Toast toast =Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(getApplicationContext());
                imageCodeProject.setImageResource(R.drawable.succeed_chang_password);
                toastView.addView(imageCodeProject, 0);
                toast.show();
                Looper.loop();
                Back();
            }

            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                Toast toast =Toast.makeText(RegistActivity.this,msg,Toast.LENGTH_SHORT);
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
    public void Back(){
        Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
