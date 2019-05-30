package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.cinema.MovieCoverFlowBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/14 19:20
 * desc   :
 * version: 1.0
 */
public class CinemaCoverFlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieCoverFlowBean.ResultBean> mData;
    private Context mContext;

    public CinemaCoverFlowAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<MovieCoverFlowBean.ResultBean> result) {
        if (result != null) {
            mData.addAll(result);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.hot_imageView.setImageURI(mData.get(i).getImageUrl());
        mHolder.name.setText(mData.get(i).getName());
        mHolder.dataTime.setText(mData.get(i).getDuration());

        //点击选择电影
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovieCoverFlowListener != null){
                    mMovieCoverFlowListener.OnMovieClick(mData.get(i).getId()+"", mData.get(i).getName());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_imageView)
        SimpleDraweeView hot_imageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView dataTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    MovieCoverFlowListener mMovieCoverFlowListener;
    public void setMovieCoverFlowListener(MovieCoverFlowListener movieCoverFlowListener){
        this.mMovieCoverFlowListener = movieCoverFlowListener;
    }
    //自定义的接口回调
    public interface MovieCoverFlowListener{
        void OnMovieClick(String movieId, String movieName);
    }

}
