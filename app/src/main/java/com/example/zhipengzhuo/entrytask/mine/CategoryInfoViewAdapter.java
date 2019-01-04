package com.example.zhipengzhuo.entrytask.mine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhipengzhuo.entrytask.R;
import com.example.zhipengzhuo.entrytask.model.CategoryInfo;
import com.example.zhipengzhuo.entrytask.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryInfoViewAdapter extends RecyclerView.Adapter<CategoryInfoViewAdapter.CategoryInfoViewHolder> {

    private List<UserInfo.Info.GridView> mDatas = new ArrayList<>();

    private Context mContext;

    public CategoryInfoViewAdapter(List<UserInfo.Info.GridView> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public CategoryInfoViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmDatas(List<UserInfo.Info.GridView> datas) {
        if (this.mDatas != null) {
            this.mDatas.clear();
        }

        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CategoryInfoViewHolder holder = new CategoryInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_info_view, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryInfoViewHolder categoryInfoViewHolder, int i) {
        UserInfo.Info.GridView bean = mDatas.get(i);
        Glide.with(mContext).load(bean.getUrl()).into(categoryInfoViewHolder.mUrlIv);
        categoryInfoViewHolder.mNameTv.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CategoryInfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUrlIv;
        private TextView mNameTv;

        public CategoryInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mUrlIv = (ImageView) itemView.findViewById(R.id.img);
            mNameTv = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
