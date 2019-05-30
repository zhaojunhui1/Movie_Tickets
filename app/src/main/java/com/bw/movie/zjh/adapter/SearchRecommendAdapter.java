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
import com.bw.movie.zjh.beans.cinema.SearchRecommendBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/17 15:27
 * desc   :
 * version: 1.0
 */
public class SearchRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchRecommendBean.ResultBean> mSearch;
    private Context mContext;

    public SearchRecommendAdapter(Context mContext) {
        this.mContext = mContext;
        mSearch = new ArrayList<>();
    }

    public void setDatas(List<SearchRecommendBean.ResultBean> result) {
        mSearch.clear();
        if (result != null){
            mSearch.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<SearchRecommendBean.ResultBean> result) {
        if (result != null){
            mSearch.addAll(result);
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
        mHolder.recommend_image.setImageURI(mSearch.get(i).getLogo());
        mHolder.recommend_name.setText(mSearch.get(i).getName());
        mHolder.recommend_address.setText(mSearch.get(i).getAddress());
        mHolder.recommend_length.setText(mSearch.get(i).getDistance() + "km");

        //点击切换小红心
        if (mSearch.get(i).getFollowCinema() == 1) {
            mHolder.recommend_give.setBackgroundResource(R.mipmap.com_icon_collection_selected);
        } else {
            mHolder.recommend_give.setBackgroundResource(R.mipmap.com_icon_collection_default);
        }
        //点点关注
        mHolder.recommend_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearch.get(i).getFollowCinema() == 1) {
                    mSearch.get(i).setFollowCinema(2);
                } else {
                    mSearch.get(i).setFollowCinema(1);
                }
                if (mSearchRecommendListener != null){
                    mSearchRecommendListener.onLike(mSearch.get(i).getId(), mSearch.get(i).getFollowCinema() == 1);
                }
                notifyItemChanged(i);
            }
        });

        //条目点击查询
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchRecommendListener != null){
                    mSearchRecommendListener.OnClick(mSearch.get(i).getId()+"", mSearch.get(i).getLogo(), mSearch.get(i).getName(), mSearch.get(i).getAddress());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearch.size();
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

    SearchRecommendListener mSearchRecommendListener;

    public void setSearchRecommendListener(SearchRecommendListener searchRecommendListener) {
        this.mSearchRecommendListener = searchRecommendListener;
    }

    public interface SearchRecommendListener {
        void onLike(int id, boolean isLike);
        void OnClick(String id, String logo, String name, String address);
    }

}
