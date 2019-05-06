package com.example.sjh.gcsjdemo.media.data.fixtures;



import com.example.sjh.gcsjdemo.media.data.model.User;
import com.example.sjh.gcsjdemo.media.data.model.Dialog;
import com.example.sjh.gcsjdemo.media.data.model.Message;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * Created by Anton Bevza on 07.09.16.
 */
public final class DialogsFixtures extends FixturesData {
    private DialogsFixtures() {
        throw new AssertionError();
    }

    //聊天记录获取
    public static ArrayList<Dialog> getDialogs() {
        ArrayList<Dialog> chats = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            //Calendar为时间函数
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i));
            calendar.add(Calendar.MINUTE, -(i * i));

            chats.add(getDialog(i, calendar.getTime()));
        }

        return chats;
    }

    //真实聊天列表获取(返回list)
    public static ArrayList<Dialog> getDialogsChat(String team_name[],String team_member[][],int team_number) {
        ArrayList<Dialog> chats = new ArrayList<>();

        for (int i = 0; i < team_number; i++) {
            //Calendar为时间函数
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i));
            calendar.add(Calendar.MINUTE, -(i * i));
            //用下个函数返回单个并加入list
            chats.add(getDialogChat(team_name,team_member,i, calendar.getTime()));
        }

        return chats;
    }

    //同上(返回单个dialog)
    private static Dialog getDialogChat(String team_name[],String team_member[][],int i, Date lastMessageCreatedAt) {
        ArrayList<User> users = getUsers();
        return new Dialog(
                Integer.toString(i),//群聊id
                team_name[i],//群聊名称
                //"",//群聊图片，为空加载默认图片
                users,//群聊用户组成
                getMessage(lastMessageCreatedAt),//群聊的最后一条消息
                i < 3 ? 3 - i : 0);//未读的消息数
    }


    private static Dialog getDialog(int i, Date lastMessageCreatedAt) {
        ArrayList<User> users = getUsers();
        return new Dialog(
                getRandomId(),
                users.size() > 1 ? groupChatTitles.get(users.size() - 2) : users.get(0).getName(),
                users.size() > 1 ? groupChatImages.get(users.size() - 2) : getRandomAvatar(),
                users,
                getMessage(lastMessageCreatedAt),
                i < 3 ? 3 - i : 0);
    }

    private static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        int usersCount = 1 + rnd.nextInt(4);

        for (int i = 0; i < 3; i++) {
            users.add(getUser());
        }

        return users;
    }

    private static User getUser() {
        return new User(
                getRandomId(),
                getRandomName(),
                getRandomAvatar(),
                getRandomBoolean());
    }

    private static Message getMessage(final Date date) {
        return new Message(
                getRandomId(),
                getUser(),
                getRandomMessage(),
                date);
    }
}
