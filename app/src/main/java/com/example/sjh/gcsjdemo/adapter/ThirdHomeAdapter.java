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
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.model.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKeyword on 16/6/5.
 * 修改于 19/4/23.
 * 修改聊天信息
 */
public class ThirdHomeAdapter extends RecyclerView.Adapter<ThirdHomeAdapter.VH> {
    private List<Friend> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;


    public ThirdHomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //R.layout.item_bxz_home_first  为item的布局形式
        View view = mInflater.inflate(R.layout.act_friends, parent, false);

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
        Friend item = mItems.get(position);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.name, String.valueOf(position) + "1");
        ViewCompat.setTransitionName(holder.last_word, String.valueOf(position) + "2");


        holder.name.setText(item.getName());
        holder.last_word.setText(item.getName());



    }

    public void setDatas(List<Friend> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Friend getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView name;//好友名，小组名
        public TextView last_word;//最后发言人+内容


        public VH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.friendName);
            last_word = (TextView) itemView.findViewById(R.id.last_msg);
        }
    }
}
