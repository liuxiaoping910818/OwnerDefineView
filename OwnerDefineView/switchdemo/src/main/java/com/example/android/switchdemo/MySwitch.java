package com.example.android.switchdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/4/17.
 */
public class MySwitch extends View {

    private Paint mPaint;//画笔
    private Bitmap mBitmapBg;//背景
    private Bitmap mBitmapSlide;//滑块

    private int MAX_LEFT;// 滑块最大左边距
    private int mSlideLeft;// 当前左边距
    private boolean isOpen;// 当前开关状态

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.itheima.myswitch66";

    public MySwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        // 获取属性值，命名空间的使用需要在Valuas里配置
        isOpen = attrs.getAttributeBooleanValue(NAMESPACE, "checked", false);

        // 加载自定义滑块图片
        int slideId = attrs.getAttributeResourceValue(NAMESPACE, "slide", -1);
        if (slideId > 0) {
            mBitmapSlide = BitmapFactory
                    .decodeResource(getResources(), slideId);
        }

        if (isOpen) {
            mSlideLeft = MAX_LEFT;
        } else {
            mSlideLeft = 0;
        }

        invalidate();
    }

    public MySwitch(Context context) {
        super(context);
        initView();
    }

    //程序启动时调用该方法进行初始化工作
    private void initView() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);// 画笔颜色

        // 初始化背景bitmap
        mBitmapBg = BitmapFactory.decodeResource(getResources(),
                R.drawable.switch_background);

        // 初始化滑块bitmap
        mBitmapSlide = BitmapFactory.decodeResource(getResources(),
                R.drawable.slide_button);

        // 滑块最大左边距
        MAX_LEFT = mBitmapBg.getWidth() - mBitmapSlide.getWidth();

        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //click :判断
                if (isClick) {
                    if (isOpen) {
                        // 关闭开关
                        isOpen = false;
                        mSlideLeft = 0;
                    } else {
                        // 打开开关
                        isOpen = true;
                        mSlideLeft = MAX_LEFT;
                    }

                    // view重绘的方法, 刷新view, 重新调用onDraw方法
                    invalidate();

                    // 回调当前开关状态
                    if (mListener != null) {
                        mListener.onCheckChanged(MySwitch.this, isOpen);
                    }
                }
            }
        });
    }

    int startX = 0;
    int moveX = 0;// 位移距离
    boolean isClick;// 标记当前是触摸还是单击事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1. 记录起始点x坐标
                startX = (int) event.getX();// 获取相对于当前控件的x坐标
                break;
            case MotionEvent.ACTION_MOVE:
                // 2. 记录移动后的x坐标
                int endX = (int) event.getX();
                // 3. 记录x偏移量
                int dx = endX - startX;
                // 4. 根据偏移量,更新mSlideLeft
                mSlideLeft += dx;

                moveX += Math.abs(dx);// 向左向右移动都要统计下来, 所以要用dx绝对值

                // 避免滑块超出边界
                if (mSlideLeft < 0) {
                    mSlideLeft = 0;
                }
                // 避免滑块超出边界
                if (mSlideLeft > MAX_LEFT) {
                    mSlideLeft = MAX_LEFT;
                }

                // 5. 刷新界面
                invalidate();
                // 6. 重新初始化起始点坐标
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                // 根据位移判断是单击事件还是移动事件
                if (moveX < 5) {
                    // 单击事件
                    isClick = true;
                } else {
                    // 移动事件
                    isClick = false;
                }

                // 初始化移动的总距离
                moveX = 0;

                if (!isClick) {
                    // 根据当前位置, 切换开关状态
                    if (mSlideLeft < MAX_LEFT / 2) {
                        // 关闭开关
                        mSlideLeft = 0;
                        isOpen = false;
                    } else {
                        // 打开开关
                        mSlideLeft = MAX_LEFT;
                        isOpen = true;
                    }

                    invalidate();
                    // 回调当前开关状态
                    if (mListener != null) {
                        mListener.onCheckChanged(MySwitch.this, isOpen);
                    }
                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    // 设置尺寸回调
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // setMeasuredDimension(100, 100);// 将当前控件宽高设置为100x100
        setMeasuredDimension(mBitmapBg.getWidth(), mBitmapBg.getHeight());// 依据背景图片来确定控件大小
        // System.out.println("onMeasure");
    }

    // measure->layout->draw
    // onMeasure->onLayout->onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制200x200的矩形
        // canvas.drawRect(0, 0, 200, 200, mPaint);
        // System.out.println("onDraw");
        // 绘制背景图片
        canvas.drawBitmap(mBitmapBg, 0, 0, null);
        // 绘制滑块图片
        canvas.drawBitmap(mBitmapSlide, mSlideLeft, 0, null);
    }

    private OnCheckChangeListener mListener;

    // 设置开关状态监听
    public void setOnCheckChangeListener(OnCheckChangeListener listener) {
        mListener = listener;
    }

    /**
     * 监听开关状态的回调接口
     */
    public interface OnCheckChangeListener {
        public void onCheckChanged(View view, boolean isChecked);
    }

}
