package com.example.android.ownerdefineview;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by Administrator on 2016/4/16.
 */
public class Tool {

    public  static void showView(ViewGroup view){
        showView(view,0);


    }
    public static void hideView(ViewGroup view){

        hideView(view,0);

    }

    public static  void showView(ViewGroup view,long delay){

        RotateAnimation animation=new RotateAnimation(180,360, Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);//保持旋转后的状态
        animation.setStartOffset(200);//设置延迟多长时间后才运行

        //开始旋转
        view.startAnimation(animation);

        //开启所有孩子的点击事件
        int childCount=view.getChildCount();
        for (int i=0;i<childCount;i++){

            view.getChildAt(i).setEnabled(true);
        }

    }
    public static void hideView(ViewGroup view,long delay){

        RotateAnimation animation=new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setStartOffset(delay);

        view.startAnimation(animation);

        //设置所有的孩子隐藏其点击事件
        int chilldCount=view.getChildCount();
        for (int i=0;i<chilldCount;i++){

            view.getChildAt(i).setEnabled(false);//禁用点击事件
        }
    }
}
