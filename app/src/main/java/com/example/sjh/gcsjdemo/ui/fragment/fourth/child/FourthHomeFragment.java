package com.example.sjh.gcsjdemo.ui.fragment.fourth.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.activity.CheckinActivity;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.group.MyGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 修改于 19/4/14
 * 用于展示个人信息界面
 */
public class FourthHomeFragment extends SupportFragment {

    private TextView mUserid;//用户学号
    private Button mUser;//个人信息按钮
    private Button mAddClass;//添加课表
    private Button mAddChat;//加入群聊
    private Button mNewChat;//创建群聊

    private String uTitles;//接收用户id

    public static FourthHomeFragment newInstance() {
        Bundle args = new Bundle();
        FourthHomeFragment fragment = new FourthHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_fourth_pager, container, false);
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

        mUser = (Button) view.findViewById(R.id.user_button);
        mAddClass = (Button) view.findViewById(R.id.add_class_button) ;
        mAddChat = (Button) view.findViewById(R.id.add_chat_button) ;
        mNewChat = (Button) view.findViewById(R.id.new_chat_button) ;
        mUserid = (TextView) view.findViewById(R.id.user_text);//欢迎信息：欢迎+同学/老师
        //在最上面打印欢迎XXX
        mUserid.setText(uTitles);//设置欢迎信息姓名

        //3个按钮的监听事件
        mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckinActivity.class);
                startActivity(intent);
            }
        });

        mAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckinActivity.class);
                startActivity(intent);
            }
        });

        mAddChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyGroup.class);//将该页面的学号传递至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  uTitles);
                intent.putExtras(bundle);
                //bundle.putCharSequence("userid",  mUserid.toString());
                startActivity(intent);
                onPause();
            }
        });

        mNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyGroup.class);//将该页面的学号传递至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  uTitles);
                intent.putExtras(bundle);
                //bundle.putCharSequence("userid",  mUserid.toString());
                startActivity(intent);
                onPause();
            }
        });

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
