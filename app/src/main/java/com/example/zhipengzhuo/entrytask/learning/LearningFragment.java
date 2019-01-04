package com.example.zhipengzhuo.entrytask.learning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.widget.ViewPagerTabs;

public class LearningFragment extends Fragment {

    private ViewPager mViewPager;
    private ViewPagerTabs mViewPagerTabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning, null);
        init(view);
        return view;
    }

    private void init(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new LearningAdapter(getFragmentManager(), getContext()));
        mViewPagerTabs = (ViewPagerTabs) view.findViewById(R.id.tabs);
        mViewPagerTabs.init(mViewPager);
    }
}
