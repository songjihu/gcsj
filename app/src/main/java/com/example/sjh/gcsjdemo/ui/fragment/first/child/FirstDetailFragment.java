package com.example.sjh.gcsjdemo.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.base.BaseBackFragment;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.ui.fragment.CycleFragment;

/**
 * Created by YoKeyword on 16/6/5.
 * 修改为详细的签到界面
 */
public class FirstDetailFragment extends BaseBackFragment {
    private static final String ARG_ITEM = "arg_item";

    private Article mArticle;

    private Toolbar mToolbar;
    private ImageView mImgDetail;
    private TextView mTvTitle;
    private FloatingActionButton mFab;

    public static FirstDetailFragment newInstance(Article article) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, article);
        FirstDetailFragment fragment = new FirstDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticle = getArguments().getParcelable(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.bxz_fragment_first_detail 点击后的详细界面
        View view = inflater.inflate(R.layout.bxz_fragment_first_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mImgDetail = (ImageView) view.findViewById(R.id.img_detail);
        mTvTitle = (TextView) view.findViewById(R.id.tv_content);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        mToolbar.setTitle("");
        initToolbarNav(mToolbar);
        //mImgDetail.setImageResource(mArticle.getImgRes());
        mTvTitle.setText(mArticle.getTitle());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CycleFragment.newInstance(1));
            }
        });
    }
}
