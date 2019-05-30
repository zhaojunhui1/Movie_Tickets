package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.my.MyRemindBean;
import com.bw.movie.zjh.utils.dataUtil.DataTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 20:54
 * desc   :
 * version: 1.0
 */
public class MyRemindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyRemindBean.ResultBean> mRemind;
    private Context mContext;

    public MyRemindAdapter(Context mContext) {
        this.mContext = mContext;
        mRemind = new ArrayList<>();
    }

    public void setDatas(List<MyRemindBean.ResultBean> result) {
        mRemind.clear();
        if (result != null){
            mRemind.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MyRemindBean.ResultBean> result) {
        if (result != null){
            mRemind.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_remind_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.remind_name.setText(mRemind.get(i).getTitle());
        mHolder.remind_details.setText(mRemind.get(i).getContent());
        //信息时间
        String newChatTime = DataTime.getNewChatTime(mRemind.get(i).getPushTime());
        mHolder.datatime.setText(newChatTime);

        //判断消息的状态
        if (mRemind.get(i).getStatus() == 0){  //未读
            mHolder.message.setVisibility(View.VISIBLE);
            mHolder.message_num.setText(1+"");

        }else if (mRemind.get(i).getStatus() == 1){   //已读
            mHolder.message.setVisibility(View.GONE);
        }

        //点击改变状态
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReMindListener != null){
                    mReMindListener.updataStatus(mRemind.get(i).getId()+"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRemind.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.remind_name)
        TextView remind_name;
        @BindView(R.id.remind_details)
        TextView remind_details;
        @BindView(R.id.datatime)
        TextView datatime;
        @BindView(R.id.message_num)
        TextView message_num;

        @BindView(R.id.message)
        RelativeLayout message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    ReMindListener mReMindListener;
    public void setReMindListener(ReMindListener reMindListener){
        this.mReMindListener = reMindListener;
    }
    public interface ReMindListener{
        void updataStatus(String id);
    }

}
