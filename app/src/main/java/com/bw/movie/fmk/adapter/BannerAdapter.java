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
import com.bw.movie.fmk.bean.BannerBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/13 9:22
 * @Description:
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.Holder> {

    public List<BannerBean.ResultBean> data;
    public Context mContext;

    public BannerAdapter(List<BannerBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    //接口
    public interface OnItem{
        void onClick(int position);
    }

    private OnItem onItem;

    //一个公共的方法
    public void setOnItem(OnItem onItem) {
        this.onItem = onItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        String name = data.get(i).getName();
        holder.banner_namae.setText(name);

        String imageUrl = data.get(i).getImageUrl();
        holder.banner_item_id.setImageURI(Uri.parse(imageUrl));

        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItem!=null){
                    onItem.onClick(i);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView banner_item_id;
        private TextView banner_namae;

        public Holder(@NonNull View itemView) {
            super(itemView);
            banner_item_id = itemView.findViewById(R.id.banner_item_id);
            banner_namae = itemView.findViewById(R.id.banner_namae);
        }
    }
}
