package com.example.sjh.gcsjdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sjh.gcsjdemo.ui.fragment.second.child.childpager.FirstPagerFragment;
import com.example.sjh.gcsjdemo.ui.fragment.second.child.childpager.OtherPagerFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class BxzPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public BxzPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagerFragment.newInstance();
        } else {
            return OtherPagerFragment.newInstance(mTitles[position]);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
