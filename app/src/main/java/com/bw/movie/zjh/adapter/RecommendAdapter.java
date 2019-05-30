package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.cinema.RecommendTjBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/13 9:55
 * desc   :  推荐
 * version: 1.0
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecommendTjBean.ResultBean> mDatas;
    private Context mContext;

    public RecommendAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    public void setDatas(List<RecommendTjBean.ResultBean> result) {
        mDatas.clear();
        if (result != null) {
            mDatas.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<RecommendTjBean.ResultBean> result) {
        if (result != null) {
            mDatas.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cinema_recommend_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.recommend_image.setImageURI(mDatas.get(i).getLogo());
        mHolder.recommend_name.setText(mDatas.get(i).getName());
        mHolder.recommend_address.setText(mDatas.get(i).getAddress());
        mHolder.recommend_length.setText(mDatas.get(i).getDistance()*0.001 + "km");

        //点击切换小红心
        if (mDatas.get(i).getFollowCinema() == 1) {
            mHolder.recommend_give.setBackgroundResource(R.mipmap.com_icon_collection_selected);
        } else {
            mHolder.recommend_give.setBackgroundResource(R.mipmap.com_icon_collection_default);
        }
        //点点关注
        mHolder.recommend_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(i).getFollowCinema() == 1) {
                    mDatas.get(i).setFollowCinema(2);
                } else {
                    mDatas.get(i).setFollowCinema(1);
                }
                if (mRecommendListener != null){
                    mRecommendListener.onLike(mDatas.get(i).getId(), mDatas.get(i).getFollowCinema() == 1);
                }
                notifyItemChanged(i);
            }
        });

        //条目点击查询
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecommendListener != null){
                    mRecommendListener.OnClick(mDatas.get(i).getId()+"", mDatas.get(i).getLogo(), mDatas.get(i).getName(), mDatas.get(i).getAddress());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recommend_image)
        SimpleDraweeView recommend_image;
        @BindView(R.id.recommend_name)
        TextView recommend_name;
        @BindView(R.id.recommend_address)
        TextView recommend_address;
        @BindView(R.id.recommend_length)
        TextView recommend_length;
        @BindView(R.id.recommend_give)
        ImageView recommend_give;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    RecommendListener mRecommendListener;

    public void setRecommendListener(RecommendListener recommendListener) {
        this.mRecommendListener = recommendListener;
    }

    public interface RecommendListener {
        void onLike(int id, boolean isLike);
        void OnClick(String id, String logo, String name, String address);
    }

}

