package com.example.sjh.gcsjdemo.ui.fragment.second.child;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.CheckinActivity;
import com.example.sjh.gcsjdemo.MainActivity;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.TimeActivity;
import com.example.sjh.gcsjdemo.adapter.FirstHomeAdapter;
import com.example.sjh.gcsjdemo.adapter.SecondHomeAdapter;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.entity.Reminder;
import com.example.sjh.gcsjdemo.event.TabSelectedEvent;
import com.example.sjh.gcsjdemo.helper.DetailTransition;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.ui.fragment.first.child.FirstDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 修改于 19/4/14
 * 用于展示提醒的item
 */
public class SecondHomeFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private SecondHomeAdapter mAdapter;//此项为展示待上课程item的适配器
    private Button mStudylog;//学习记录的按钮
    private Button mPublish;//发布通知
    private Button mRemind;//发布提醒

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private String uTitles;//接收用户id


    //5个item的标题
    private String[] mRemindermsg = new String[]{
            "第2节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n" + "待交作业：\n" + "①\n" + "②\n" + "③",
            "第2节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n" + "待交作业：\n" + "①\n" + "②\n" + "③",
            "第2节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n" + "待交作业：\n" + "①\n" + "②\n" + "③",
            "第2节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n" + "待交作业：\n" + "①\n" + "②\n" + "③",
            "第2节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n" + "待交作业：\n" + "①\n" + "②\n" + "③",
    };

    public static SecondHomeFragment newInstance() {
        Bundle args = new Bundle();
        SecondHomeFragment fragment = new SecondHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_second_pager1, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    /*
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(getActivity(), event.message, Toast.LENGTH_SHORT).show();
    }
*/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String data) {
        //接收用户id
        uTitles=data;
        Log.i("----------------------",data);
    }



    public void initView(View view) {

        mRecy = (RecyclerView) view.findViewById(R.id.recy2);//循环显示的多个item
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout2);//下拉循环布局
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新的颜色
        mRefreshLayout.setOnRefreshListener(this);//设置下拉刷新的对象
        mAdapter = new SecondHomeAdapter(_mActivity);//定义item的适配器
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);//设置为流布局并定义manger
        mRecy.setLayoutManager(manager);//循环显示的多个item的布局管理
        mRecy.setAdapter(mAdapter);//循环显示的多个item的适配器设置
        mStudylog = (Button) view.findViewById(R.id.study_log) ;
        mPublish = (Button) view.findViewById(R.id.publish_notice) ;
        mRemind = (Button) view.findViewById(R.id.publish_remind) ;

        //3个按钮的监听事件
        mStudylog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimeActivity.class);
                startActivity(intent);
            }
        });

        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckinActivity.class);
                startActivity(intent);
            }
        });

        mRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckinActivity.class);
                startActivity(intent);
            }
        });


        // 在list中循环显示8个item
        List<Reminder> reminderList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int index = i % 5;
            Reminder reminder = new Reminder(mRemindermsg[index]);
            reminderList.add(reminder);
        }
        //设置数据到适配器
        mAdapter.setDatas(reminderList);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });



    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.FIRST) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
