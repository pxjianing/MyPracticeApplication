package com.jn.mypracticeapplication.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.mypracticeapplication.activity.GoodsDetailActivity;
import com.jn.mypracticeapplication.adapter.GoodsIntroductionTopPagerAdapter;
import com.jn.mypracticeapplication.R;
import com.jn.mypracticeapplication.ext.navigator.ScaleCircleNavigator;
import com.jn.mypracticeapplication.widget.SlideDetailsLayout;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
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
 * 商品商品页
 * Created by JN on 2017/6/7.
 */

public class GoodsIntroductionFragment extends Fragment implements View.OnClickListener, SlideDetailsLayout.OnSlideDetailsListener {

    private Toast toast;

    private static final String[] CHANNELS = new String[]{"第一张", "第二张", "第三张", "第四张", "第五张", "第六张"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Integer> mBackgroundList = new ArrayList<>();

    private GoodsDetailActivity mGoodsDetailActivity;

    private GoodsIntroductionTopPagerAdapter mGoodsIntroductionTopPagerAdapter;

    private ViewPager mViewPager;

    private SlideDetailsLayout sdlSwitch;

    private ScrollView svGoodsIntroduction;

    private MagicIndicator magicIndicator;

    private LinearLayout llPullUp;

    private FloatingActionButton fabUpSlide;

    private TextView tvPriceDecreaseNotice,tvOldPrice;

    //------------------------下拉图文详情-------------------------
    //------------------------------------------------------------

    //------------------------下拉图文详情-------------------------
    private static final String[] CHANNELS1 = new String[]{"商品介绍", "规格参数", "包装售后"};
    private List<String> mDataList1 = Arrays.asList(CHANNELS1);
    private List<Fragment> mFragmentList;

    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private GoodsDetailInfoFragment mGoodsDetailInfoFragment;
    private GoodsDetailFormatFragment mGoodsDetailFormatFragment;
    private GoodsDetailAfterSalePackingFragment mGoodsDetailAfterSalePackingFragment;

    private MagicIndicator magicIndicator1;
    //-------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGoodsDetailActivity = (GoodsDetailActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_introduction,null);

        initView(view);
        initData();
        initEvent();

        //------------------------下拉图文详情-------------------------
        initView1(view);
        initData1();
        initEvent1();

        mFragmentContainerHelper.handlePageSelected(0, false);
        switchPages(0);
        //------------------------------------------------------------

        return view;
    }

    private void initView(View view) {
        sdlSwitch = (SlideDetailsLayout) view.findViewById(R.id.sdl_goods_introduction_switch);
        svGoodsIntroduction = (ScrollView) view.findViewById(R.id.sv_goods_introduction);
        fabUpSlide = (FloatingActionButton) view.findViewById(R.id.fab_goods_introduction_up_slide);

        tvOldPrice = (TextView) view.findViewById(R.id.tv_old_price);

        llPullUp = (LinearLayout) view.findViewById(R.id.ll_goods_introduction_pull_up);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_goods_introduction);
        magicIndicator = (MagicIndicator) view.findViewById(R.id.mi_goods_introduction);

        tvPriceDecreaseNotice = (TextView) view.findViewById(R.id.tv_price_decrease_notice);
    }

    private void initData() {
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
        mBackgroundList.add(R.mipmap.ic_launcher);
    }

    private void initEvent() {
        tvPriceDecreaseNotice.setOnClickListener(this);
        llPullUp.setOnClickListener(this);
        fabUpSlide.setOnClickListener(this);
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fabUpSlide.hide();

        //顶部图片点击事件，京东应该是startActivity
        mGoodsIntroductionTopPagerAdapter = new GoodsIntroductionTopPagerAdapter(getActivity(),mDataList,mBackgroundList);
        mViewPager.setAdapter(mGoodsIntroductionTopPagerAdapter);
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        sdlSwitch.setOnSlideDetailsListener(this);

        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(CHANNELS.length);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_goods_introduction_pull_up:
                //上拉查看图文详情
                sdlSwitch.smoothOpen(true);
                break;
            case R.id.fab_goods_introduction_up_slide:
                //点击滑动到顶部
                svGoodsIntroduction.smoothScrollTo(0, 0);
                sdlSwitch.smoothClose(true);
                break;
            case R.id.tv_price_decrease_notice:
                showToast("降价通知");
        }
    }

    private void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fabUpSlide.show();
            mGoodsDetailActivity.mViewPager.setNoScroll(true);
            mGoodsDetailActivity.tvTop.setVisibility(View.VISIBLE);
            mGoodsDetailActivity.magicIndicator.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            fabUpSlide.hide();
            mGoodsDetailActivity.mViewPager.setNoScroll(false);
            mGoodsDetailActivity.tvTop.setVisibility(View.GONE);
            mGoodsDetailActivity.magicIndicator.setVisibility(View.VISIBLE);
        }
    }

    //------------------------下拉图文详情-------------------------

    private void initView1(View v) {
        magicIndicator1 = (MagicIndicator) v.findViewById(R.id.magic_indicator_goods_intro);
    }

    private void initData1() {
        mFragmentList = new ArrayList<Fragment>();
        mGoodsDetailInfoFragment = new GoodsDetailInfoFragment();
        mGoodsDetailFormatFragment = new GoodsDetailFormatFragment();
        mGoodsDetailAfterSalePackingFragment = new GoodsDetailAfterSalePackingFragment();

        mFragmentList.add(mGoodsDetailInfoFragment);
        mFragmentList.add(mGoodsDetailFormatFragment);
        mFragmentList.add(mGoodsDetailAfterSalePackingFragment);

    }

    private void initEvent1() {

        initMagicIndicator1();
    }

    private void initMagicIndicator1() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS1.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(CHANNELS1[index]);
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
        magicIndicator1.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(getActivity(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator1);



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
            fragmentTransaction.add(R.id.fragment_goods_introduction_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    //------------------------------------------------------------
}
