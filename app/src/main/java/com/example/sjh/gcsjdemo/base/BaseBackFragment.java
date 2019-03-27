package com.example.sjh.gcsjdemo.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import me.yokeyword.fragmentation.SupportFragment;
import com.example.sjh.gcsjdemo.R;

/**
 * Created by YoKeyword on 16/2/7.
 */
public class BaseBackFragment extends SupportFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
