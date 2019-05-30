package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.bean.ShangYingBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 21:02
 * @Description:
 */
public class TabThreeAdapter extends RecyclerView.Adapter<TabThreeAdapter.Holder> {

    public List<ShangYingBean.ResultBean> data;
    public Context mContext;

    public TabThreeAdapter(List<ShangYingBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_one_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        String name = data.get(i).getName();
        holder.tab_one_name.setText(name);

        String summary = data.get(i).getSummary();
        holder.tab_one_jianjie.setText(summary);

        String imageUrl = data.get(i).getImageUrl();
        holder.tab_one_image.setImageURI(Uri.parse(imageUrl));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView tab_one_image;
        private TextView tab_one_name;
        private TextView tab_one_jianjie;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tab_one_image = itemView.findViewById(R.id.tab_one_image);
            tab_one_name = itemView.findViewById(R.id.tab_one_name);
            tab_one_jianjie = itemView.findViewById(R.id.tab_one_jianjie);
        }
    }
}
