package com.example.sjh.gcsjdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.utils.db.StudyRecordDao;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Libaoming on 2/5/2019.
 * 11 hour 51 minute
 * project_name : gcsj
 */

public class StudyRecordAdapter extends BaseAdapter {
    private List<StudyRecordDao> list;
    private Context context;
    public StudyRecordAdapter(Context context,List<StudyRecordDao> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public StudyRecordDao getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_study_record_layout,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if ("学习".equals(list.get(position).getName())){
            mHolder.mIvIcon.setImageResource(R.mipmap.study_icon);

        }else if ("娱乐".equals(list.get(position).getName())){
            mHolder.mIvIcon.setImageResource(R.mipmap.play_icon);

        }else if ("睡眠".equals(list.get(position).getName())){
            mHolder.mIvIcon.setImageResource(R.mipmap.sleep_icon);

        }
        mHolder.mTvType.setText(list.get(position).getName());
        mHolder.mTvTime.setText(list.get(position).getStartTime()+"-"+list.get(position).getEndTime());
        mHolder.mTvTotalTime.setText(list.get(position).getTotalTime());
        return convertView;
    }

    public static class ViewHolder{
        private ImageView mIvIcon;
        private TextView mTvTime;
        private TextView mTvTotalTime;
        private TextView mTvType;
        public ViewHolder(View view){
            mIvIcon = view.findViewById(R.id.iv_img_icon);
            mTvTime = view.findViewById(R.id.tv_time);
            mTvTotalTime = view.findViewById(R.id.tv_study_totalTime);
            mTvType = view.findViewById(R.id.tv_type);
        }
    }
}
