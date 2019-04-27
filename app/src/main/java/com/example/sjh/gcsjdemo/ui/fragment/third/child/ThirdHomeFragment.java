package com.example.sjh.gcsjdemo.ui.fragment.third.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.ChatActivity;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.ThirdHomeAdapter;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.entity.Friend;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public class ThirdHomeFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView welcomeView;
    private ThirdHomeAdapter mAdapter;//此项为展示待上课程item的适配器
    private List<Friend> friendsList;
    private Handler handler;


    private boolean mInAtTop = true;
    private int mScrollTotal;
    private String uTitles = new String();

    //显示好友列表

    private int friend_number=0;



    /*
    获取好友名单
     */
    private List<Friend> getMyFriends(){
        MyXMPPTCPConnection connection = MyXMPPTCPConnection.getInstance();
        List<Friend> friends = new ArrayList<Friend>();
        //通过Roster.getInstanceFor(connection)获取Roast对象;
        //通过Roster对象的getEntries()获取Set，遍历该Set就可以获取好友的信息了;
        //循环确保有好友
        for(int t=1;friends.isEmpty()&&t<100;t++)
        {
            for(RosterEntry entry : Roster.getInstanceFor(connection).getEntries()){
                Friend friend = new Friend(entry.getUser(), entry.getName());
                friends.add(friend);
                //好友计数器
                friend_number++;
                Log.i("（）（）（）（）（）（）",friend.getJid());
                Log.i("（）（）（）（）（）（）",friend.getName());

            }
        }
        return friends;
    }




    //5个item的标题
    private String[] mTitles = new String[]{

    };


    //5个item的图片
    private String[] mCheck = new String[]{

    };


    public static ThirdHomeFragment newInstance() {

        Bundle args = new Bundle();

        ThirdHomeFragment fragment = new ThirdHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_third_home, container, false);
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
        //接收用户姓名
        uTitles=data;
        Log.i("（）（）（）（）（）（）",data);
    }



    public void initView(View view) {

        mRecy = (RecyclerView) view.findViewById(R.id.recy3);//循环显示的多个item
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout3);//下拉循环布局
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新的颜色
        mAdapter = new ThirdHomeAdapter(_mActivity);//定义item的适配器
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);//设置为流布局并定义manger
        mRecy.setLayoutManager(manager);//循环显示的多个item的布局管理
        mRecy.setAdapter(mAdapter);//循环显示的多个item的适配器设置
        mRefreshLayout.setOnRefreshListener(this);//设置下拉刷新的对象
        handler=new Handler();//创建属于主线程的handler

        // 获取好友名单
        friendsList = getMyFriends();

        //设置数据到适配器
        mAdapter.setDatas(friendsList);

        //点击item的事件监听，开启新的fragment
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {

                //发送数据并开始聊天
                EventBus.getDefault().postSticky(friendsList.get(position).getJid());
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);

            }
        });
        //点击签到按钮的事件监听，开启新的fragment

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

        //实时刷新列表
        new Thread(new Runnable() {
             @Override
            public void run() {
                 boolean flg = false;
                 while(!flg){
                     try {
                         handler.post(runnableUi);
                         Thread.sleep(3000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }) .start();

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 获取好友名单
                List<Friend> friendsList = getMyFriends();
                //设置数据到适配器
                mAdapter.setDatas(friendsList);
                mRecy.setAdapter(mAdapter);
                mRecy.scrollToPosition(mAdapter.getItemCount()-1);//此句为设置显示
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
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

    // 构建Runnable对象，在runnable中更新界面
    Runnable  runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            //获取好友名单
            List<Friend> friendsList = getMyFriends();
            //设置数据到适配器
            mAdapter.setDatas(friendsList);
            mRecy.setAdapter(mAdapter);
            mRecy.scrollToPosition(mAdapter.getItemCount()-1);//此句为设置显示
        }

    };
}
