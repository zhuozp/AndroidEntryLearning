package com.example.zhipengzhuo.entrytask.learning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.zhipengzhuo.entrytask.EntryTaskApp;
import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.data.DataManager;
import com.example.zhipengzhuo.entrytask.database.DaoSession;
import com.example.zhipengzhuo.entrytask.event.LearningDataEvent;
import com.example.zhipengzhuo.entrytask.model.LearningData;
import com.example.zhipengzhuo.entrytask.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;


public class AllPageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mRecycleViewAdapter;
    private List<LearningData> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_all_page, null);
        initView(view);
        init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mDatas != null) {
            mDatas.clear();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(LearningDataEvent event) {
        final LearningDataEvent tmpEvent = event;
        Task.call(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                // 删除的数据，才能进入all页面
                synchronized (mDatas) {
                    if (tmpEvent != null && tmpEvent.data == null || tmpEvent.data.getProcess() == 1) {
                        int tmpSerialNo = tmpEvent.data.getSerialNo();
                        for (int i = 0; i < mDatas.size(); i++) {
                            if (tmpSerialNo == mDatas.get(i).getSerialNo()) {
                                mDatas.get(i).setProcess(0);
                                break;
                            } else if (mDatas.get(i).getSerialNo() < tmpSerialNo && i < mDatas.size() - 1 && mDatas.get(i + 1).getSerialNo() > tmpSerialNo) {
                                LearningData tmpData = tmpEvent.data.clone();
                                tmpData.setProcess(0);
                                mDatas.add(i + 1, tmpData);
                                break;
                            } else if (i == 0 && mDatas.get(0).getSerialNo() > tmpSerialNo) {
                                LearningData tmpData = tmpEvent.data.clone();
                                tmpData.setProcess(0);
                                mDatas.add(0, tmpData);
                                break;
                            } else if (i == mDatas.size() - 1) {
                                LearningData tmpData = tmpEvent.data.clone();
                                tmpData.setProcess(0);
                                mDatas.add(mDatas.size(), tmpData);
                                break;
                            }
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Boolean, Void>() {
            @Override
            public Void then(Task<Boolean> task) throws Exception {
                boolean flag = task.getResult();
                if (flag) {
                    mRecycleViewAdapter.setData(mDatas);
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecycleViewAdapter = new RecycleViewAdapter(getContext(), mClickCallback);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    RecycleViewAdapter.IClickCallback mClickCallback = new RecycleViewAdapter.IClickCallback() {
        @Override
        public void click(final LearningData data) {
            synchronized (mDatas) {
                for (int i = 0; mDatas != null && i < mDatas.size(); i++) {
                    if (data != null && data.getSerialNo() == mDatas.get(i).getSerialNo()) {
                        mDatas.remove(i);
                        mRecycleViewAdapter.setData(mDatas);
                        Task.call(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                if (getActivity() != null) {
                                    DataManager.updateData((EntryTaskApp) getActivity().getApplication(), data, 1);
                                }
                                return true;
                            }
                        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Boolean, Void>() {
                            @Override
                            public Void then(Task<Boolean> task) throws Exception {
                                LearningDataEvent event = new LearningDataEvent();
                                event.data = data.clone();
                                EventBus.getDefault().post(event);
                                return null;
                            }
                        }, Task.BACKGROUND_EXECUTOR);
                        break;
                    }
                }
            }
        }
    };

    private void init() {
        Task.call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (!PreferenceUtils.getBoolean(getContext(), PreferenceUtils.KEY_INIT_DATA, false)) {
                    if (getActivity() != null) {
                        EntryTaskApp app = (EntryTaskApp) getActivity().getApplication();
                        DataManager.initData(app);
                    }
                    PreferenceUtils.saveBoolean(getContext(), PreferenceUtils.KEY_INIT_DATA, true);
                }
                Log.d("MainActivity","init data");
                return true;
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Boolean, List>() {

            @Override
            public List then(Task<Boolean> task) throws Exception {
                if (getActivity() == null) {
                    return null;
                }
                return DataManager.getDatas((EntryTaskApp) getActivity().getApplication(), "where process = ?", "0");
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<List, Void>() {

            @Override
            public Void then(Task<List> task) throws Exception {
                List<LearningData> datas = task.getResult();
                mDatas.addAll(datas);
                mRecycleViewAdapter.setData(datas);
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
