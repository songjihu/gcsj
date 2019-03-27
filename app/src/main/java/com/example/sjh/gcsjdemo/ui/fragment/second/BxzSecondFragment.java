package com.example.sjh.gcsjdemo.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.base.BaseMainFragment;
import com.example.sjh.gcsjdemo.ui.fragment.second.child.ViewPagerFragment;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BxzSecondFragment extends BaseMainFragment {

    public static BxzSecondFragment newInstance() {

        Bundle args = new Bundle();

        BxzSecondFragment fragment = new BxzSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_second, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(ViewPagerFragment.class) == null) {
            loadRootFragment(R.id.fl_second_container, ViewPagerFragment.newInstance());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
    }
}
