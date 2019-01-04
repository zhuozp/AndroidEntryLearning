package com.example.zhipengzhuo.entrytask;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zhipengzhuo.entrytask.learning.LearningFragment;
import com.example.zhipengzhuo.entrytask.mine.MineFragment;
import com.example.zhipengzhuo.entrytask.widget.ViewPagerTabGroupView;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter implements ViewPagerTabGroupView.ITabable {

    private Context mContext;

    private Class<? extends Fragment>[] clazz = new Class[] {LearningFragment.class, MineFragment.class};
    private int[] iconRess = {R.drawable.icon_learning_page_selector, R.drawable.icon_mine_page_selector};
    private int[] titleRess = {R.string.home_entry_learning_page, R.string.home_entry_mine_page};

    public MainFragmentPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mContext == null) {
            return null;
        }
        return mContext.getString(titleRess[position]);
    }

    @Override
    public Fragment getItem(int i) {
        try {
            return clazz[i].newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Drawable getPageIcon(int position) {
        if (mContext == null) {
            return null;
        }
        return mContext.getResources().getDrawable(iconRess[position]);
    }

    @Override
    public int getCount() {
        return clazz.length;
    }
}
