package com.example.sjh.gcsjdemo.ui.fragment.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.base.BaseMainFragment;
import com.example.sjh.gcsjdemo.ui.fragment.third.child.ShopFragment;
import com.example.sjh.gcsjdemo.ui.fragment.third.child.ThirdHomeFragment;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BxzThirdFragment extends BaseMainFragment {

    public static BxzThirdFragment newInstance() {

        Bundle args = new Bundle();

        BxzThirdFragment fragment = new BxzThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_third, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(ThirdHomeFragment.class) == null) {
            // ShopFragment是flow包里的
            loadRootFragment(R.id.fl_third_container, ThirdHomeFragment.newInstance());
        }
    }
}
