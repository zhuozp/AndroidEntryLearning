package com.example.zhipengzhuo.entrytask.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.model.LearningData;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.LearningViewHolder> {

    private Context mContext;
    private ArrayList<LearningData> mDatas = new ArrayList<>();
    private IClickCallback mIClickCallback;

    public void setData(List<LearningData> datas) {
        if (mDatas != null && !mDatas.isEmpty()) {
            mDatas.clear();
        }

        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public RecycleViewAdapter(Context mContext, IClickCallback iClickCallback) {
        this.mIClickCallback = iClickCallback;
        this.mContext = mContext;
    }

    public RecycleViewAdapter(Context mContext, ArrayList<LearningData> mDatas, IClickCallback callback) {
        this.mIClickCallback = callback;
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @NonNull
    @Override
    public LearningViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LearningViewHolder holder = new LearningViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_learning_view, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LearningViewHolder learningViewHolder, int i) {
        // TODO
        learningViewHolder.mContentTv.setText(mDatas.get(i).getName());
        learningViewHolder.mProcessIconIv.setImageResource(mDatas.get(i).getProcess() == 1 ? R.drawable.ic_dropdown_check : R.drawable.read_more);
        learningViewHolder.mProcessIconIv.setTag(mDatas.get(i));
        learningViewHolder.mProcessIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    LearningData data = (LearningData) v.getTag();
                    Log.d("RecycleViewAdapter", "name is " + data.getName());
                    if (mIClickCallback != null) {
                        mIClickCallback.click(data);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class LearningViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconIv;
        public TextView mContentTv;
        public ImageView mProcessIconIv;

        public LearningViewHolder(@NonNull View itemView) {
            super(itemView);
            mIconIv = (ImageView) itemView.findViewById(R.id.icon);
            mContentTv = (TextView) itemView.findViewById(R.id.content);
            mProcessIconIv = (ImageView) itemView.findViewById(R.id.processIcon);
        }
    }

    interface IClickCallback {
        public void click(LearningData data);
    }
}
