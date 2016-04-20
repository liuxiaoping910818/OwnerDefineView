package com.example.android.advertisedem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/17.
 */
public class TestMainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] imgIds=new int[]{

            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };

    // 图片的标题集合
    private final String[] mImageDes = { "巩俐不低俗，我就不能低俗", "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };

    private  TextView tv_title;

    LinearLayout ll_container;

    private int previous;//上一个图片位置

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //获取当前位置的图片
            int currentItem=viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            handler.sendEmptyMessageDelayed(0,2000);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager= (ViewPager) findViewById(R.id.viewPage);

        viewPager.setAdapter(new MyAdapter());

        viewPager.setCurrentItem(imgIds.length*1000);

        //为图片添加标题
        ll_container= (LinearLayout) findViewById(R.id.ll_container);
        tv_title= (TextView) findViewById(R.id.tv_title);

        //为viewPager添加页面尝侦听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //某个页面被选中时调用该方法
            @Override
            public void onPageSelected(int position) {
                //为图上新闻添加标题
                int pos = position % imgIds.length;
                tv_title.setText(mImageDes[pos]);

                //将选中的设置为红色
                ll_container.getChildAt(pos).setEnabled(true);

                //将上一张图片设置为灰色
                ll_container.getChildAt(previous).setEnabled(false);

                //更新当前的图片
                previous=pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //这是添加初始化后的新闻标题，也就是第一张出现的力图片的标题
        tv_title.setText(mImageDes[0]);

        //在图片下面添加5个小圆点
        for (int i=0;i<imgIds.length;i++){

            ImageView imageView=new ImageView(this);

            imageView.setImageResource(R.drawable.shape_point_selector);

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i!=0){

                params.leftMargin=6;
                imageView.setEnabled(false);
            }

            imageView.setLayoutParams(params);

            ll_container.addView(imageView);

        }
        handler.sendEmptyMessageDelayed(0,2000);

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);// 清除所有消息和Runnable对象
                        break;
                    case MotionEvent.ACTION_UP:
                        // 继续轮播广告
                        handler.sendEmptyMessageDelayed(0, 2000);
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
        int pos = position % imgIds.length;

        ImageView view = new ImageView(getApplicationContext());
        // view.setImageResource(mImageIds[position]);
        view.setBackgroundResource(imgIds[pos]);

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
