package com.example.sjh.gcsjdemo.ui.fragment.third.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.ThirdHomeAdapter;
import com.example.sjh.gcsjdemo.entity.Friend;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.media.DemoDialogsActivity;
import com.example.sjh.gcsjdemo.media.data.fixtures.DialogsFixtures;
import com.example.sjh.gcsjdemo.media.data.model.Dialog;
import com.example.sjh.gcsjdemo.media.holder.CustomHolderDialogsActivity;
import com.example.sjh.gcsjdemo.media.holder.CustomHolderMessagesActivity;
import com.example.sjh.gcsjdemo.media.holder.holders.dialogs.CustomDialogViewHolder;
import com.example.sjh.gcsjdemo.utils.AppUtils;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnection;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnectionOnLine;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public  class ThirdHomeFragmentChat extends SupportFragment implements DialogsListAdapter.OnDialogClickListener<Dialog> {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private ThirdHomeAdapter mAdapter;//此项为展示待上课程item的适配器
    private List<Friend> friendsList;
    private DialogsList dialogsList;//会话列表
    private DialogsListAdapter<Dialog> dialogsAdapter;//会话适配器
    private ImageLoader imageLoader;//图片加载
    private Handler handler;
    private MyXMPPTCPConnectionOnLine connectionOnLine;//设置在线的连接




    private boolean mInAtTop = true;
    private int mScrollTotal;
    private String uTitles = new String();

    //显示好友列表

    private int friend_number=0;

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomHolderDialogsActivity.class));
    }

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

                //Log.i("（）（）（）（）（）（）",friend_number+friend.getJid());
                //Log.i("（）（）（）（）（）（）",friend.getName());


            }
        }
        return friends;
    }



    //5个item的图片
    private String[] mCheck = new String[]{

    };

    private String[] with_friends = new String[]{
            "20162430722@gcsj-app",
            "20162430723@gcsj-app"};
    //群组其他人的idwith_friends


    public static ThirdHomeFragmentChat newInstance() {

        Bundle args = new Bundle();

        ThirdHomeFragmentChat fragment = new ThirdHomeFragmentChat();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_custom_holder_dialogs, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        EventBus.getDefault().register(this);
        //设置监听器，长按或者点击的时间
        //dialogsAdapter.setOnDialogClickListener(this);
        //dialogsAdapter.setOnDialogLongClickListener(this);
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
        //Log.i("（）（）（）（）（）（）",data);
    }



    public void initView(View view) {
        dialogsList = (DialogsList) view.findViewById(R.id.dialogsList);//会话列表
        //mAdapter = new ThirdHomeAdapter(_mActivity);//定义item的适配器
        dialogsAdapter= new DialogsListAdapter<>(
                R.layout.item_custom_dialog_view_holder,
                CustomDialogViewHolder.class,
                imageLoader);

        handler=new Handler();//创建属于主线程的handler

        // 获取好友名单
        //friendsList = getMyFriends();
        //设置适配器内容
        dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        //设置监听器，长按或者点击的时间
        dialogsAdapter.setOnDialogClickListener(this);


        //设置数据到适配器
        //mAdapter.setDatas(friendsList);
        //将设置好的适配器配置给List
        dialogsList.setAdapter(dialogsAdapter);

    }


    //TODO:打开此界面时，先更新未收到的聊天记录，加载到数据库，再将数据库中聊天记录创建在本地
    public void onReupdate()
    {

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


    @Override
    public void onDialogClick(Dialog dialog) {
        CustomHolderMessagesActivity.open(getActivity());
    }
}
