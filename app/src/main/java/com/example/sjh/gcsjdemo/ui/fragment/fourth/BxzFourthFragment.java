package com.example.sjh.gcsjdemo.ui.fragment.fourth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.base.BaseMainFragment;
import com.example.sjh.gcsjdemo.ui.fragment.fourth.child.AvatarFragment;
import com.example.sjh.gcsjdemo.ui.fragment.fourth.child.FourthHomeFragment;
import com.example.sjh.gcsjdemo.ui.fragment.fourth.child.MeFragment;
import com.example.sjh.gcsjdemo.ui.fragment.second.child.SecondHomeFragment;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BxzFourthFragment extends BaseMainFragment {
    private Toolbar mToolbar;
    private View mView;

    public static BxzFourthFragment newInstance() {

        Bundle args = new Bundle();

        BxzFourthFragment fragment = new BxzFourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bxz_fragment_second, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (findChildFragment(ViewPagerFragment.class) == null) {
//          loadRootFragment(R.id.fl_second_container, ViewPagerFragment.newInstance());
//      }
        if (findChildFragment(FourthHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_second_container, FourthHomeFragment.newInstance());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //if (findChildFragment(AvatarFragment.class) == null) {
            //loadFragment();
        //}

        //mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        //mToolbar.setTitle(R.string.me);
    }

    private void loadFragment() {
        //loadRootFragment(R.id.fl_fourth_container_upper, AvatarFragment.newInstance());
        //loadRootFragment(R.id.fl_fourth_container_lower, MeFragment.newInstance());

    }

    public void onBackToFirstFragment() {
        _mBackToFirstListener.onBackToFirstFragment();
    }
}
