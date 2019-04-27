package MyThread;

import android.util.Log;

import com.example.sjh.gcsjdemo.entity.Friend;
import com.example.sjh.gcsjdemo.utils.MyXMPPTCPConnection;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

public class FriendsThread implements Runnable{

    List<Friend> friends = new ArrayList<Friend>();

    @Override
    public void run() {
        getMyFriends();
        System.out.println("MyRunnable running");
    }
    public List<Friend> getMyFriends(){
        MyXMPPTCPConnection connection = MyXMPPTCPConnection.getInstance();
        //通过Roster.getInstanceFor(connection)获取Roast对象;
        //通过Roster对象的getEntries()获取Set，遍历该Set就可以获取好友的信息了;
        //循环确保有好友
        for(int t=1;friends.isEmpty()&&t<100;t++)
        {
            for(RosterEntry entry : Roster.getInstanceFor(connection).getEntries()){
                Friend friend = new Friend(entry.getUser(), entry.getName());
                friends.add(friend);
                //好友计数器
                Log.i("（）（）（）（）（）（）",friend.getJid());
                Log.i("（）（）（）（）（）（）",friend.getName());

            }
        }
        return friends;
    }
}
