package com.example.sjh.gcsjdemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.adapter.StudyRecordAdapter;
import com.example.sjh.gcsjdemo.utils.db.DBHelper;
import com.example.sjh.gcsjdemo.utils.db.StudyRecordDao;

import java.util.List;

public class RecordFragment extends Fragment {

    private View footView;
    private ListView mListView;
    private DBHelper db;
    private StudyRecordAdapter adapter;

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        footView = inflater.inflate(R.layout.fragment_record, container, false);
        db = DBHelper.getInstance(getContext());
        initView();
        initData();
        return footView;
    }

    private void initData() {
        List<StudyRecordDao> daoList = db.select();
        adapter = new StudyRecordAdapter(getContext(),daoList);
        mListView.setAdapter(adapter);
    }

    private void initView() {
        mListView = (ListView) footView.findViewById(R.id.lv_study_record);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
