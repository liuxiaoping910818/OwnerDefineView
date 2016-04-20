package com.example.android.advertisedem;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TextView tvTitle;
    private LinearLayout llContainer;

    private int[] mImageIds = new int[] { R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d, R.drawable.e };

    // 图片标题集合
    private final String[] mImageDes = { "巩俐不低俗，我就不能低俗", "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };

    private int mPreviousPos;// 上一个页面位置

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int currentItem = mViewPager.getCurrentItem();// 获取当前页面位置
            mViewPager.setCurrentItem(++currentItem);// 跳到下一个页面

            // 继续发送延时2秒的消息, 形成类似递归的效果, 使广告一直循环切换
            mHandler.sendEmptyMessageDelayed(0, 2000);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPage);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);

        mViewPager.setAdapter(new MyAdapter());// 给viewpager设置数据
        // mViewPager.setCurrentItem(Integer.MAX_VALUE/2);
        mViewPager.setCurrentItem(mImageIds.length * 10000);

        // 设置滑动监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // 某个页面被选中
            @Override
            public void onPageSelected(int position) {
                int pos = position % mImageIds.length;
                tvTitle.setText(mImageDes[pos]);// 更新新闻标题

                // 更新小圆点
                llContainer.getChildAt(pos).setEnabled(true);// 将选中的页面的圆点设置为红色
                // 将上一个圆点变为灰色
                llContainer.getChildAt(mPreviousPos).setEnabled(false);

                // 更新上一个页面位置
                mPreviousPos = pos;
            }

            // 滑动过程中
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            // 滑动状态变化
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvTitle.setText(mImageDes[0]);// 初始化新闻标题

        // 动态添加5个小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.shape_point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i != 0) {// 从第2个圆点开始设置左边距, 保证圆点之间的间距
                params.leftMargin = 6;
                view.setEnabled(false);// 设置不可用, 变为灰色圆点
            }

            view.setLayoutParams(params);

            llContainer.addView(view);
        }

        // 发送一个延时2秒更新广告条的消息,在handlerMessage里里操作
        mHandler.sendEmptyMessageDelayed(0, 2000);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);// 清除所有消息和Runnable对象
                        break;
                    case MotionEvent.ACTION_UP:
                        // 继续轮播广告
                        mHandler.sendEmptyMessageDelayed(0, 2000);
                        break;

                    default:
                        break;
                }

                return false;// 返回false, 让viewpager原生触摸效果正常运行
            }
        });
    }

    class MyAdapter extends PagerAdapter {

        // 返回item的个数
        @Override
        public int getCount() {
            // return mImageIds.length;
            return Integer.MAX_VALUE;
        }

        // 判断当前要展示的view和返回的object是否是一个对象, 如果是,才展示
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 类似getView方法, 初始化每个item的布局, viewpager默认自动加载前一张和后一张图片, 保证始终保持3张图片,
        // 剩余的都需要销毁
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 0,1, 5->0, 6->1, 10->0
            int pos = position % mImageIds.length;

            ImageView view = new ImageView(getApplicationContext());
            // view.setImageResource(mImageIds[position]);
            view.setBackgroundResource(mImageIds[pos]);

            // 将item的布局添加给容器
            container.addView(view);
            // System.out.println("初始化item..." + pos);

            return view;// 返回item的布局对象
        }

        // item销毁的回调方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 从容器中移除布局对象
            container.removeView((View) object);
            // System.out.println("销毁item..." + position);
        }

    }

}
