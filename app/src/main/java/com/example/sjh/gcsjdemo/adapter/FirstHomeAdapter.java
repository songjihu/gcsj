package com.example.sjh.gcsjdemo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.entity.Article;
import com.example.sjh.gcsjdemo.helper.DetailTransition;
import com.example.sjh.gcsjdemo.listener.OnItemClickListener;
import com.example.sjh.gcsjdemo.ui.fragment.first.child.FirstDetailFragment;

/**
 * Created by YoKeyword on 16/6/5.
 * 修改于 19/4/8.
 * 修改为未上课程的提示信息
 */
public class FirstHomeAdapter extends RecyclerView.Adapter<FirstHomeAdapter.VH> {
    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;


    public FirstHomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //R.layout.item_bxz_home_first  为item的布局形式
        View view = mInflater.inflate(R.layout.item_bxz_home_first1, parent, false);

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
        Article item = mItems.get(position);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.checkinmsg, String.valueOf(position) + "1");
        ViewCompat.setTransitionName(holder.checkinstatus, String.valueOf(position) + "2");

        holder.checkinmsg.setText(item.getCheckInStatus());
        holder.checkinstatus.setText(item.getTitle());
    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Article getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView checkinmsg;//获取签到课程名称
        public TextView checkinstatus;//获取签到状态

        public VH(View itemView) {
            super(itemView);
            checkinmsg = (TextView) itemView.findViewById(R.id.checkinmsg);
            checkinstatus = (TextView) itemView.findViewById(R.id.homeworkmsg);
        }
    }
}
