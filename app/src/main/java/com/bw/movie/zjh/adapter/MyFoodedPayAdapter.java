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
import com.bw.movie.zjh.beans.my.MyFoodedBean;
import com.bw.movie.zjh.utils.dataUtil.DataTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/23 11:25
 * desc   :
 * version: 1.0
 */
public class MyFoodedPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyFoodedBean.ResultBean> mPay;
    private Context mContext;

    public MyFoodedPayAdapter(Context mContext) {
        this.mContext = mContext;
        mPay = new ArrayList<>();
    }

    public void setDatas(List<MyFoodedBean.ResultBean> result) {
        mPay.clear();
        if (result != null){
            mPay.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<MyFoodedBean.ResultBean> result) {
        if (result != null){
            mPay.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_fooded_pay_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder mHolder = (ViewHolder) viewHolder;
        //下单时间
        String chatTime = DataTime.getNewChatTime(mPay.get(i).getCreateTime()/*, "MM-dd HH:mm"*/);
        mHolder.ordertime.setText(chatTime);

        mHolder.movie_name.setText(mPay.get(i).getMovieName());
        mHolder.orderid.setText("订单号: "+mPay.get(i).getOrderId());
        mHolder.cinema.setText("影院: "+mPay.get(i).getCinemaName());
        mHolder.house.setText("影厅: "+mPay.get(i).getScreeningHall());
        //时间
        long createTime = mPay.get(i).getCreateTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String dataTime = formatter.format(createTime);
        mHolder.datatime.setText("时间: "+dataTime+" "+mPay.get(i).getBeginTime()+"-"+mPay.get(i).getEndTime());

        mHolder.number.setText("数量: "+mPay.get(i).getAmount()+"张");
        mHolder.price.setText("金额: "+mPay.get(i).getPrice()*mPay.get(i).getAmount()+"元");


        //点击支付
        mHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFoodedPayListener != null){
                    mFoodedPayListener.OnClickPay(mPay.get(i).getOrderId(), mPay.get(i).getPrice()*mPay.get(i).getAmount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPay.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ordertime)
        TextView ordertime;
        @BindView(R.id.movie_name)
        TextView movie_name;
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

        @BindView(R.id.pay)
        RelativeLayout pay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    FoodedPayListener mFoodedPayListener;
    public void setFoodedPayListener(FoodedPayListener foodedPayListener){
        this.mFoodedPayListener = foodedPayListener;
    }
    public interface FoodedPayListener{
        void OnClickPay(String orderId, double priceAll);
    }

}
