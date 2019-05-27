package com.example.sjh.gcsjdemo.ui.fragment.fourth.child;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.AddCourseTable;
import com.example.sjh.gcsjdemo.activity.CheckinActivity;
import com.example.sjh.gcsjdemo.activity.MainActivity;
import com.example.sjh.gcsjdemo.PersonActivity;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.SecondHomeAdapter;
import com.example.sjh.gcsjdemo.entity.Reminder;
import com.example.sjh.gcsjdemo.event.TabSelectedEvent;
import com.example.sjh.gcsjdemo.person.DBUtils;

import com.example.sjh.gcsjdemo.person.dCourse;
import com.example.sjh.gcsjdemo.person.dCourseInfo;
import com.example.sjh.gcsjdemo.test;
import com.example.sjh.gcsjdemo.group.MyGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    //刷新课表定义变量
    ArrayList<dCourse> cou_list =new ArrayList<>();
    ArrayList<dCourseInfo> cou_list_info=new ArrayList<>();
    Connection conn=null;
    Statement stmt = null;
    Statement stmt2 = null;
    //String mon_one=null;
   // String mon_two;
   // String mon_three;
   // String mon_four;
   // String mon_five;
    //刷新课表定义变量


//获取个人信息
    String stuNo;
    String stuName;
    String stuTel;
    String stuSex;
    String stuCla;
    String stuDep;
    String stuSch;

    String No="20172432101";
    //获取个人信息
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
        if(data.contains(":")){
            uTitles=data.split(":")[1];
        }
        if(data.contains("243")){
            uTitles=data;
        }
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
                new Thread(new Runnable(){
                    public void run(){

                        conn = DBUtils.getConn();

                        String sql ="select stu_no,stu_name,gender,class_name,depart_name,school_name from stu_info stu,class_info cla,department_info dep,school_info sch where stu.class_id=cla.class_id and stu.depart_id=dep.depart_id and stu.school_id=sch.school_id and stu_no="+No;


                        try {
                            stmt = conn.createStatement();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            ResultSet rs = stmt.executeQuery(sql);
                            if (rs.next()){
                                stuNo= rs.getString("stu_no");
                                stuName= rs.getString("stu_name");
                                //stuTel= rs.getString("user_id");
                                 //stuSex= rs.getString("gender");
                                stuSex=rs.getString("gender");
                                 if(stuSex=="1"){
                                     stuSex="男";
                                 }else {
                                     stuSex="女";
                                 }
                                stuCla= rs.getString("class_name");
                                 stuDep= rs.getString("depart_name");
                                stuSch= rs.getString("school_name");

                            }
                            System.out.println(stuNo+"+"+stuName+"+"+stuCla+"+"+stuDep+"+"+stuSch+"+"+stuSex);
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra("stuNo",stuNo);
                intent.putExtra("stuName",stuName);
                intent.putExtra("stuSex",stuSex);
                intent.putExtra("stuCla",stuCla);
                intent.putExtra("stuDep",stuDep);
                intent.putExtra("stuSch",stuSch);
                startActivity(intent);
            }
        });

        mAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable(){
                    public void run(){

                        conn = DBUtils.getConn();

                        String sql ="select one,two,three,four,five from schedule_info where sch_id='2016301' ";
                        String sql2="select * from schedule_con";

                        try {
                            stmt = conn.createStatement();
                            stmt2=conn.createStatement();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {

                            ResultSet rs = stmt.executeQuery(sql);
                            ResultSet rs2=stmt2.executeQuery(sql2);

                            while(rs.next()){
                                     dCourse dcourse=new dCourse();
                                     dcourse.setD_one(rs.getString("one"));
                                     dcourse.setD_two(rs.getString("two"));
                                     dcourse.setD_three(rs.getString("three"));
                                     dcourse.setD_four(rs.getString("four"));
                                     dcourse.setD_five(rs.getString("five"));

                                     cou_list.add(dcourse);
/*
                                mon_one =rs.getString("one");
                                mon_two =rs.getString("two");
                                mon_three =rs.getString("three");
                                mon_four =rs.getString("four");
                                mon_five =rs.getString("five");
*/
                            }
                          //  System.out.println(mon_one+"+++++++"+mon_two+"+++++++"+mon_three+"+++++++"+mon_four+"+++++++"+mon_five);
                              while (rs2.next()){
                                  dCourseInfo dcourseinfo =new dCourseInfo();
                                  dcourseinfo.setcName(rs2.getString("course_name"));
                                  dcourseinfo.setcTeacherName(rs2.getString("teacher"));
                                  dcourseinfo.setcAddress(rs2.getString("address"));
                                  dcourseinfo.setScId(rs2.getString("sc_id"));
                                  dcourseinfo.setDay(rs2.getString("day"));

                                  cou_list_info.add(dcourseinfo);




                              }







                            rs.close();
                            rs2.close();



                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            stmt.close();
                            stmt2.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                try {

                    Thread.sleep(1500);//延时确保刷新到课表信息

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent =new Intent();
                intent.setClass(getActivity(), AddCourseTable.class);
                //dCourse dcourse=new dCourse(mon_one,mon_two,mon_three,mon_four,mon_five);
                intent.putExtra("cou_list",cou_list);
                intent.putExtra("cou_list_info",cou_list_info);
                System.out.println("------------------------------>"+cou_list.size());
                System.out.println("------------------------------>"+cou_list_info.size());
               // Bundle Mon=new Bundle();
               // Mon.putString("mon_one",mon_one);
               // Mon.putString("mon_two",mon_two);
                //Mon.putString("mon_three",mon_three);
               // Mon.putString("mon_four",mon_four);
               // Mon.putString("mon_five",mon_five);
                // intent.putExtras(Mon);


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
