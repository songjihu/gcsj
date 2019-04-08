package com.example.sjh.gcsjdemo.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sjh.gcsjdemo.base.BaseMainFragment;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.ui.fragment.first.child.FirstHomeFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class BxzFirstFragment extends BaseMainFragment {

    public static BxzFirstFragment newInstance(String name) {

        //传递用户姓名（并没有采用这种方式）

        Bundle args = new Bundle();
        args.putString("username", name);
        BxzFirstFragment fragment = new BxzFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_first, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(FirstHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, FirstHomeFragment.newInstance());
        }
    }

}
