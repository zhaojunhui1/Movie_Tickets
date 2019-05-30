package com.bw.movie.zjh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.zjh.beans.cinema.CinemaDetailsWorkBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/14  19:30
 * desc   :
 * version: 1.0
 */
public class CinemaDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CinemaDetailsWorkBean.ResultBean> mWorks;
    private Context mContext;

    public CinemaDetailsAdapter(Context mContext) {
        this.mContext = mContext;
        mWorks = new ArrayList<>();
    }

    public void setDatas(List<CinemaDetailsWorkBean.ResultBean> result) {
        if (result != null){
            mWorks.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cinema_detail_work_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder mHolder = (ViewHolder) viewHolder;

        mHolder.like_name.setText(mWorks.get(i).getScreeningHall());
        mHolder.cinema_start.setText(mWorks.get(i).getBeginTime());
        mHolder.cinema_end.setText(mWorks.get(i).getEndTime());
        //调用
        SpannableString spannableString = changTVsize(mWorks.get(i).getPrice()+"");
        mHolder.cinema_price.setText(spannableString);

        //点击去选座
        mHolder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCinemaDetailsListener != null){
                    mCinemaDetailsListener.onNextClick(
                            mWorks.get(i).getId()+"",
                            mWorks.get(i).getBeginTime(),
                            mWorks.get(i).getEndTime(),
                            mWorks.get(i).getScreeningHall(),
                            mWorks.get(i).getPrice());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWorks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.like_name)
        TextView like_name;
        @BindView(R.id.cinema_start)
        TextView cinema_start;
        @BindView(R.id.cinema_end)
        TextView cinema_end;
        @BindView(R.id.cinema_price)
        TextView cinema_price;
        @BindView(R.id.next)
        RelativeLayout next;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    CinemaDetailsListener mCinemaDetailsListener;
    public void setCinemaDetailsListener(CinemaDetailsListener cinemaDetailsListener){
        this.mCinemaDetailsListener = cinemaDetailsListener;
    }
    //自定义接口
    public interface CinemaDetailsListener{
        //去选座
        void onNextClick(String scheduleId, String begin, String end, String screenHall, double price);
    }

    //价格样式
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


}
