package com.example.android.waterripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MyRingWave extends View {

    private static final int MIN_DIS=10;//圆环之间的最小的间距

    //圆的集合
    private ArrayList<Wave> mWaveList=new ArrayList<>();

    //颜色的集合
    private  int[] mMolors=new int[]{

            Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE
    };


    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            flushData();
            invalidate();
            if (!mWaveList.isEmpty()){

                handler.sendEmptyMessageDelayed(0,50);
            }
        }
    };

    //刷新数据
    private void flushData() {

        //遍历圆环的集合，修改每个圆的尺寸

        //需要称除的集合
        ArrayList<Wave> removeList=new ArrayList<>();
        for (Wave wave:mWaveList){

            wave.radius+=3;
            wave.paint.setStrokeWidth(wave.radius/3);

            if (wave.paint.getAlpha()<=0){

                removeList.add(wave);
                continue;
            }

            int alpha=wave.paint.getAlpha();
            alpha-=5;
            if (alpha<0){

                alpha=0;
            }

            wave.paint.setAlpha(alpha);
        }
        mWaveList.removeAll(removeList);

    }

    public MyRingWave(Context context) {
        super(context);
    }

    public MyRingWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRingWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    //开始绘制圆
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Wave wave:mWaveList)

            canvas.drawCircle(wave.cx,wave.cy,wave.radius,wave.paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                addPoint((int) event.getX(), (int)event.getY());
                break;
            default:
                break;
        }
        return true;
    }

    private void addPoint(int x, int y) {

        if (mWaveList.isEmpty()){

            addWave(x,y);
            handler.sendEmptyMessage(0);
        }else {

            Wave lastWave=mWaveList.get(mWaveList.size()-1);
            //判断将要添加的一圆和最后的一个圆的距离是否达到 一定的值
            if (Math.abs(x-lastWave.cx)>MIN_DIS ||Math.abs(y-lastWave.cy)>MIN_DIS){

                addWave(x,y);
            }
        }

    }

    //添加 一个波浪
    private void addWave(int x, int y) {

        Wave wave=new Wave();
        wave.cx=x;
        wave.cy=y;

        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(wave.radius / 3);
        paint.setAlpha(255);

        //设置随机的颜色，
        int colorIndex= (int) (Math.random()*4);
        paint.setColor(mMolors[colorIndex]);
        wave.paint=paint;
        mWaveList.add(wave);

    }

    class Wave{

        public  int cx;
        public  int cy;
        public  int radius;
        public Paint paint;
    }
}
