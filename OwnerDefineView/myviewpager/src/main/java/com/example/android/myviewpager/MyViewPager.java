package com.example.android.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * 自定义ViewPager
 *
 * 1. 写一个类继承ViewGroup
 * 2. 重写onLayout方法, 保证孩子正常显示(一字排开)
 * 3. 重写onTouchEvent, 手势识别器(onScroll),scrollby
 * 4. 监听手指抬起事件,根据当前滑动后位置,确定下一个页面, scrollTo
 * 5. 防止pos过大
 * 6. 平滑移动, Scroller, startScroll->computeScroll
 * 7. 增加RadioButton, 监听自定义viewpager页面切换(回调接口),更改raidoButton选中位置
 * 8. 监听RadioButton切换事件, 更改页面
 * 9. 增加测试页面(ScrollView)
 * 11. 重写onMeasure,测量每个孩子
 * 12. 重写onInterceptTouchEvent, 在适当时机(水平滑动),中断事件传递
 * @author Kevin
 * @date 2015-8-8
 */
public class MyViewPager extends ViewGroup {

    // 手势识别器
    private GestureDetector mDetector;
    private Scroller mScroller;

    public MyViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    // 手势滑动
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                            float distanceX, float distanceY) {

                        scrollBy((int) distanceX, 0);// 滑动x,y方向偏移一定距离, 相对位移
                        // scrollTo(x, y);//绝对位移, 移动到确定的x,y坐标位置

                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }
                });

        // 初始化滑动器
        mScroller = new Scroller(getContext());
    }

    // 测量布局宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 手动测量所有孩子的宽高, 解决测试页面无法展示的bug
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        System.out.println("widthMeasureSpec:" + widthMeasureSpec);
        System.out.println("heightMeasureSpec:" + heightMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);
        System.out.println("width:" + size);

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //MeasureSpec.AT_MOST; //wrap_content
        //MeasureSpec.EXACTLY; //确定值 , 宽高写死, 100dp/ match_parent
        //MeasureSpec.UNSPECIFIED;//没有指定宽高
        System.out.println("mode:" + mode);
    }

    // 设置布局位置

    /**
     *
     * @param changed
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 手动设置孩子们的位置, 保证一字排开
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(0 + i * getWidth(), 0, (i + 1) * getWidth(),
                    getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);// 委托手势识别器处理

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 1.获取当前滑动的位置
                // 2.根据当前位置,确定应该跳到第几个页面
                // 3.跳到确定页面
                int scrollX = getScrollX();
                // System.out.println("scrollX:" + scrollX);
                // 计算应该跳转到下一个页面的哪个位置
                int pos = (scrollX + getWidth() / 2) / getWidth();

                // //350 / 320 = 1
                // int pos = scrollX / getWidth();
                // // 350% 320 = 30
                // int offset = scrollX % getWidth();
                // if (offset > getWidth() / 2) {
                // pos++;
                // }

                // System.out.println("pos:" + pos);

                // 防止pos过大
                if (pos > getChildCount() - 1) {
                    pos = getChildCount() - 1;
                }

                // 移动到确定页面
                // scrollTo(pos * getWidth(), 0);

                setCurrentItem(pos);
                break;

            default:
                break;
        }

        return true;
    }

    // 当调用startScroll后,系统会不断回调此方法
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {// 判断滑动回调有没有结束
            int currX = mScroller.getCurrX();// 获取当前应该移动到的位置
            System.out.println("currX:" + currX);

            scrollTo(currX, 0);// 移动到确定位置
            invalidate();// 刷新界面
        }
    }

    private OnPageChangeListener mListener;

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    //自定义一个接口来设置页面监听
    public interface OnPageChangeListener {
        public void onPageSelected(int position);
    }

    /**
     * 切换到某个具体的页面
     *
     * @param pos
     */
    public void setCurrentItem(int pos) {
        // 平滑的移动到某个位置
        int distance = pos * getWidth() - getScrollX();// 目标位置减去当前位置,获得要滑动的距离
        // 开始滑动, 滑动时间等于距离绝对值,
        // 保证距离越长,时间越久(此方法不会产生滑动,而是会导致不断回调computeScroll,需要在这个方法中处理滑动逻辑)
        mScroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));// 参1:开始x,
        // 参2:开始y;参3:x偏移,参4:y偏移;参5:滑动时间
        invalidate();// 刷新界面,保证滑动器正常运行

        // 页面切换的回调
        if (mListener != null) {
            mListener.onPageSelected(pos);
        }
    }

    int startX;
    int startY;

    // 事件中断 onInterceptTouchEvent->onTouchEvent
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 如果左右滑动, 就需要拦截, 上下滑动,不需要拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDetector.onTouchEvent(ev);// 将ACTION_DOWN传递给手势识别器, 避免事件丢失
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dx = endX - startX;
                int dy = endY - startY;

                if (Math.abs(dx) > Math.abs(dy)) {
                    // 左右滑动
                    return true;// 中断事件传递, 不允许孩子响应事件了, 由父控件处理
                }
                break;

            default:
                break;
        }

        return false;// 不拦截事件,优先传递给孩子处理
    }

    // 事件分发dispatchTouchEvent->onInterceptTouchEvent->onTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}