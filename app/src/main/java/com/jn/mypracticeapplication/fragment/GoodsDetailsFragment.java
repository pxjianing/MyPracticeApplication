package com.jn.mypracticeapplication.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jn.mypracticeapplication.R;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JN on 2017/6/7.
 */

public class GoodsDetailsFragment extends Fragment {

    private static final String[] CHANNELS = new String[]{"商品介绍", "规格参数", "包装售后"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Fragment> mFragmentList;

    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private GoodsDetailInfoFragment mGoodsDetailInfoFragment;
    private GoodsDetailFormatFragment mGoodsDetailFormatFragment;
    private GoodsDetailAfterSalePackingFragment mGoodsDetailAfterSalePackingFragment;

    private MagicIndicator magicIndicator;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_details,null);
        Log.d("PXJN","GoodsDetailsFragment->onCreateView");
        initView(view);
        initData();
        initEvent();

        mFragmentContainerHelper.handlePageSelected(0, false);
        switchPages(0);

        return view;
    }

    private void initView(View v) {
        magicIndicator = (MagicIndicator) v.findViewById(R.id.magic_indicator1);
    }

    private void initData() {
        mFragmentList = new ArrayList<Fragment>();
        mGoodsDetailInfoFragment = new GoodsDetailInfoFragment();
        mGoodsDetailFormatFragment = new GoodsDetailFormatFragment();
        mGoodsDetailAfterSalePackingFragment = new GoodsDetailAfterSalePackingFragment();

        mFragmentList.add(mGoodsDetailInfoFragment);
        mFragmentList.add(mGoodsDetailFormatFragment);
        mFragmentList.add(mGoodsDetailAfterSalePackingFragment);

    }

    private void initEvent() {

        initMagicIndicator();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(CHANNELS[index]);
                clipPagerTitleView.setTextColor(Color.GRAY);
                clipPagerTitleView.setClipColor(Color.RED);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index);
                        switchPages(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                if (index == 0) {
                    return 1.0f;
                } else if (index == 1) {
                    return 1.0f;
                } else {
                    return 1.0f;
                }
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(getActivity(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);



    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = mFragmentList.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = mFragmentList.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = mFragmentList.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_goods_detail_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
