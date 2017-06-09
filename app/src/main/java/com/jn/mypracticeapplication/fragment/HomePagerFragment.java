package com.jn.mypracticeapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jn.mypracticeapplication.R;
import com.jn.mypracticeapplication.activity.GoodsDetailActivity;
import com.jn.mypracticeapplication.activity.MainActivity;
import com.jn.mypracticeapplication.adapter.HomePagerTopPagerAdapter;
import com.jn.mypracticeapplication.adapter.SimpleGVAdapter;
import com.jn.mypracticeapplication.entity.SimpleGVEntity;
import com.jn.mypracticeapplication.ext.navigator.ScaleCircleNavigator;
import com.jn.mypracticeapplication.util.ScreenUtil;
import com.jn.mypracticeapplication.widget.GridViewForScrollView;
import com.jn.mypracticeapplication.widget.ObservableScrollView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JN on 2017/6/8.
 */

public class HomePagerFragment extends Fragment {

    private static final String[] CHANNELS = new String[]{"AD1", "AD2", "AD3", "AD4", "AD5", "AD6", "AD7"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Integer> mBackgroundList = new ArrayList<>();

    private HomePagerTopPagerAdapter mHomePagerTopPagerAdapter;

    private MagicIndicator magicIndicator;

    private ViewPager mViewPager;

    private GridViewForScrollView gv1;

    private MainActivity mMainActivity;

    private LinearLayout llHomePagerScanningLayout;
    private LinearLayout llHomePagerAdvisoryLayout;
    private View vHomePagerTitleBar;
    private ObservableScrollView svHomePager;
    private RelativeLayout rlTopMagicIndicator;
    private FrameLayout flHomePagerTitleBar;
    private int distanceY;
    private int height = 40;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager,null);

        initView(view);
        initData();
        initEvent();

        return view;
    }

    private void initView(View view) {
        magicIndicator = (MagicIndicator) view.findViewById(R.id.mi_home_pager);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_home_pager);
        llHomePagerScanningLayout = (LinearLayout) view.findViewById(R.id.home_pager_scanning_layout);
        llHomePagerAdvisoryLayout = (LinearLayout) view.findViewById(R.id.home_pager_advisory_layout);
        vHomePagerTitleBar = view.findViewById(R.id.home_pager_title_bar_bg_view);
        svHomePager = (ObservableScrollView) view.findViewById(R.id.sv_home_pager);
        rlTopMagicIndicator = (RelativeLayout) view.findViewById(R.id.rl_home_pager_top_magic_indicator);
        flHomePagerTitleBar = (FrameLayout) view.findViewById(R.id.fl_home_pager_title_bar);

        gv1 = (GridViewForScrollView) view.findViewById(R.id.gv_home_pager);
    }

    private void initData() {
        mBackgroundList.add(R.mipmap.ad1);
        mBackgroundList.add(R.mipmap.ad2);
        mBackgroundList.add(R.mipmap.ad3);
        mBackgroundList.add(R.mipmap.ad4);
        mBackgroundList.add(R.mipmap.ad5);
        mBackgroundList.add(R.mipmap.ad6);
        mBackgroundList.add(R.mipmap.ad7);

        List<SimpleGVEntity> data = new ArrayList<SimpleGVEntity>();
        data.add(new SimpleGVEntity(R.mipmap.gv_item_chicken, "京东超市"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_duck, "全球购"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_goose, "服装城"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_dove, "京东生鲜"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_chicken2, "京东到家"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_duck2, "冲值缴费"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_goose2, "领京豆"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_chickandduck, "领券"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_chickandduck, "惠赚钱"));
        data.add(new SimpleGVEntity(R.mipmap.gv_item_chickandduck, "PLUS会员"));

        SimpleGVAdapter mGVMSCAdapter = new SimpleGVAdapter(getActivity(), data);
        gv1.setAdapter(mGVMSCAdapter);

    }

    private void initEvent() {

        mHomePagerTopPagerAdapter = new HomePagerTopPagerAdapter(getActivity(),mDataList,mBackgroundList);
        mViewPager.setAdapter(mHomePagerTopPagerAdapter);

        initMagicIndicator();


        ViewTreeObserver vto = rlTopMagicIndicator.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlTopMagicIndicator.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                svHomePager.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//                        Log.i("jn","childView.getMeasuredHeight() = " + childView.getMeasuredHeight() + "\n另一个=" + (y-oldy + osvMSC.getHeight()));
//                        Log.i("jn","x=" + x + " y=" + y + " oldx=" + oldx + " oldy=" + oldy);

                        int dy = y - oldy;
                        distanceY += dy;
                        if(svHomePager.getScrollY() == 0){
                            distanceY = 0;
                        }
                        Log.d("PXJN","x=" + x + " y=" + y + " oldx=" + oldx + " oldy=" + oldy);
                        Log.d("PXJN","dy=" + dy);
                        Log.d("PXJN","distanceY=" + distanceY);
                        if (distanceY > ScreenUtil.dip2px(mMainActivity, 20)) {
                            vHomePagerTitleBar.setBackgroundColor(getResources().getColor(R.color.white));
                            if (Build.VERSION.SDK_INT > 10) {
                                vHomePagerTitleBar.setAlpha(distanceY * 1.0f / ScreenUtil.dip2px(mMainActivity, 100));
                            }

                        }
                        else {
                            vHomePagerTitleBar.setBackgroundColor(0);
                        }

                        if (distanceY > ScreenUtil.dip2px(mMainActivity, height) && !llHomePagerScanningLayout.isSelected()) {
                            llHomePagerScanningLayout.setSelected(true);
                            llHomePagerAdvisoryLayout.setSelected(true);
                        }
                        else if (distanceY <= ScreenUtil.dip2px(mMainActivity, height) && llHomePagerScanningLayout.isSelected()) {
                            llHomePagerScanningLayout.setSelected(false);
                            llHomePagerAdvisoryLayout.setSelected(false);
                        }

                    }
                });
            }
        });


        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class));
            }
        });

    }

    private void initMagicIndicator() {
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
}
