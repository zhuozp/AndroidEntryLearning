package com.example.zhipengzhuo.entrytask.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.model.CategoryInfo;
import com.example.zhipengzhuo.entrytask.model.UserInfo;
import com.example.zhipengzhuo.entrytask.network.GetRequest;
import com.example.zhipengzhuo.entrytask.network.RetrofitUtils;
import com.example.zhipengzhuo.entrytask.widget.GrideDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MineFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CategoryInfoViewAdapter mAdapter;
    private TextView mNameTv;
    private TextView mLevelTv;
    private TextView mContentTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        reqData();
        return view;
    }

    private void initView(View view) {
        mAdapter = new CategoryInfoViewAdapter(getContext());
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new GrideDividerItemDecoration(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        RecyclerViewHeader header = view.findViewById(R.id.header);
        header.attachTo(mRecyclerView);

        mNameTv = view.findViewById(R.id.name);
        mLevelTv = view.findViewById(R.id.processIcon);
        mContentTv = view.findViewById(R.id.content);
    }

    private void reqData() {
        Retrofit retrofit = RetrofitUtils.getsRetrofit();

        GetRequest getRequest = retrofit.create(GetRequest.class);
        Observable<UserInfo> observable = getRequest.reqUserInfo(2);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (userInfo != null && userInfo.getInfo() != null) {

                            mContentTv.setText(userInfo.getInfo().getContent());
                            mAdapter.setmDatas(userInfo.getInfo().getGridview());

                            mNameTv.setText(userInfo.getInfo().getName());
                            mLevelTv.setText("等级：" + userInfo.getInfo().getLevel());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
