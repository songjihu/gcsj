package com.example.sjh.gcsjdemo.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;



/**
 * 用来显示个人已经加入的群聊，并且点击进去进入聊天模式
 */
public class MyGroup extends AppCompatActivity {

    ArrayList<String> tgrpno= new ArrayList<>();
    ArrayList<String> tgrpname = new ArrayList<>();
    ArrayList<String> ctype=new ArrayList<>();
    private String tgroupno ;
    private String tgroupname ;


    String mUserid ="20162430701";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);

        final ListView listView = findViewById(R.id.grp_list);
        //从上个界面接收学号
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        mUserid= bundle.getString("userid");
        // System.out.println("获取到的mUserid为"+mUserid);

        Button btjoin =(Button)findViewById(R.id.btjoin);
        Button btcreate=(Button)findViewById(R.id.btcreate);
        ImageView imageView = (ImageView)findViewById(R.id.empty_list);

        mygrp();


        if(tgrpname.size()==0){
            imageView.setVisibility(View.VISIBLE);
        }

//        if(ctype.size()==0){
//        imageView.setVisibility(View.VISIBLE);
//            System.out.println("群组i名称为"+tgrpname.get(0));
//         }
//        else {
//        imageView.setVisibility(View.INVISIBLE);
//            for(int i=0;i<tgrpname.size();i++)
//                System.out.println("群组名称为"+tgrpname.get(i));
//        }
//
//
//        int i=ctype.size();
//    String[] ctype1= new String[i];
//    for(i=0;i<ctype.size();i++)
//        ctype1[i]=ctype.get(i);
//ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.ctype,android.R.layout.simple_list_item_checked);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tgrpname);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String result = parent.getItemAtPosition(pos).toString();
                Toast.makeText(MyGroup.this,result,Toast.LENGTH_SHORT).show();
                //这个地方需改成点击事件发生的动作
            }
        });

        //Button创建群组跳转页面
        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGroup.this, CreateGroup.class);//将该页面的学号传递至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  mUserid.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        //button加入群组跳转页面
        btjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGroup.this, JoinGroup.class);//将该页面的学号传递至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  mUserid.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }



    private void mygrp(){

        final GroupInfo grp = new GroupInfo() ;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT * FROM user_team WHERE user_id = "+ mUserid;
                    Statement stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet rset = stmt.executeQuery("select * from user_team");
                    ResultSetMetaData rsmd = rset.getMetaData() ;
                    int columnCount = rsmd.getColumnCount();

                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
//将学号所对应的所有组号读取出来
                    rset.close();
                    int temp=0;

                    while(rs.next()){
                        int i=1;
                        while(i<columnCount)
                        {
                            try{
                                temp=i+1;
                                if(rs.getString(temp)==null){}
                                else{
                                    tgrpno.add(rs.getString(temp));
                                }

                                i++;
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                //System.out.println("从数据库读取组号信息结束");
                                break;
                            }

                        }
                        //

                    }
                    rs.close();
                    //将组号所对应的组名存储到tgroupname中

                    String sql1;
                    Statement st1=(Statement)cn.createStatement();

                    for(int i=0;i<tgrpno.size();i++)
                    {
                        sql1="SELECT * FROM team_info WHERE team_id = "+tgrpno.get(i);

                        ResultSet rs1=st1.executeQuery(sql1);
                        if(rs1.next()){
                            tgrpname.add(rs1.getString("team_name"));
                        }

                        rs1.close();
                    }
                    st1.close();

                    st.close();

                    cn.close();


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();


        //LoginThread thread = new LoginThread(email,password);
        //thread.start();
        //uuu=thread.getUu();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //return password.length() > 4;
     /*  if(uuu.getRpwd().equals(password)){
            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return false;
        }
*/
        // Toast.makeText(getApplicationContext(),answer,Toast.LENGTH_LONG).show();

    }

}
