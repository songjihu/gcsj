package com.example.sjh.gcsjdemo.media.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.media.DemoMessagesActivity;
import com.example.sjh.gcsjdemo.media.data.fixtures.MessagesFixtures;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomIncomingImageMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomIncomingTextMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomOutcomingImageMessageViewHolder;
import com.example.sjh.gcsjdemo.media.holder.holders.messages.CustomOutcomingTextMessageViewHolder;
import com.example.sjh.gcsjdemo.utils.AppUtils;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import com.example.sjh.gcsjdemo.media.data.model.Message;


public class CustomHolderMessagesActivity extends DemoMessagesActivity
        implements MessagesListAdapter.OnMessageLongClickListener<Message>,
        MessageInput.InputListener,
        MessageInput.AttachmentsListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomHolderMessagesActivity.class));
    }

    //定义消息列表
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_messages);
        //消息列表布局
        messagesList = (MessagesList) findViewById(R.id.messagesList);
        //初始化适配器
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
    }

    //点击发送的时间，显示输入的文字
    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }


    //点击加号的时间，刷新出一个图片
    @Override
    public void onAddAttachments() {
        messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
    }

    //消息长按事件
    @Override
    public void onMessageLongClick(Message message) {
        AppUtils.showToast(this, R.string.on_log_click_message, false);
    }

    private void initAdapter() {

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

        //配置适配器内容
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, holdersConfig, super.imageLoader);
        //配置点击事件
        super.messagesAdapter.setOnMessageLongClickListener(this);
        super.messagesAdapter.setLoadMoreListener(this);
        //把配置好的适配器给List
        messagesList.setAdapter(super.messagesAdapter);
    }
}
