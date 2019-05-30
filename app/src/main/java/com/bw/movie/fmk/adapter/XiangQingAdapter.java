package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.bean.XiangQingZhuYeBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 9:40
 * @Description:
 */
public class XiangQingAdapter extends XRecyclerView.Adapter<XiangQingAdapter.Holder> {

    private List<XiangQingZhuYeBean.ResultBean> data;
    private Context mContext;


    public XiangQingAdapter(List<XiangQingZhuYeBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.xiangqing_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String starring = data.get(i).getStarring();
        holder.xiangqing_item_name.setText(starring);

    }



    @Override
    public int getItemCount() {
        return data.size();
    }



    public class Holder extends XRecyclerView.ViewHolder {

        private TextView xiangqing_item_name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            xiangqing_item_name = itemView.findViewById(R.id.xiangqing_item_name);
        }
    }
}
