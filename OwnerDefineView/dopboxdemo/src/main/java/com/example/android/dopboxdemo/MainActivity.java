package com.example.android.dopboxdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * 下拉框练习
 */

public class MainActivity extends AppCompatActivity {

    private ListView lvlist;
    private EditText et_content;
    private ArrayList<String> mList;
    private PopupWindow mPopupWin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_Drop= (ImageView) findViewById(R.id.iv_drop);

        et_content= (EditText) findViewById(R.id.et_content);

        iv_Drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDropDown();
            }
        });

        lvlist=new ListView(this);

        mList=new ArrayList<>();
        for (int i=0;i<100;i++){

            mList.add("adc"+i);
        }

        lvlist.setAdapter(new MyAdapter());


        //点击Item显示到Editexth
        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_content.setText(mList.get(position));
                mPopupWin.dismiss();
            }
        });


    }

    private void showDropDown() {

        if (mPopupWin==null){

            mPopupWin=new PopupWindow(lvlist,et_content.getWidth(),200,true);
            mPopupWin.setBackgroundDrawable(new ColorDrawable());
        }
        mPopupWin.showAsDropDown(et_content);
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_item, null);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView
                        .findViewById(R.id.tv_content);
                holder.ivDelete = (ImageView) convertView
                        .findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvContent.setText(getItem(position));
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    MyAdapter.this.notifyDataSetChanged();
                }
            });

            return convertView;
        }

    }

    static class ViewHolder {
        public TextView tvContent;
        public ImageView ivDelete;
    }
}
