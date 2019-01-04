package com.example.zhipengzhuo.entrytask;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.zhipengzhuo.entrytask.widget.UnScrollViewPager;
import com.example.zhipengzhuo.entrytask.widget.ViewPagerTabGroupView;

public class MainActivity extends FragmentActivity {

    private UnScrollViewPager mViewPager;
    private ViewPagerTabGroupView mTabGroup;
    private MainFragmentPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mViewPager = (UnScrollViewPager) findViewById(R.id.viewPage);
        mTabGroup = (ViewPagerTabGroupView) findViewById(R.id.tab_group);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mTabGroup.setViewPager(mViewPager);
    }
}

