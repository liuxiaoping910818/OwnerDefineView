package com.example.android.myviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2016/4/18.
 */
public class IndexActivity extends AppCompatActivity {

    MyViewPager myViewPager;

    private int[] imgIds=new int[]{

            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6

    };

    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager= (MyViewPager) findViewById(R.id.mypager);

        for (int i=0;i<imgIds.length;i++){

            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imgIds[i]);
            myViewPager.addView(imageView);
        }

        //添加测试页面
        View testView=View.inflate(this,R.layout.list_item,null);
        myViewPager.addView(testView,1);

        //初始化Radiobutton
        group= (RadioGroup) findViewById(R.id.rg_group);
        for (int i=0;i<imgIds.length;i++){

            RadioButton radioButton=new RadioButton(this);
            //以当前的位置为id
            radioButton.setId(i);

            if (i==0){

                //将每个页面默认设置为选中
                radioButton.setChecked(true);
            }
            group.addView(radioButton);

        }
        myViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int id=position;//因为当前的位置就是其id，所以让id等于当前的位置
                group.check(id);
            }
        });

        //radioButto被选中的中监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int position=checkedId;//id和position相同，可以直接将Id当做Position来使用
                myViewPager.setCurrentItem(position);

            }
        });
    }

}
