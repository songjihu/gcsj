package com.example.sjh.gcsjdemo.ui.fragment.first.child;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;//用来读取本地时间，进一步来更新今日课程
import java.util.Calendar;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.FirstHomeAdapter;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class FirstHomeFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private FirstHomeAdapter mAdapter;//此项为展示待上课程item的适配器


    private boolean mInAtTop = true;
    private int mScrollTotal;
    private int signIn[]=new int[5];
    private String uTitles[] = new String[10];//id+name
    //private String subjectId[]=new String[10];//所有老师要教的课程id
    private List<String> subjectId = new ArrayList<String>();
    //private List<String> signInCode=new ArrayList<String>();
    private String signCode[]=new String[5];
    private selectTask selectTask= new selectTask();
    private List<String> sign_in_flag= new ArrayList<String>();
    public String qq;



    //5个item的标题
    private String[] mTitles = new String[5];
    private String[] nTitles=new String[5];
    //5个item的图片
    private String[] mCheck = new String[5];
    private int[] mImgRes = new int[5];



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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(String data) {
        //接收用户姓名+得到用户所在班级进一步得到课程ID
        // StuInfo a=new StuInfo();
        if(data.contains(":")){
            uTitles=data.split(":");
            Log.i("**********0",uTitles[0]);
            Log.i("**********1",uTitles[1]);
        }
    }


    public void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);//循环显示的多个item
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);//下拉循环布局
        TextView welcomeView = (TextView) view.findViewById(R.id.welcomemsg);
        //在最上面打印欢迎XXX
        welcomeView.setText("欢迎"+uTitles[1]+"老师");//设置欢迎信息姓名
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新的颜色
        mRefreshLayout.setOnRefreshListener(this);//设置下拉刷新的对象
        mAdapter = new FirstHomeAdapter(_mActivity);//定义item的适配器
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);//设置为流布局并定义manger
        mRecy.setLayoutManager(manager);//循环显示的多个item的布局管理
        mRecy.setAdapter(mAdapter);//循环显示的多个item的适配器设置
        //点击item的事件监听，开启新的fragment

        selectTask.execute();


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                if(position==0&&signIn[0]==1&&mCheck[0].equals("签到状态：已开启\n")){
                //初始化要加载的fragment
               FirstDetailFragment fragment = FirstDetailFragment.newInstance(mAdapter.getItem(position));
                    start(fragment);
                }
            }
        });
        //点击签到按钮的事件监听，开启新的fragment

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

    //执行更新函数
    @Override
    public void onRefresh() {
        //查询更新
        new selectTask().execute();
    }

    public void condi(){
        for(int i=0;i<=4;i++){
            nTitles[i]="kong";
        }
    }

    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
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

    //从数据库更新状态
    private class selectTask extends AsyncTask<List<String>, Object, Short> {

        //此次连接登录服务器为离线状态
        @SafeVarargs
        @Override
        protected final Short doInBackground(List<String>... params) {
            condi();
            /*读取本地时间，然后读取单天对应的课表，放入相应的结构中。*/
            Calendar ca = Calendar.getInstance();
            /* final int hour=ca.get(Calendar.HOUR);//小时 */
            final String hour1=getHour();
            final int hour=Integer.valueOf(hour1);
            short c_num=0;
            int WeekOfYear = ca.get(Calendar.DAY_OF_WEEK);
            final String WeekOf=String.valueOf(WeekOfYear);
            Log.i("****",WeekOf);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection cn= DriverManager.getConnection("jdbc:mysql://182.254.161.189/gcsj?useUnicode=true&characterEncoding=utf-8","root","mypwd");
                String sql = "SELECT * FROM `t_course` WHERE teacher_id = " + uTitles[0];
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    //sign_in_flag.add(rs.getString(3));
                    subjectId.add(rs.getString(1));
                }
                Log.i("-----结束查询-----","000");

                String result[]=new String[5];
                sql="SELECT * FROM `schedule_info` WHERE day = "+WeekOf;//获取所有当天的课程信息
                Log.i("今天星期",WeekOf);
                st=cn.createStatement();
                rs=st.executeQuery(sql);
                // ResultSet re;
                int p;
                while(rs.next()){
                    for(int j=3;j<=7;j++) {
                        if(rs.getString(j)==null)
                            result[j-3]="kong";
                        else
                        {
                            Log.i("长度为",""+subjectId.size());
                            for (int m=0;m<subjectId.size();m++){
                                Log.i("9999",subjectId.get(m));
                                if(rs.getString(j).equals(subjectId.get(m))){
                                    //如果是这个老师的课则加入
                                    result[j-3]=rs.getString(j);//不为空则读取课程id号
                                }else {
                                    //不是这个老师的课也置为空
                                    result[j-3]="kong";
                                }
                            }

                        }

                    }
                    if(hour>20) p=5;
                    else{
                        if(hour>18) p=4;
                        else {
                            if(hour>16)p=3;
                            else{
                                if(hour>12) p=2;
                                else{
                                    if(hour>10) p=1;
                                    else p=0;
                                }
                            }
                        }
                    }
                    int i=0;
                    for(int h=p;h<=4;h++){
                        //遍历结果，不为空的
                        if(!result[h].equals("kong")){
                            //if(rs.getString(j)!=null) {
                            sql="SElECT * FROM `schedule_con` where sc_id= "+result[h]+" and day ="+WeekOf;
                            ResultSet re=st.executeQuery(sql);
                            if(re.next()) {
                                signIn[i] = re.getInt("sign_in");
                                signCode[i]=re.getString("sign_in_code");
                            }
                            nTitles[i] = "第"+(h+1)+"节\n"  +re.getString("course_name")+"\n"+re.getString("address")+"\n"+re.getString("teacher");
                            Log.i("打印",nTitles[i]);
                            i=i+1;
                        }
                    }
                    c_num=(short)i;
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
            Toast.makeText(getActivity(), "查询完成", Toast.LENGTH_SHORT).show();
            List<Article> articleList = new ArrayList<>();
            for (int i = 0; i <state; i++) {
                //if(signIn[index]==0) fuzhi(mCheck[index],"签到状态：未开启\n");
                //  else fuzhi(mCheck[index],"签到状态：已开启\n");
                if(nTitles[i].equals("kong")){
                    nTitles[i]="";
                    mCheck[i]="";

                }
                else{
                    if(signIn[i]==0) mCheck[i]="签到状态：未开启\n";
                    else mCheck[i]="签到状态：已开启\n";

                    if(mCheck[i].equals("签到状态：已开启\n"))
                        mImgRes[i]=R.drawable.linglingling;
                }
                Article article = new Article(nTitles[i], mCheck[i], mImgRes[i]);
                articleList.add(article);
            }
            if(state==0){
                Article article = new Article("今日无课","", R.drawable.linglinglingu);
                articleList.add(article);
            }
            //设置数据到适配器
            mAdapter.setDatas(articleList);
            mRecy.setAdapter(mAdapter);
            mRecy.scrollToPosition(0);
            mRefreshLayout.setRefreshing(false);


        }
    }
}