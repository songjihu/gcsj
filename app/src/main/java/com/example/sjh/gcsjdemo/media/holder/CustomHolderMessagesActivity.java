package com.example.sjh.gcsjdemo.media.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.entity.ChatMessage;
import com.example.sjh.gcsjdemo.helper.MessageTranslateBack;
import com.example.sjh.gcsjdemo.helper.MessageTranslateTo;
import com.example.sjh.gcsjdemo.media.DemoMessagesActivity;
import com.example.sjh.gcsjdemo.media.data.fixtures.MessagesFixtures;
import com.example.sjh.gcsjdemo.media.data.model.User;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomIncomingImageMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomIncomingTextMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomOutcomingImageMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomOutcomingTextMessageViewHolder;
import com.example.sjh.gcsjdemo.utils.AppUtils;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnection;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnectionOnLine;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import com.example.sjh.gcsjdemo.media.data.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;

import java.util.ArrayList;
import java.util.List;


public class CustomHolderMessagesActivity extends DemoMessagesActivity
        implements MessagesListAdapter.OnMessageLongClickListener<Message>,
        MessageInput.InputListener,
        MessageInput.AttachmentsListener, ChatManagerListener,
        ChatMessageListener {

    String team_member[]= new String[20];
    String team_member_ex[]= new String[20];
    int f_number;
    private MyXMPPTCPConnectionOnLine connection;//连接
    private ChatManager chatManager;//会话管理
    private Chat chat[]=new Chat[20];//会话

    static ArrayList<String> avatars = new ArrayList<String>() {
        {
            add("http://d.lanrentuku.com/down/png/1904/international_food/fried_rice.png");
        }
    };
    //接受处理消息
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0:
                    MessageTranslateBack helper=new MessageTranslateBack((String) msg.obj);
                    User user = new User(helper.getMsgFromId(),helper.getMsgFrom(),avatars.get(0),true);
                    //ChatMessage chatMessage = new ChatMessage((String) msg.obj, 1);
                    Message message = new Message(helper.getMsgFrom(),user,helper.getMsgContent(),helper.getMsgDate());
                    //messageList.add(chatMessage);
                    if((helper.getMsgFromId()).equals(team_member[19]))
                    {
                        //啥也不干
                    }else{
                        messagesAdapter.addToStart(message,true);//加入下方列表
                        Log.i("1发送11111111111111111","1");
                    }
                    break;
                default:
                    break;
            }
        }
    };



    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomHolderMessagesActivity.class));
    }

    //定义消息列表
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册
        setContentView(R.layout.activity_custom_holder_messages);
        //消息列表布局
        messagesList = (MessagesList) findViewById(R.id.messagesList);
        //初始化适配器
        initAdapter();
        initChatManager();
        initChat();


        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    //点击发送的时间，显示输入的文字
    @Override
    public boolean onSubmit(CharSequence input) {
        //messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
        sendChatMessage(input.toString());
        return true;
    }

    //传递用户成员
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String data[]) {
        //接收用户姓名
        team_member=data;
        //Log.i("（）（）（）（）（）（）",data);
    }

    //点击加号的事件，刷新出一个图片
    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    //消息长按事件
    @Override
    public void onMessageLongClick(Message message) {
        AppUtils.showToast(this, R.string.on_log_click_message, false);
    }

    private void initChatManager(){
        connection = MyXMPPTCPConnectionOnLine.getInstance();
        if(connection != null){
            chatManager = ChatManager.getInstanceFor(connection);
            chatManager.addChatListener(this);
        }
    }

    private void initChat(){
        if(chatManager != null){
            //第一个参数是 用户的ID
            //第二个参数是 ChatMessageListener，我们这里传null就好了
            //群组共计x个成员，建立会话
            for (int i=0;i<f_number;i++)
            {
                chat[i]=chatManager.createChat(team_member_ex[i], null);
            }

        }
    }

    private void initAdapter() {
        //加后缀
        for(int i=0;i<20;i++){
            if(team_member[i]==null) {f_number=i;break;}
            team_member_ex[i]=team_member[i]+"@gcsj-app";
            //Log.i("_)_)_)_)_)_",i+":"+team_member[i]);
        }

        //We can pass any data to ViewHolder with payload
        //我们可以传递任何数据到ViewHolder用payload
        //定义一个 来消息 文本 的payload
        CustomIncomingTextMessageViewHolder.Payload payload = new CustomIncomingTextMessageViewHolder.Payload();
        //For example click listener
        //点击事件监听器
        payload.avatarClickListener = new CustomIncomingTextMessageViewHolder.OnAvatarClickListener() {
            @Override
            public void onAvatarClick() {
                Toast.makeText(CustomHolderMessagesActivity.this,
                        "Text message avatar clicked", Toast.LENGTH_SHORT).show();
            }
        };

        //消息holder的配置和创建
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomIncomingTextMessageViewHolder.class,
                        R.layout.item_custom_incoming_text_message,
                        payload)
                .setOutcomingTextConfig(
                        CustomOutcomingTextMessageViewHolder.class,
                        R.layout.item_custom_outcoming_text_message)
                .setIncomingImageConfig(
                        CustomIncomingImageMessageViewHolder.class,
                        R.layout.item_custom_incoming_image_message)
                .setOutcomingImageConfig(
                        CustomOutcomingImageMessageViewHolder.class,
                        R.layout.item_custom_outcoming_image_message);

        //配置适配器内容，第一个参数为发送者的id，id不同则在右侧
        super.messagesAdapter = new MessagesListAdapter<>(team_member[19], holdersConfig, super.imageLoader);
        //配置点击事件
        super.messagesAdapter.setOnMessageLongClickListener(this);
        super.messagesAdapter.setLoadMoreListener(this);
        //把配置好的适配器给List
        messagesList.setAdapter(super.messagesAdapter);
    }

    //给群组中的每个人都发消息

    private void sendChatMessage(String msgContent){
        MessageTranslateTo helper=new MessageTranslateTo(team_member[17],team_member[19],team_member[18],msgContent);
        User user = new User(helper.getMsgFromId(),helper.getMsgFrom(),avatars.get(0),true);
        MessageTranslateBack helper1=new MessageTranslateBack(helper.getMsgJson());
        Log.i("2发送222222222222222",helper.getMsgJson());
        Message message = new Message(helper.getMsgFrom(),user,helper.getMsgContent(),helper1.getMsgDate());
        messagesAdapter.addToStart(message,true);//加入下方列表
        Log.i("2发送222222222222222","222");

        for(int i=0;i<f_number;i++)
        {
            if(chat[i]!= null){
                try {
                    //发送消息，参数为发送的消息内容
                    chat[i].sendMessage(helper.getMsgJson());
                    Log.i("0发送",helper.getMsgJson());
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener(this);
    }

    @Override
    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
        if(message.getType().equals(org.jivesoftware.smack.packet.Message.Type.chat) || message.getType().equals(org.jivesoftware.smack.packet.Message.Type.normal)){
            if(message.getBody() != null){
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 0;
                msg.obj = message.getBody();
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
