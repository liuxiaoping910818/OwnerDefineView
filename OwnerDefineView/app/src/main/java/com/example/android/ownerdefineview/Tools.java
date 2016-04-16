package com.example.android.ownerdefineview;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by Administrator on 2016/4/15.
 */
public class Tools {

    public static void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    public static void showView(ViewGroup view) {
        showView(view, 0);
    }

    // 隐藏动画
    public static void hideView(ViewGroup view, long delay) {

        // Animation.RELATIVE_TO_SELF:相对于自身旋转180度，x坐村为0.5，y坐标为1，其标注的就是一个圆心，第二层和第三层就围着这一点旋转
        RotateAnimation anim = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                1f);
        anim.setDuration(500);
        anim.setFillAfter(true);// 保持动画后的状态
        anim.setStartOffset(delay);// 延迟多长时间后才运行动画

        view.startAnimation(anim);

        // 禁用所有孩子的点击事件
        int childCount = view.getChildCount();
        for (int i = 0; i < childCount; i++) {
            view.getChildAt(i).setEnabled(false);// 禁用点击事件
        }
    }

    // 显示动画
    public static void showView(ViewGroup view, long delay) {
        RotateAnimation anim = new RotateAnimation(180, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                1f);
        anim.setDuration(500);
        anim.setFillAfter(true);// 保持动画后的状态
        anim.setStartOffset(delay);// 延迟多长时间后才运行动画

        view.startAnimation(anim);

        // 开启所有孩子的点击事件
        int childCount = view.getChildCount();
        for (int i = 0; i < childCount; i++) {
            view.getChildAt(i).setEnabled(true);// 开启点击事件
        }
    }

}

