package com.example.niancan.spinner;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;


/**
 * Created by NianCan on 2014/8/9.
 */
public class Main extends Activity {

    private EditText et;
    private List<String> list;
    private ListView lv;
    private PopupWindow pw;
    private NumberAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        et = (EditText) findViewById(R.id.et_number);
        findViewById(R.id.select_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectNumberPoupWindow();
            }
        });
    }

    /**
     * 谈出选择号码的框
     */
    private void showSelectNumberPoupWindow() {
        initListView();
        pw = new PopupWindow(lv,et.getWidth()-4,200);
        //获得焦点
        pw.setFocusable(true);

        //点击外部可以被关闭
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        //显示
        pw.showAsDropDown(et, 2, -5);

    }

    /**
     * 创建ListView
     */
    private void initListView() {
        lv = new ListView(this);

        //去掉Item分隔线
        lv.setDivider(null);
        lv.setDividerHeight(0);
        //去掉Scroll
        lv.setVerticalScrollBarEnabled(false);
        lv.setBackgroundResource(R.drawable.listview_background);
        list = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add("80000" + i);
        }
        mAdapter = new NumberAdapter();
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Item");
                String number = list.get(position);
                et.setText(number);
                pw.dismiss();
            }
        });
    }

    class NumberAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
//            View view = null;
//            if (convertView == null) {
//                view = View.inflate(Main.this, R.layout.number_list_item, null);
//            } else {
//                view = convertView;
//            }

            HolderView mHolder = null;
            if (convertView == null) {
                convertView = View.inflate(Main.this, R.layout.number_list_item, null);
                mHolder = new HolderView();
                mHolder.tv = (TextView) convertView.findViewById(R.id.tv_tv);
                mHolder.ib = (ImageButton) convertView.findViewById(R.id.delete);
                convertView.setTag(mHolder);
            }else {
              mHolder = (HolderView) convertView.getTag();
            }
            mHolder.tv.setText(list.get(position));
            mHolder.ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    list.remove(position);
                    mAdapter.notifyDataSetChanged();
                    if (list.size()==0){
                        pw.dismiss();
                    }
                }
            });
            return convertView;
        }
    }

    class HolderView {
        public TextView tv;
        public ImageButton ib;
    }
}
