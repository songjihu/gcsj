package com.example.sjh.gcsjdemo.ui.fragment.first.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.*;//用来读取本地时间，进一步来更新今日课程
import java.text.*;
import java.util.concurrent.CountDownLatch;

import Entity.StuInfo;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

import com.example.sjh.gcsjdemo.MessageEvent;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.MainActivity;
import com.example.sjh.gcsjdemo.adapter.FirstHomeAdapter;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.event.TabSelectedEvent;
import com.example.sjh.gcsjdemo.helper.DetailTransition;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;

import static com.example.sjh.gcsjdemo.R.id.welcomemsg;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class FirstHomeFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView welcomeView;
    private FirstHomeAdapter mAdapter;//此项为展示待上课程item的适配器


    private boolean mInAtTop = true;
    private int mScrollTotal;
    private String uTitles = new String();
    private String classId=new String();
    private String subjectId=new String();


    //5个item的标题
    private String[] mTitles = new String[]{
            "第一节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n",
            "第一节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n",
            "第一节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n",
            "第一节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n",
            "第一节\n" + "人工智能（ 北一101）\n" + "柴玉梅\n",
    };


    //5个item的图片
    private String[] mCheck = new String[]{
            "签到状态：未签到\n",
            "签到状态：未开启\n",
            "签到状态：未开启\n",
            "签到状态：未开启\n",
            "签到状态：未开启\n",
    };

    private int[] mImgRes = new int[]{
            R.drawable.linglingling, R.drawable.linglingling, R.drawable.linglingling, R.drawable.linglingling, R.drawable.linglingling
    };



    public static FirstHomeFragment newInstance() {

        Bundle args = new Bundle();

        FirstHomeFragment fragment = new FirstHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bxz_fragment_first_home, container, false);
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
        //接收用户姓名+得到用户所在班级进一步得到课程ID
        StuInfo a=new StuInfo();
        uTitles=data;
        classId=a.getclassId(data);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/kcsj","root","mypwd");
                    String sql="SELECT class_sch_id FROM `class_info` WHERE class_id = "+"classId";
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        subjectId=rs.getString("class_sch_id");
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
        });
        Log.i("**********",data);
    }



    public void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);//循环显示的多个item
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);//下拉循环布局
        welcomeView = (TextView) view.findViewById(R.id.welcomemsg);//欢迎信息：欢迎+同学/老师
        //在最上面打印欢迎XXX
        welcomeView.setText("欢迎"+uTitles);//设置欢迎信息姓名
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新的颜色
        mRefreshLayout.setOnRefreshListener(this);//设置下拉刷新的对象
        mAdapter = new FirstHomeAdapter(_mActivity);//定义item的适配器
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);//设置为流布局并定义manger
        mRecy.setLayoutManager(manager);//循环显示的多个item的布局管理
        mRecy.setAdapter(mAdapter);//循环显示的多个item的适配器设置
        //点击item的事件监听，开启新的fragment
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                //初始化要加载的fragment
                FirstDetailFragment fragment = FirstDetailFragment.newInstance(mAdapter.getItem(position));
                //3个参数为  点击位置 无用 item的内容
                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Fade());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailTransition());
                    fragment.setSharedElementEnterTransition(new DetailTransition());

                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    extraTransaction()
                            .addSharedElement(((FirstHomeAdapter.VH) vh).checkinmsg, getString(R.string.image_transition))
                            .addSharedElement(((FirstHomeAdapter.VH) vh).checkinstatus, "tv")
                            .start(fragment);
                } else {
                    start(fragment);
                }
            }
        });
        //点击签到按钮的事件监听，开启新的fragment


        // 在list中循环显示8个item
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int index = i % 5;
            if(mCheck[index].equals("签到状态：未开启\n"))
            {
                mImgRes[index]=R.drawable.linglinglingu;
            }
            Article article = new Article(mTitles[index], mCheck[index], mImgRes[index]);
            articleList.add(article);
        }
        //设置数据到适配器
        mAdapter.setDatas(articleList);

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


    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
        /*读取本地时间，然后读取单天对应的课表，放入相应的结构中。*/
        Calendar ca = Calendar.getInstance();
        int hour=ca.get(Calendar.HOUR);//小时
        int WeekOfYear = ca.get(Calendar.DAY_OF_WEEK);
        int j=0;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/kcsj","root","mypwd");
                    String sql="SELECT * FROM `schedule_info` WHERE day = "+"WeekofYear";
                    String sql1="SElECT course_name FROM `schedule_con where sc="+"rs.getString(`one`)";
                    String sql2="SElECT course_name FROM `schedule_con where sc="+"rs.getString(`two`)";
                    String sql3="SElECT course_name FROM `schedule_con where sc="+"rs.getString(`three`)";
                    String sql4="SElECT course_name FROM `schedule_con where sc="+"rs.getString(`four`)";
                    String sql5="SElECT course_name FROM `schedule_con where sc="+"rs.getString(`five`)";
                    Statement st=(Statement)cn.createStatement();
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next()){
                        //if
                       mTitles[j]="第j节\n"+"rs.getString(`one`)"+
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
        });
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.FIRST) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
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
