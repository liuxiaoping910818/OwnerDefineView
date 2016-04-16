package com.example.android.ownerdefineview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/4/16.
 */
public class MainTestActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlLevel1, rlLevel2, rlLevel3;

    private boolean isLevel1Show = true;
    private boolean isLevel2Show = true;
    private boolean isLevel3Show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivHome= (ImageView) findViewById(R.id.iv_home);
        ImageView ivMenu= (ImageView) findViewById(R.id.iv_menu);


        rlLevel1= (RelativeLayout) findViewById(R.id.rl_level1);
        rlLevel2= (RelativeLayout) findViewById(R.id.rl_level2);
        rlLevel3= (RelativeLayout) findViewById(R.id.rl_level3);

        ivHome.setOnClickListener(this);
        ivMenu.setOnClickListener(this);

        rlLevel3.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_home:
                if (isLevel2Show){

                    Tool.hideView(rlLevel2);
                    isLevel2Show=false;
                    if (isLevel3Show){

                        Tool.hideView(rlLevel3);
                        isLevel3Show=false;
                    }
                }else {

                    Tool.showView(rlLevel2);
                    isLevel2Show=false;
                }
                break;
            case R.id.iv_menu:
                System.out.println("menu clicked!");
                if (isLevel3Show) {
                    Tools.hideView(rlLevel3);
                    isLevel3Show = false;
                } else {
                    Tools.showView(rlLevel3);
                    isLevel3Show = true;
                }

                break;

            default:
                break;
        }

    }
}
