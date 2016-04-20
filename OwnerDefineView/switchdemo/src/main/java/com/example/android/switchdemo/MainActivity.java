package com.example.android.switchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySwitch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitch = (MySwitch) findViewById(R.id.ms_switch);
        //这里的选择监听是自己定义的一个接口来里德监听
        mSwitch.setOnCheckChangeListener(new MySwitch.OnCheckChangeListener() {

            @Override
            public void onCheckChanged(View view, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "当前状态:" + isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}

