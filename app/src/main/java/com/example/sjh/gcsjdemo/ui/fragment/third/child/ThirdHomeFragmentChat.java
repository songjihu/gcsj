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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.ThirdHomeAdapter;
import com.example.sjh.gcsjdemo.dbmanager.ChatUtil;
import com.example.sjh.gcsjdemo.entity.ChatMessage;
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
import com.mysql.jdbc.exceptions.MySQLQueryInterruptedException;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    private MyXMPPTCPConnection connection;//离线的连接
    private MyXMPPTCPConnectionOnLine connection_online;//设置在线的连接

    final CountDownLatch countDownLatch = new CountDownLatch(1);//进程结束标志
    final String team_id[] = new String[20];//加入的讨论组id
    final int team_location [] = new int[20];//加入的讨论组列的位置
    final String team_name[] = new String[20];//加入的讨论组name
    final String team_member[][] = new String[20][20];//加入的讨论组人员组成
    private String user_name;//用户名



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
        connection_online = MyXMPPTCPConnectionOnLine.getInstance();
        connection = MyXMPPTCPConnection.getInstance();



        //uTitles="20162430722";
        //读取加入的群组名称和个数
        new Thread(new Runnable() {
            @Override
            public void run() {
            try{
                int team_number;//一共有几个讨论组
                int i,j;
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                Statement st=(Statement)cn.createStatement();


                //读取team的id
                String sql="SELECT * FROM `user_team` WHERE user_id = "+uTitles;
                ResultSet rs=st.executeQuery(sql);
                //while(rs.next()){
                rs.next();
                for(j=2,i=0;j<14;j++)//根据课程数确定循环次数
                {
                    if(rs.getString(j)==null)
                    {
                        //是空跳过，否则存入team_id
                    }
                    else{
                        team_id[i]=rs.getString(j);//j为第几列
                        team_location[i]=j;
                        Log.i("-_-_-_-_-_-_-_-_",i+":"+rs.getString(j));
                        i++;
                    }

                }
                team_number=i;

                //sql列名
                //while(rs.next()){

                //读取team名字
                for(i=0;i<team_number;i++)
                {
                    sql="SELECT * FROM `team_info` WHERE team_id = "+team_id[i];
                    rs=st.executeQuery(sql);
                    if(rs.next()) ; else break;
                    team_name[i]=rs.getString("team_name");
                    Log.i("-_-_-_-_-_-_-_-_",i+":"+team_name[i]);
                }

                //读取小组成员
                sql="SELECT * FROM `user_team`";
                rs=st.executeQuery(sql);

                while(rs.next()){
                    for (i=0;i<team_number;i++){
                        if(rs.getString(team_location[i])!=null&&rs.getString(team_location[i]).equals(team_id[i])){
                            //加入二维数组
                            if(rs.getString(team_location[i]).equals(uTitles))
                            {
                                //跳过自己
                            }else{
                                j=0;
                                while (team_member[i][j]!=null){j++;}//循环到为空的位置
                                team_member[i][j]=rs.getString(1);//加入学号
                                //Log.i("成员",j+":"+team_member[i][j]);
                            }

                        }else {
                            //啥都不干
                        }
                    }
                }

                //读取小组成员
                sql="SELECT user_name FROM `user` WHERE user_id =" +uTitles;
                rs=st.executeQuery(sql);

                while(rs.next()){
                    user_name=rs.getString("user_name");
                    Log.i("11111111111111111111111",user_name);
                }

                team_location[19]=team_number;//传出讨论组个数
/*
                for (i=0;i<team_number;i++)
                    for (j=0;j<20;j++)
                    {
                        if(team_member[i][j]==null) break;
                        Log.i("组",i+":");
                        Log.i("成员",j+":"+team_member[i][j]);
                    }
*/


                //关闭
                cn.close();
                st.close();
                rs.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
                countDownLatch.countDown();
            }

        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取好友名单
        //friendsList = getMyFriends();
        //设置适配器内容
        //TODO: 从数据库读取最新的聊天记录到本地数据库，本应用中用json格式当做消息体


        //结束离线状态，进入在线状态
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if(connection.isConnected())
                    {
                        connection.disconnect();
                        connection.instantShutdown();
                    }
                    if(!connection_online.isConnected())
                    {
                        connection_online.connect();
                    }
                    connection_online.login(uTitles,uTitles);
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //connection.disconnect();
                countDownLatch.countDown();
            }

        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dialogsAdapter.setItems(DialogsFixtures.getDialogsChat(team_name,team_member,team_location[19]));
        //设置监听器，长按或者点击的时间
        dialogsAdapter.setOnDialogClickListener(this);
        //设置数据到适配器
        dialogsList.setAdapter(dialogsAdapter);
        //mAdapter.setDatas(friendsList);
        //将设置好的适配器配置给List

        //ChatUtil ru = new ChatUtil();
        //ru.insertMsg(new ChatMessage("132"));


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


    //TODO:打开此界面时，先更新未收到的聊天记录，加载到数据库，再由数据库更新适配器
    public void onReupdate()
    {

    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable  runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            //TODO: 修改函数，从数据库读取最新的聊天dialog，加载到适配器中
            dialogsAdapter.setItems(DialogsFixtures.getDialogsChat(team_name,team_member,team_location[19]));
            //设置数据到适配器
            //dialogsList.setAdapter(dialogsAdapter);
            //mRecy.scrollToPosition(mAdapter.getItemCount()-1);//此句为设置显示
        }

    };


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
        String temp = dialog.getId();
        int t = Integer.parseInt(temp);
        team_member[t][19]=uTitles;//存入自己是谁
        team_member[t][18]=team_id[t];//存入群组id
        team_member[t][17]=user_name;//存入自己叫啥
        EventBus.getDefault().postSticky(team_member[t]);//发送小组成员
        CustomHolderMessagesActivity.open(getActivity());

    }
}
