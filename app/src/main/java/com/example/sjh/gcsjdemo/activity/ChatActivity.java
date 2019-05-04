package com.example.sjh.gcsjdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.ChatAdapter;
import com.example.sjh.gcsjdemo.entity.ChatMessage;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 聊天界面
 */
public class ChatActivity extends AppCompatActivity implements ChatManagerListener, ChatMessageListener, View.OnClickListener {
    private ListView chatListView;
    private EditText et_chat;
    private Button sendBtn;
    private MyXMPPTCPConnection connection;
    private ChatManager chatManager;
    private List<ChatMessage> messageList;
    private String friendJid;
    private Chat chat0,chat1;
    //private List<Chat> chat;
    private ChatAdapter adapter;
    private String with_friends[];
    private Boolean init_flag = false;

    //接受处理消息
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0:
                    ChatMessage chatMessage = new ChatMessage((String) msg.obj, 1);
                    messageList.add(chatMessage);
                    adapter.notifyDataSetChanged();//显示内容
                    Log.i("1发送11111111111111111","1");
                    chatListView.setSelection(messageList.size() - 1);
                    break;
                default:
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String[] data) {
        //接收jid
        //friendJid=data;
        with_friends=data;
        Log.i("*****-----1----******",with_friends[0]);
        Log.i("*****-----2----******",with_friends[1]);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //聊天界面布局
        setContentView(R.layout.act_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        messageList = new ArrayList<ChatMessage>();
        //friendJid = getIntent().getStringExtra("friend_jid");
        initView();
        initListener();
        initChatManager();
        initChat();
        adapter = new ChatAdapter(ChatActivity.this, messageList);
        chatListView.setAdapter(adapter);
        chatListView.setSelection(messageList.size() - 1);

        //先处理离线消息
        OfflineMessageManager offlineMessageManager=new OfflineMessageManager(connection);
        List<Message> messages= null;

        try {
            messages =  offlineMessageManager.getMessages();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        //处理后上线

    }

    private void initView(){
        chatListView = (ListView) findViewById(R.id.chatListView);
        et_chat = (EditText) findViewById(R.id.chatEditText);
        sendBtn = (Button) findViewById(R.id.sendBtn);
    }

    private void initListener(){
        sendBtn.setOnClickListener(this);
    }

    private void initChatManager(){
        connection = MyXMPPTCPConnection.getInstance();
        if(connection != null){
            chatManager = ChatManager.getInstanceFor(connection);
            chatManager.addChatListener(this);
        }
    }

    private void initChat(){
        if(chatManager != null){
            //第一个参数是 用户的ID
            //第二个参数是 ChatMessageListener，我们这里传null就好了
            //群组共计3个成员
            chat0 = chatManager.createChat(with_friends[0], null);
            chat1 = chatManager.createChat(with_friends[1], null);

        }
    }

    //给群组中的每个人都发消息
    private void sendChatMessage(String msgContent){
        ChatMessage chatMessage = new ChatMessage(msgContent, 0);
        messageList.add(chatMessage);//加入list自己发送的消息
        if(chat0 != null && chat1 != null){
            try {
                //发送消息，参数为发送的消息内容
                chat0.sendMessage(msgContent);
                Log.i("0发送",msgContent);
                chat1.sendMessage(msgContent);
                Log.i("1发送",msgContent);
                et_chat.setText("");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();//修改显示内容
        chatListView.setSelection(messageList.size() - 1);
    }

    //ChatListener中需要重写的方法
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        //在这里面给chat对象添加ChatMessageListener
        chat.addMessageListener(this);
    }

    //ChatMessageListener中需要重写的方法
    //当接收到对方发来的消息的时候，就会回调processMessage方法
    @Override
    public void processMessage(Chat chat, Message message) {
        if(message.getType().equals(Message.Type.chat) || message.getType().equals(Message.Type.normal)){
            if(message.getBody() != null){
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 0;
                msg.obj = message.getBody();
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn:
                if(!TextUtils.isEmpty(et_chat.getText().toString())){
                    sendChatMessage(et_chat.getText().toString());
                }else{
                    Toast.makeText(ChatActivity.this, "消息不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销bus
    }

}
