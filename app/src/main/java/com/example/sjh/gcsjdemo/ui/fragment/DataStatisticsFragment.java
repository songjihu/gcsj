package com.example.sjh.gcsjdemo.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.ui.view.PieChartView;
import com.example.sjh.gcsjdemo.ui.view.PieModel;
import com.example.sjh.gcsjdemo.utils.db.DBHelper;
import com.example.sjh.gcsjdemo.utils.db.StudyRecordDao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataStatisticsFragment extends Fragment {

    private View footView;
    private PieChartView mPieChart;
    private List<PieModel> pieModelList = new ArrayList<>();
    private List<Integer> colorList = new ArrayList<>();
    private DBHelper db;
    private float totalLen = 0;
    private float studyLen = 0;
    private float entryLen = 0;
    private float sleepLen = 0;
    private List<StudyRecordDao> dataList = new ArrayList<>();

    public DataStatisticsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        footView = inflater.inflate(R.layout.fragment_data_statistics, container, false);
        mPieChart = footView.findViewById(R.id.tv_pieChart);
        db = DBHelper.getInstance(getContext());
        dataList = db.select();
        //分别获取所用总时间
        for (int i = 0; i < dataList.size(); i++) {
            totalLen+=dataList.get(i).getTimeLength();
            if ("学习".equals(dataList.get(i).getName())){
                studyLen+=dataList.get(i).getTimeLength();
            }else if ("娱乐".equals(dataList.get(i).getName())){
                entryLen+=dataList.get(i).getTimeLength();
            }else if ("睡眠".equals(dataList.get(i).getName())){
                sleepLen+=dataList.get(i).getTimeLength();
            }
        }
        //填充饼图
        pieModelList.add(new PieModel(Color.YELLOW,studyLen/totalLen));
        pieModelList.add(new PieModel(Color.CYAN,entryLen/totalLen));
        pieModelList.add(new PieModel(Color.RED,1-sleepLen/totalLen));
        mPieChart.setData(pieModelList);
        mPieChart.startAnima();
        return footView;
    }

}
