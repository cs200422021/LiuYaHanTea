package com.tea.liuyahan.liuyahantea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DefaultActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        //通过sharepreferences保存配置文件，用于判断是否第一次启动
        sharedPreferences = getSharedPreferences("app_config", Context.MODE_PRIVATE);

        //通过新线程进行判断、延迟跳转
        new StartThread().start();
    }

    //封装判断是否第一次启动和延迟跳转的类
    private class StartThread extends Thread{
        @Override
        public void run() {
            super.run();
            //使线程休眠3s后跳转
            try {
                Thread.sleep(3*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //intent跳转
            Intent intent = new Intent();
            //声明boolean值用来标记是否第一次启动
            boolean isFirstStart = sharedPreferences.getBoolean("isFirstStart",true);
            if (isFirstStart){
                intent.setClass(DefaultActivity.this,GuidActivity.class);
                //获取sharepreferences的editer
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //通过editor将第一次启动设置为false
                editor.putBoolean("isFirstStart",false);
                editor.commit();
            } else {
                intent.setClass(DefaultActivity.this,MainActivity.class);
            }
            //启动新页面并销毁启动页
            startActivity(intent);
            finish();
        }
    }
}
