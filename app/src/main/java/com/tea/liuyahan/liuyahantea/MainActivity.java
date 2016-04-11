package com.tea.liuyahan.liuyahantea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //声明用来记录两次退出点击时间
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        
    }



    @Override
    public void onBackPressed() {
        //点击返回键后调用退出方法，进行二次点击判断
        exit();
    }

    //自定义退出方法
    private void exit() {
        //通过当前系统时间与存储的点击时间判断点击间隔
        if ((System.currentTimeMillis() - exitTime)>2000){
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);
        }
    }
}
