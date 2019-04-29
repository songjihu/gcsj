package com.example.sjh.gcsjdemo.media.data.model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

/*
 * Created by troy379 on 04.04.17.
 * 消息
 */

public class Message implements IMessage,
        MessageContentType.Image,
        /*this is for default image messages implementation*/
        //这个是给默认的图片implementation
        MessageContentType {
    /*and this one is for custom content type (in this case - voice message)*/
    //而且这一个是为了传统的容器类型（为了防止出现声音消息）
    private String id;//消息id
    private String text;//消息的文本内容
    private Date createdAt;//消息的时间
    private User user;//消息的来源用户
    private Image image;//消息的图片
    private Voice voice;//消息的声音

    //不带时间的文本消息
    public Message(String id, User user, String text) {
        this(id, user, text, new Date());
    }

    //带时间的文本消息
    public Message(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public static class Voice {

        //声音消息
        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }
}
