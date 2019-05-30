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
import com.bw.movie.zjh.beans.cinema.CinemaCallBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/15 20:38
 * desc   :
 * version: 1.0
 */
public class CinemaCallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CinemaCallBean.ResultBean> mCalls;
    private Context mContext;

    public CinemaCallAdapter(Context mContext) {
        this.mContext = mContext;
        mCalls = new ArrayList<>();
    }

    public void setDatas(List<CinemaCallBean.ResultBean> result) {
        mCalls.clear();
        if (result != null){
            mCalls.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<CinemaCallBean.ResultBean> result) {
        if (result != null){
            mCalls.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cinema_details_call_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.call_image.setImageURI(mCalls.get(i).getCommentHeadPic());
        mHolder.call_name.setText(mCalls.get(i).getCommentUserName());
        mHolder.call_show.setText(mCalls.get(i).getCommentContent());
        //日期  时间
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String dataTime = formatter.format(mCalls.get(i).getCommentTime());
        mHolder.call_datatime.setText(dataTime);

        mHolder.praise_number.setText(mCalls.get(i).getGreatNum() + "");   //点赞数量


        //点击切换图片
        if (mCalls.get(i).getIsGreat() == 1) {
            mHolder.call_praise.setBackgroundResource(R.mipmap.com_icon_praise_selected);
        } else {
            mHolder.call_praise.setBackgroundResource(R.mipmap.com_icon_praise_default);
        }
        // 评论 点赞
        mHolder.call_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCalls.get(i).getIsGreat() == 1) {

                } else {
                    mCalls.get(i).setIsGreat(1);
                    mCalls.get(i).setGreatNum(mCalls.get(i).getGreatNum() + 1);
                }
                if (mCallListener != null) {
                    mCallListener.OnGreat(mCalls.get(i).getCommentId(), mCalls.get(i).getIsGreat() == 1);
                }
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCalls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.call_image)
        SimpleDraweeView call_image;
        @BindView(R.id.call_name)
        TextView call_name;
        @BindView(R.id.call_show)
        TextView call_show;
        @BindView(R.id.call_datatime)
        TextView call_datatime;
        @BindView(R.id.call_praise)
        ImageView call_praise;
        @BindView(R.id.praise_number)
        TextView praise_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    CallListener mCallListener;

    public void setCallListener(CallListener callListener) {
        this.mCallListener = callListener;
    }

    public interface CallListener {
        void OnGreat(int id, boolean isGreat);
    }


}
