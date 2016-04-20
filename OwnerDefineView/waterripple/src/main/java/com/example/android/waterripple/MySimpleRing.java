package com.example.android.waterripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/4/19.水波纹效果的自定义View
 */
public class MySimpleRing extends View {

    private Paint paint;
    public MySimpleRing(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySimpleRing(Context context) {
        super(context);
        initView();
    }

    public MySimpleRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        //初始化画笔
        paint=new Paint();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
