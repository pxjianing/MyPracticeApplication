package com.jn.mypracticeapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jn.mypracticeapplication.R;

/**
 * Created by ricom on 2017/6/7.
 */

public class GoodsDetailAfterSalePackingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_detail_after_sale_packing,null);
        Log.d("PXJN","GoodsDetailAfterSalePackingFragment->onCreateView");
        return view;
    }
}
