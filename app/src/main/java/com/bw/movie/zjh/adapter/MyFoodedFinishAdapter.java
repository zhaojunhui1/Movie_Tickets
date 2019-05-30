package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.my.MyFoodedBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 14:30
 * desc   :
 * version: 1.0
 */
public class MyFoodedFinishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyFoodedBean.ResultBean> mFinish;
    private Context mContext;

    public MyFoodedFinishAdapter(Context mContext) {
        this.mContext = mContext;
        mFinish = new ArrayList<>();
    }

    public void setDatas(List<MyFoodedBean.ResultBean> result) {
        mFinish.clear();
        if (result != null) {
            mFinish.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MyFoodedBean.ResultBean> result) {
        if (result != null) {
            mFinish.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_fooded_finish_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.movie_name.setText(mFinish.get(i).getMovieName());
        mHolder.starttime.setText(mFinish.get(i).getBeginTime() + "-" + mFinish.get(i).getEndTime());
        mHolder.orderid.setText("订单号: " + mFinish.get(i).getOrderId());
        //下单时间
        long createTime = mFinish.get(i).getCreateTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String dataTime = formatter.format(createTime);
        mHolder.datatime.setText("下单时间: " + dataTime);

        mHolder.cinema.setText("影院: " + mFinish.get(i).getCinemaName());
        mHolder.house.setText("影厅: " + mFinish.get(i).getScreeningHall());

        mHolder.number.setText("数量: " + mFinish.get(i).getAmount() + "张");
        mHolder.price.setText("金额: " + mFinish.get(i).getPrice() * mFinish.get(i).getAmount() + "元");

    }

    @Override
    public int getItemCount() {
        return mFinish.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_name)
        TextView movie_name;
        @BindView(R.id.starttime)
        TextView starttime;
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.cinema)
        TextView cinema;
        @BindView(R.id.house)
        TextView house;
        @BindView(R.id.datatime)
        TextView datatime;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.price)
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}


