package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.my.MyLikeCinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/22 16:08
 * desc   :
 * version: 1.0
 */
public class MyLikeCinemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyLikeCinemaBean.ResultBean> mMovie;
    private Context mContext;

    public MyLikeCinemaAdapter(Context mContext) {
        this.mContext = mContext;
        mMovie = new ArrayList<>();
    }

    public void setDatas(List<MyLikeCinemaBean.ResultBean> result) {
        mMovie.clear();
        if (result != null){
            mMovie.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MyLikeCinemaBean.ResultBean> result) {
        if (result != null){
            mMovie.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_like_cinema_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.like_image.setImageURI(mMovie.get(i).getLogo());
        mHolder.like_name.setText(mMovie.get(i).getName());
        mHolder.like_address.setText(mMovie.get(i).getAddress());

    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.like_image)
        SimpleDraweeView like_image;
        @BindView(R.id.like_name)
        TextView like_name;
        @BindView(R.id.like_address)
        TextView like_address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
