package com.example.sjh.gcsjdemo.ui.fragment.first.child;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.activity.CheckinActivity;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.FirstCheckAdapter;
import com.example.sjh.gcsjdemo.adapter.FirstHomeAdapter;
import com.example.sjh.gcsjdemo.base.BaseBackFragment;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/5.
 * 修改为详细的签到界面
 * 在这一层实现是否需要签到
 */
public class FirstDetailFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_ITEM = "arg_item";

    private String mArticle;//签到的课程id
    private static Boolean mCheckOn;//是否开启签到

    private Button button01;
    private Button button02;
    private Button button03;
    //private FloatingActionButton mFab;

    private RecyclerView mRecy;//循环显示view
    private SwipeRefreshLayout mRefreshLayout;//循环显示view的刷新
    private FirstCheckAdapter mAdapter;//此项为展示未签到item的适配器
    private selectTask selectTask= new selectTask();//更新item内容函数
    private boolean mInAtTop = true;
    private int mScrollTotal;
    private static String t_code;//教师签到码
    private static String c_id;//要签到的班级id


    public static FirstDetailFragment newInstance(String article,Boolean checkOn) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, article);
        args.putBoolean("checkOn", checkOn);
        FirstDetailFragment fragment = new FirstDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticle = getArguments().getString(ARG_ITEM);
        mCheckOn = getArguments().getBoolean("checkOn");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_first_detail, container, false);
        //EventBusActivityScope.getDefault(_mActivity).register(this);
        //EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {

        button01=view.findViewById(R.id.button_first);//发起签到
        button02=view.findViewById(R.id.button_second);//查看签到
        button03=view.findViewById(R.id.button_third);//关闭签到

        button01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首先检测是否开启签到
                if(!mCheckOn)
                {
                    Intent intent = new Intent(getActivity(), CheckinActivity.class);
                    Bundle  bundle =new Bundle();
                    bundle.putString("signinClassid",mArticle);//放入签到课程号
                    intent.putExtras(bundle);
                    Log.i("-=-=-=-=-=-=-=-=-=",mArticle);
                    //intent.putExtra("signinClassid",mArticle);
                    startActivity(intent);
                    mCheckOn=true;
                }
                else {
                    Toast.makeText(getContext(), getString(R.string.pcheckinerror),Toast.LENGTH_SHORT).show();
                }

            }
        });
        button02.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mCheckOn)
                {
                    new selectTask().execute();//更新
                }
                else {
                    Toast.makeText(getContext(), "请先开启签到",Toast.LENGTH_SHORT).show();
                }

            }
        });

        button03.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mCheckOn)
                {
                    new shutTask().execute();//更新
                    Toast.makeText(getContext(), "关闭成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "请先开启签到",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mRecy = (RecyclerView) view.findViewById(R.id.recy_check);//循环显示的多个item
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_check);//下拉循环布局
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新的颜色
        mRefreshLayout.setOnRefreshListener(this);//设置下拉刷新的对象
        mAdapter = new FirstCheckAdapter(_mActivity);//定义item的适配器
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);//设置为流布局并定义manger
        mRecy.setLayoutManager(manager);//循环显示的多个item的布局管理
        mRecy.setAdapter(mAdapter);//循环显示的多个item的适配器设置
        //点击item的事件监听，开启新的fragment



        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }

            }
        });
        //onRefresh();


    }

    @Override
    public void onRefresh() {
        //查询更新
        new selectTask().execute();
    }

    //从数据库更新状态
    private class selectTask extends AsyncTask<List<String>, Object, Short> {
        List<Article> articleList = new ArrayList<>();
        short c_num=0;//学生数量
        @SafeVarargs
        @Override
        protected final Short doInBackground(List<String>... params) {
            //更新签到状态
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj?useUnicode=true&characterEncoding=utf-8","root","mypwd");
                String sql = "SELECT * FROM `schedule_con` WHERE sc_id = " + mArticle;//根据课程号选择签到码和班级号
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    //sign_in_flag.add(rs.getString(3));
                    t_code=rs.getString("sign_in_code");//123456
                    c_id=rs.getString("sch_id");//2016301
                }

                sql="SELECT * FROM `class_info`  WHERE class_sch_id = "+c_id;//重新获取班级号
                st=cn.createStatement();
                rs=st.executeQuery(sql);

                while(rs.next()){
                    c_id=rs.getString("class_id");//201624301
                }

                sql="SELECT * FROM `stu_info`  WHERE class_id = "+c_id;//获取签到状态
                st=cn.createStatement();
                rs=st.executeQuery(sql);

                while(rs.next()){
                    //如果已经签到，则继续下一个
                    if(rs.getString("sign_in_code").equals(t_code))
                    {
                        continue;
                    }
                    //Log.i("1111",rs.getString(1));
                    //Log.i("2222",rs.getString(2));
                    c_num++;
                    Article article = new Article(rs.getString(1),c_num+":"+rs.getString(2));
                    articleList.add(article);
                }
                cn.close();
                st.close();
                rs.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Log.i("数量为",""+c_num);
            return c_num;
        }

        @Override
        protected void onPostExecute(Short state) {
            //设置数据到适配器
            mAdapter.setDatas(articleList);
            mRecy.setAdapter(mAdapter);
            mRecy.scrollToPosition(0);
            mRefreshLayout.setRefreshing(false);


        }
    }

    //从数据库更新状态到关闭签到
    private class shutTask extends AsyncTask<List<String>, Object, Short> {
        @SafeVarargs
        @Override
        protected final Short doInBackground(List<String>... params) {
            //关闭签到
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj?useUnicode=true&characterEncoding=utf-8","root","mypwd");
                String sql="update schedule_con set sign_in_code = 11 , sign_in = 0 where sc_id = ?";
                PreparedStatement st=cn.prepareStatement(sql);
                Log.i("--------sql-------",sql);
                st.setString(1,mArticle);
                st.executeUpdate();
                cn.close();
                st.close();
                mCheckOn=false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }
}
