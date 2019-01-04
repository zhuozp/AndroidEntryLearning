package com.example.zhipengzhuo.entrytask.learning;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.model.LearningData;


public class LearningAdapter extends FragmentPagerAdapter {

    public static final int[] TITLES = { R.string.learning_page_all_title, R.string.learning_page_delete_title};
    private Class<? extends Fragment>[] clazz = new Class[] {AllPageFragment.class, DeletePageFragment.class};

    private Context mContext;

    public LearningAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
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
    public int getCount() {
        return clazz.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mContext == null) {
            return null;
        }
        return mContext.getString(TITLES[position]);
    }
}
