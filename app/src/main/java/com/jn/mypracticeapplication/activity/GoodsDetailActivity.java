package com.jn.mypracticeapplication.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.mypracticeapplication.R;
import com.jn.mypracticeapplication.adapter.GoodsPagerAdapter;
import com.jn.mypracticeapplication.fragment.GoodsCommentsFragment;
import com.jn.mypracticeapplication.fragment.GoodsDetailsFragment;
import com.jn.mypracticeapplication.fragment.GoodsIntroductionFragment;
import com.jn.mypracticeapplication.widget.MyNoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JN on 2017/6/7.
 */
public class GoodsDetailActivity extends AppCompatActivity {

    private static final String[] CHANNELS = new String[]{"商品", "详情", "评价"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    private GoodsPagerAdapter mGoodsPagerAdapter;

    public MyNoScrollViewPager mViewPager;
    public TextView tvTop;
    public MagicIndicator magicIndicator;

    private List<Fragment> fragmentList;
    private GoodsIntroductionFragment mGoodsIntroductionFragment;
    private GoodsDetailsFragment mGoodsDetailsFragment;
    private GoodsCommentsFragment mGoodsCommentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        initData();
        initView();
        initEvent();
    }

    private void initData() {
        fragmentList = new ArrayList<Fragment>();
        mGoodsIntroductionFragment = new GoodsIntroductionFragment();
        mGoodsDetailsFragment = new GoodsDetailsFragment();
        mGoodsCommentsFragment = new GoodsCommentsFragment();

        fragmentList.add(mGoodsIntroductionFragment);
        fragmentList.add(mGoodsDetailsFragment);
        fragmentList.add(mGoodsCommentsFragment);
    }

    private void initView() {
        mViewPager = (MyNoScrollViewPager) findViewById(R.id.view_pager_goods);
        tvTop = (TextView) findViewById(R.id.tv_goods_detail_top);
    }

    private void initEvent() {
        mGoodsPagerAdapter = new GoodsPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mGoodsPagerAdapter);

        initMagicIndicator();
    }


    private void initMagicIndicator() {
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator_goods);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(Color.BLACK);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
//        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
//        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        titleContainer.setDividerDrawable(new ColorDrawable() {
//            @Override
//            public int getIntrinsicWidth() {
//                return UIUtil.dip2px(GoodsDetailActivity.this, 5);
//            }
//        });
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    public void back(View view) {
        finish();
    }

    public void share(View view) {
        Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
    }

    public void more(View view) {
        Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popwindow_goods_detail, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
        ColorDrawable dw = new ColorDrawable(0);
        popupWindow.setBackgroundDrawable(dw);

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }


}
