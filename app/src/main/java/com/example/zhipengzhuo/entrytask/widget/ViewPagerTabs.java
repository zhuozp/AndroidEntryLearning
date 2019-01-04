package com.example.zhipengzhuo.entrytask.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.zhipengzhuo.entrytask.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTabs extends HorizontalScrollView implements
        ViewPager.OnPageChangeListener {

    public static final String TAG = "ViewPagerTabs";

    private ViewPager mViewPager;

    private PagerAdapter mAdapter;

    private LinearLayout mTabContainer;

    private LinearLayout mStripViewContainer;

    private View mStripView;

    private List<View> mTabTitles;

    private int mLastSelection = -1;

    private int mSelection = -1;

    private int mNextSelection = -1;

    private int mStripSelection = -1;

    private final DataSetObserver mObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            onViewPagerDataChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            onViewPagerDataChanged();
        }
    };

    public ViewPagerTabs(Context context) {
        super(context);
    }

    public ViewPagerTabs(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerTabs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setHorizontalScrollBarEnabled(false);
        View.inflate(getContext(), R.layout.layout_view_pager_tabs, this);
        mTabTitles = new ArrayList<View>();
        mTabContainer = (LinearLayout) findViewById(R.id.view_pager_tab_container);
        mStripViewContainer = (LinearLayout) findViewById(R.id.view_pager_tab_strip);
        mStripView = findViewById(R.id.view_pager_tab_strip_view);
    }

    public void init(ViewPager pager) {
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        mViewPager = pager;
        mViewPager.setOnPageChangeListener(this);

        mAdapter = pager.getAdapter();
        if (mAdapter == null) {
            throw new RuntimeException("init() 应该在ViewPager设置完Adapter之后调用.");
        }
        mAdapter.registerDataSetObserver(mObserver);
        onViewPagerDataChanged();
    }

    public void onViewPagerDataChanged() {
        clearTabs();
        if (mAdapter != null) {
            final int count = mAdapter.getCount();
            final int width = getContext().getResources().getDisplayMetrics().widthPixels;
            int used = 0;
            for (int i = 0; i < count; i++) {
                used += addTab(i, mAdapter.getPageTitle(i), width);
            }
            if (used < width && count > 0) {
                int offset = (width - used) / count;
                for (int i = 0; i < count; i++) {
                    View v = mTabTitles.get(i);
                    ViewGroup.LayoutParams params = v.getLayoutParams();
                    params.width += offset;
                    v.setLayoutParams(params);
                    v.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                            MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY));
                }
            }
            setSelection(mNextSelection != -1 ? mNextSelection : (mSelection == -1 ? 0 : mSelection));
        }
    }

    private int addTab(int position, CharSequence title, int width) {
        View view;
        if (position < mTabTitles.size()) {
            view = mTabTitles.get(position);
        } else {
            view = View.inflate(getContext(), R.layout.include_view_pager_title_view, null);
            view.setOnClickListener(mTabClickListener);
            mTabTitles.add(view);
        }
        view.setTag(position);
        TextView text = (TextView) view.findViewById(R.id.tab_title);
        text.setText(title);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabContainer.addView(view, params);
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY));
        int measuredWidth = view.getMeasuredWidth();
        params = view.getLayoutParams();
        params.width = measuredWidth;
        view.setLayoutParams(params);
        return measuredWidth;
    }

    private void clearTabs() {
        mTabContainer.removeAllViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        onViewPagerDataChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        if (mPageListener != null) {
            mPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mPageListener != null) {
            mPageListener.onPageSelected(position);
        }
        for (int i = 0; i < mTabTitles.size(); i++) {
            View view = mTabTitles.get(i);
            if (position == i) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
        }
        setSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageListener != null) {
            mPageListener.onPageScrollStateChanged(state);
        }
    }

    /**
     * Tab点击回调.
     */
    public final OnClickListener mTabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof Integer) {
                int position = (Integer) tag;
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(position);
                }
            }
        }
    };

    public void setSelection(int position) {
        if (mSelection != position) {
            if (mTabTitles.size() == 0) {
                mNextSelection = position;
                return;
            }
            mLastSelection = mSelection;
            mSelection = position;
            // 选中位置需要变化
            mStripSelection = -1;
            mNextSelection = -1;
            View view = mTabTitles.get(position);
            int measureWidth = view.getMeasuredWidth();
            ViewGroup.LayoutParams params = mStripView.getLayoutParams();
            int currentWidth = mStripView.getMeasuredWidth();
            int endWidth = measureWidth;
            mStripView.setLayoutParams(params);
            if (mLastSelection == -1) {
                mLastSelection = 0;
            }
            View oldView = mTabTitles.get(mLastSelection);
            View newView = mTabTitles.get(mSelection);
            int currX = oldView.getLeft();
            int lastX = newView.getLeft();

            smoothScrollToPosition(currX, lastX, currentWidth, endWidth);
            // 如果长度超出了当前可视长度，那么ViewPagerTabs也应该跟着滑动.
            View current = mTabTitles.get(mSelection);
            final int left = current.getLeft();
            final int right = current.getRight();
            final int scrollX = getScrollX();
            if (left < scrollX) {
                smoothScrollTo(left, 0);
            } else if (right > scrollX + getMeasuredWidth()) {
                smoothScrollTo(right - current.getMeasuredWidth(), 0);
            }
            mStripView.setVisibility(View.VISIBLE);
        }
    }

    private Scroller mScroller;

    /**
     * 平滑移动至指定位置.
     *
     * @param from
     * @param to
     * @param fromWidth
     * @param toWidth
     */
    private void smoothScrollToPosition(int from, int to, int fromWidth, int toWidth) {
        if (mScroller != null) {
            mScroller.abortAnimation();
        }
        mScroller = new Scroller(getContext());
        mScroller.startScroll(-from, fromWidth, from - to, toWidth - fromWidth, 800);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller != null) {
            if (!mScroller.isFinished()) {
                mScroller.computeScrollOffset();
                int x = mScroller.getCurrX();
                // 同时对宽度进行处理，防止宽度突变.
                int width = mScroller.getCurrY();
                ViewGroup.LayoutParams params = mStripView.getLayoutParams();
                params.width = width;
                mStripView.setLayoutParams(params);
                mStripViewContainer.scrollTo(x, 0);
                invalidate();
                return;
            }
        }
        if (mSelection != -1 && mStripSelection != mSelection) {
            // 防止反复调用，导致重复绘制.
            mStripSelection = mSelection;
            View view = mTabTitles.get(mSelection);
            ViewGroup.LayoutParams params = mStripView.getLayoutParams();
            params.width = view.getMeasuredWidth();
            mStripView.setLayoutParams(params);
            mStripViewContainer.scrollTo(-view.getLeft(), 0);
        }
    }

    private ViewPager.OnPageChangeListener mPageListener;

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mPageListener = listener;
    }
}
