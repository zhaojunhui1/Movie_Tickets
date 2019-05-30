package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.bean.RMenBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 21:02
 * @Description:
 */
public class TabOneAdapter extends RecyclerView.Adapter<TabOneAdapter.Holder> {

    public List<RMenBean.ResultBean> data;
    public Context mContext;

    public TabOneAdapter(List<RMenBean.ResultBean> data, Context mContext) {
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
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String name = data.get(i).getName();
        holder.tab_one_name.setText(name);

        String summary = data.get(i).getSummary();
        holder.tab_one_jianjie.setText(summary);

        String imageUrl = data.get(i).getImageUrl();
        holder.tab_one_image.setImageURI(Uri.parse(imageUrl));

//        int followMovie = data.get(i).getFollowMovie();
//        holder.tab_one_guanzhu.setImageURI(followMovie);

        //点击切换,关注
        if (data.get(i).getFollowMovie()==1){
            holder.tab_one_guanzhu.setBackgroundResource(R.mipmap.com_icon_collection_selected);
        }else{
            holder.tab_one_guanzhu.setBackgroundResource(R.mipmap.com_icon_collection_default);
        }

        //点击
        holder.tab_one_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(i).getFollowMovie()==1){
                    data.get(i).setFollowMovie(2);
                }else{
                    data.get(i).setFollowMovie(1);
                }
                if(onItem!=null){
                    onItem.onGuanLike(data.get(i).getId(),data.get(i).getFollowMovie()==1);
                }
                notifyItemChanged(i);
            }
        });

        //条目点击
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItem!=null){
//                    onItem.onClick(i);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView tab_one_image;
        private TextView tab_one_name;
        private TextView tab_one_jianjie;
        private ImageView tab_one_guanzhu;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tab_one_image = itemView.findViewById(R.id.tab_one_image);
            tab_one_name = itemView.findViewById(R.id.tab_one_name);
            tab_one_jianjie = itemView.findViewById(R.id.tab_one_jianjie);
            tab_one_guanzhu = itemView.findViewById(R.id.tab_one_guanzhu);
        }
    }


    //接口
    public interface OnItem{
        void onGuanLike(int id, boolean isLike);
//        void getdata(int id,int great,int position);
//        void onClick(int position);
    }

    private OnItem onItem;

    //一个公共的方法
    public void setOnItem(OnItem onItem) {
        this.onItem = onItem;
    }
}
