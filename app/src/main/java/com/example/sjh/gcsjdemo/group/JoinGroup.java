package com.example.sjh.gcsjdemo.group;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sjh.gcsjdemo.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class JoinGroup extends AppCompatActivity {

    private String mUserid;
    private String jresult_course_name=null;
    private String jresult_course_id=null;
    ArrayList<String> jcourse_name =new ArrayList<>();
    ArrayList<String> jcourse_id =new ArrayList<>();
    ArrayList<String> jgroup_no=new ArrayList<>();
    ArrayList<String> jgroup_name =new ArrayList<>();
    private  String result_grpno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        mUserid= bundle.getString("userid");


        //界面初始化共7个控件
        TextView textView1=findViewById(R.id.join_text1);
        TextView textView2=findViewById(R.id.join_text2);

        Button jbtcreat=findViewById(R.id.join_to_create);
        Button jbtsearch=findViewById(R.id.join_search_proceed);

        final ImageView imageView=findViewById(R.id.join_empty_list);

        final ListView listView=findViewById(R.id.result_list);
        final Spinner search_spinner=findViewById(R.id.course_search);



        final CountDownLatch countDownLatch = new CountDownLatch(1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql="SELECT * FROM course_info WHERE depart_id = "+"24";
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        jcourse_id.add(rs.getString("course_id"));
                        jcourse_name.add(rs.getString("course_name"));
                        // Log.i("CreateGroup","查询成果、、、、、");
                    }
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

        //LoginThread thread = new LoginThread(email,password);
        //thread.start();
        //uuu=thread.getUu();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //spinner监听器，最后结果存至result_min和result_max中
        ArrayAdapter<String> jcourse_name_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,jcourse_name);
        jcourse_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_spinner.setAdapter(jcourse_name_adapter);

        search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_course= parent.getItemAtPosition(position).toString();
                jresult_course_name=search_spinner.getSelectedItem().toString();
               // Log.i("JoinGroup",jresult_course_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(JoinGroup.this,"未选择课程！",Toast.LENGTH_SHORT).show();
            }

        });

        jbtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                jgroup_no.clear();
                jgroup_name.clear();
                result_grpno=null;
                search();
                if (jgroup_name.size() > 0) { }
                else {
                    imageView.setVisibility(View.VISIBLE);

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(JoinGroup.this, android.R.layout.simple_list_item_1, jgroup_name);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                        String result = parent.getItemAtPosition(pos).toString();
                        //Toast.makeText(JoinGroup.this, result, Toast.LENGTH_SHORT).show();
                        //这个地方需改成点击事件发生的动作
                        for(int i=0;i<jgroup_name.size();i++)
                        {
                            if(result.equals(jgroup_name.get(i))){
                                result_grpno=jgroup_no.get(i);
                            }
                        }
                        // Log.i("JoinGroup","获取到的课程id号为"+result_grpno);
                        Intent intent = new Intent(JoinGroup.this, ShowGroupInfo.class);//将该页面的学号传递至下个页面
                        Bundle  bundle =new Bundle();
                        bundle.putCharSequence("userid",mUserid );
                        bundle.putCharSequence("grpno",result_grpno);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        onPause();
                    }
                });
            }
        });
        jbtcreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinGroup.this, CreateGroup.class);//将该页面的学号传递至下个页面
                Bundle  bundle =new Bundle();
                bundle.putString("userid",  mUserid);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }


    private  void search(){

        final CountDownLatch countDownLatch = new CountDownLatch(1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0;
                    while(i<jcourse_name.size()){
                        if(jresult_course_name==jcourse_name.get(i)){
                            jresult_course_id=jcourse_id.get(i);
                            break;
                        }
                        i++;
                    }
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj","root","mypwd");
                    String sql1="SELECT * FROM team_info WHERE team_course = "+jresult_course_id;
                    Statement st1=(Statement)cn.createStatement();
                    ResultSet rs1=st1.executeQuery(sql1);
                    while(rs1.next()){
                        jgroup_no.add(rs1.getString("team_id"));
                        jgroup_name.add(rs1.getString("team_name"));
                        // Log.i("JoinGroup","检查点2.。。。。。。。。。。。");
                    }
                    cn.close();
                    st1.close();
                    rs1.close();
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
    }


}
