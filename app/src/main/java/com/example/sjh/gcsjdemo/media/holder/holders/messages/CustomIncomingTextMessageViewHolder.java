package com.example.sjh.gcsjdemo.media.holder.holders.messages;

import android.view.View;

import com.example.sjh.gcsjdemo.R;
import com.stfalcon.chatkit.messages.MessageHolders;

import com.example.sjh.gcsjdemo.media.data.model.Message;

public class CustomIncomingTextMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    //一个小点，表示是否在线
    private View onlineIndicator;


    //把itemView的传送数据的payload送给viewHolder
    public CustomIncomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        //在线状态为刚开始的设定
        onlineIndicator = itemView.findViewById(R.id.onlineIndicator);
    }

    @Override
    public void onBind(Message message) {
        //绑定消息
        super.onBind(message);
        //获取发送消息的人是否在线并切换颜色
        boolean isOnline = message.getUser().isOnline();
        if (isOnline) {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_online);
        } else {
            onlineIndicator.setBackgroundResource(R.drawable.shape_bubble_offline);
        }
        //We can set click listener on view from payload
        //为来自payload的视图添加监听器
        final Payload payload = (Payload) this.payload;
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payload != null && payload.avatarClickListener != null) {
                    payload.avatarClickListener.onAvatarClick();
                }
            }
        });
    }

    //嵌套下一个
    public static class Payload {
        public OnAvatarClickListener avatarClickListener;
    }

    //点击事件定义
    public interface OnAvatarClickListener {
        void onAvatarClick();
    }
}
