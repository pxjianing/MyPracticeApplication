package com.jn.mypracticeapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by JN on 2017/6/7.
 */

public class GoodsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public GoodsPagerAdapter(FragmentManager fm,List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = mFragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

}
