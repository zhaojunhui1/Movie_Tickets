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
import com.bw.movie.fmk.bean.RYingBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/13 9:22
 * @Description:
 */
public class RYingAdapter extends RecyclerView.Adapter<RYingAdapter.Holder> {

    public List<RYingBean.ResultBean> data;
    public Context mContext;

    public RYingAdapter(List<RYingBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    //接口
    public interface OnItem{
        void onClick(int position, String movieId);
    }

    private OnItem onItem;

    //一个公共的方法
    public void setOnItem(OnItem onItem) {
        this.onItem = onItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rmen_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String name = data.get(i).getName();
        holder.rmen_item_namae.setText(name);

        String imageUrl = data.get(i).getImageUrl();
        holder.rmen_item_id.setImageURI(Uri.parse(imageUrl));
        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItem!=null){
                    onItem.onClick(i,data.get(i).getId()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView rmen_item_id;
        private TextView rmen_item_namae;

        public Holder(@NonNull View itemView) {
            super(itemView);
            rmen_item_id = itemView.findViewById(R.id.rmen_item_id);
            rmen_item_namae = itemView.findViewById(R.id.rmen_item_namae);
        }
    }
}
