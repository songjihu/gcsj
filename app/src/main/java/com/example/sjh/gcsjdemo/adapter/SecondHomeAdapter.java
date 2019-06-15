package com.example.sjh.gcsjdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.dbmanager.RemindUtil;
import com.example.sjh.gcsjdemo.entity.Remind;
import com.example.sjh.gcsjdemo.entity.Reminder;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.ui.fragment.second.child.SecondHomeFragment;
import com.example.sjh.gcsjdemo.ui.fragment.second.recycleview.RecyclerItemView;

import java.util.ArrayList;
import java.util.List;

import static com.example.sjh.gcsjdemo.dbmanager.MyApplication.getContext;

/**
 * 修改于 19/4/14.
 * 修改为需要提醒的信息
 */
public class SecondHomeAdapter extends RecyclerView.Adapter<SecondHomeAdapter.VH> implements RecyclerItemView.onSlidingButtonListener{
    private List<Remind> mRemindItems = new ArrayList<>();    //remind链表
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mClickListener;
    private onSlidingViewClickListener onSvcl;

    private RecyclerItemView recyclers;



    public SecondHomeAdapter(Context context,List<Remind> remindItems) {
        this.context = context;
        this.mRemindItems =remindItems;
    }


    public SecondHomeAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //R.layout.item_recycler  为item的布局形式
        View view = mInflater.inflate(R.layout.item_recycler, parent, false);

        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
      //Reminder item = mRemindItems.get(position);


        holder.title.setText(mRemindItems.get(position).getTitle());
        holder.content.setText(mRemindItems.get(position).getCon());
        holder.remind_time.setText(mRemindItems.get(position).getRemindTime());
        holder.layout_left.getLayoutParams().width = getScreenWidth(context);

        holder.layout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(context,"做出操作，进入新的界面或弹框",Toast.LENGTH_SHORT).show();
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    //获得布局下标（点的哪一个）
                    int subscript = holder.getLayoutPosition();
                    onSvcl.onItemClick(view, subscript);
                }
            }
        });
       /* holder.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"其他："+position,Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"删除了："+position,Toast.LENGTH_SHORT).show();
                int subscript = holder.getLayoutPosition();
                onSvcl.onDeleteBtnCilck(view,subscript);
            }
        });

    }

    public void setDatas(List<Remind> items) {
        mRemindItems.clear();
        mRemindItems.addAll(items);
    }


    @Override
    public int getItemCount() {
        return mRemindItems.size();
    }


    public Remind getItem(int position) {
        return mRemindItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
      //  public TextView remind_msg;//获取提醒信息
        public TextView title;
        public TextView content;
        public TextView remind_time;
        public LinearLayout layout_left;
        public TextView other;
        public TextView delete;
        public VH(View itemView) {
            super(itemView);
          //  remind_msg = (TextView) itemView.findViewById(R.id.remind_msg);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            remind_time = (TextView) itemView.findViewById(R.id.remind_time);
            layout_left = (LinearLayout) itemView.findViewById(R.id.layout_left);
          //  other = (TextView) itemView.findViewById(R.id.other);
            delete = (TextView) itemView.findViewById(R.id.delete);
            ((RecyclerItemView)itemView).setSlidingButtonListener(SecondHomeAdapter.this);
        }
    }
    @Override
    public void onMenuIsOpen(View view) {
        recyclers = (RecyclerItemView) view;
    }

    @Override
    public void onDownOrMove(RecyclerItemView recycler) {
        if(menuIsOpen()){
            if(recyclers != recycler){
                closeMenu();
            }
        }
    }

    //删除数据
    public void removeData(int position){

        RemindUtil ru = new RemindUtil();
        ru.deleteRemind(mRemindItems.get(position).getRemindId());
        mRemindItems.remove(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);

    }

    //关闭菜单
    public void closeMenu() {
        recyclers.closeMenu();
        recyclers = null;

    }

    // 判断是否有菜单打开
    public Boolean menuIsOpen() {
        if(recyclers != null){
            return true;
        }
        return false;
    }

    //设置在滑动侦听器上
   public void setOnSlidListener(onSlidingViewClickListener listener) {
        onSvcl = listener;
    }

    // 在滑动视图上单击侦听器
    public interface onSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }

    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        //窗口管理器
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE );
        //显示度量
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels; //返回屏幕宽度像素
    }
}
