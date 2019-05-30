package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.my.MyLikeMovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/22 15:04
 * desc   :
 * version: 1.0
 */
public class MyLikeMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyLikeMovieBean.ResultBean> mMovie;
    private Context mContext;

    public MyLikeMovieAdapter(Context mContext) {
        this.mContext = mContext;
        mMovie = new ArrayList<>();
    }

    public void setDatas(List<MyLikeMovieBean.ResultBean> result) {
        mMovie.clear();
        if (result != null){
            mMovie.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MyLikeMovieBean.ResultBean> result) {
        if (result != null){
            mMovie.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_like_movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.my_movie_image.setImageURI(mMovie.get(i).getImageUrl());
        mHolder.my_movie_name.setText(mMovie.get(i).getName());
        mHolder.my_movie_summary.setText("简介: "+mMovie.get(i).getSummary());

        //日期  时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      /*  formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));*/
        String dataTime = formatter.format(mMovie.get(i).getReleaseTime());
        mHolder.my_movie_time.setText(dataTime);

    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_movie_image)
        SimpleDraweeView my_movie_image;
        @BindView(R.id.my_movie_name)
        TextView my_movie_name;
        @BindView(R.id.my_movie_summary)
        TextView my_movie_summary;
        @BindView(R.id.my_movie_time)
        TextView my_movie_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
