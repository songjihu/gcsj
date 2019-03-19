package com.example.sjh.gcsjdemo.ui.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;

import me.yokeyword.fragmentation.SupportFragment;

import com.example.sjh.gcsjdemo.ui.fragment.fourth.BxzFourthFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class MeFragment extends SupportFragment {
    private TextView mTvBtnSettings;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_fourth_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBtnSettings = (TextView) view.findViewById(R.id.tv_btn_settings);
        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SettingsFragment.newInstance());
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((BxzFourthFragment)getParentFragment()).onBackToFirstFragment();
        return true;
    }
}
