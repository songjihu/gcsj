package com.example.sjh.gcsjdemo.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.entity.Reminder;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改于 19/4/14.
 * 修改为需要提醒的信息
 */
public class SecondHomeAdapter extends RecyclerView.Adapter<SecondHomeAdapter.VH> {
    private List<Reminder> mRemindItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;


    public SecondHomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //R.layout.item_bxz_home_first  为item的布局形式
        View view = mInflater.inflate(R.layout.item_bxz_home_second, parent, false);

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
    public void onBindViewHolder(VH holder, int position) {
        Reminder item = mRemindItems.get(position);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.remind_msg, String.valueOf(position) + "1");

        holder.remind_msg.setText(item.getMsg());


    }

    public void setDatas(List<Reminder> items) {
        mRemindItems.clear();
        mRemindItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return mRemindItems.size();
    }

    public Reminder getItem(int position) {
        return mRemindItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView remind_msg;//获取提醒信息

        public VH(View itemView) {
            super(itemView);
            remind_msg = (TextView) itemView.findViewById(R.id.remind_msg);

        }
    }
}
