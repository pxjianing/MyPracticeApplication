package com.jn.mypracticeapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 商品商品页顶部的图片轮播
 * Created by JN on 2017/6/8.
 */

public class GoodsIntroductionTopPagerAdapter extends PagerAdapter {
    private List<String> mDataList;
    private List<Integer> mBackgroundList;
    private Context mContext;

    public GoodsIntroductionTopPagerAdapter(Context mContext, List<String> dataList, List<Integer> backgroundList) {
        this.mContext = mContext;
        this.mDataList = dataList;
        this.mBackgroundList = backgroundList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        TextView textView = new TextView(container.getContext());
        textView.setText(mDataList.get(position));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(24);
        textView.setBackgroundResource(mBackgroundList.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
