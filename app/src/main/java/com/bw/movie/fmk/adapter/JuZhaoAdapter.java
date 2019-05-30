package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 9:40
 * @Description:
 */
public class JuZhaoAdapter extends RecyclerView.Adapter<JuZhaoAdapter.Holder> {

    private List<String> data;
    private Context mContext;


    public JuZhaoAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.juzhao_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

            String[] split = data.get(i).split(",");

            holder.juzhao_item_image1.setImageURI(split[0]);
            holder.juzhao_item_image2.setImageURI(split[0]);
            holder.juzhao_item_image3.setImageURI(split[0]);
            holder.juzhao_item_image4.setImageURI(split[0]);

            Log.e("tab","posterList=="+ data);
    }



    @Override
    public int getItemCount() {
        return data.size();
    }



    public class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView juzhao_item_image1;
        private SimpleDraweeView juzhao_item_image2;
        private SimpleDraweeView juzhao_item_image3;
        private SimpleDraweeView juzhao_item_image4;

        public Holder(@NonNull View itemView) {
            super(itemView);
            juzhao_item_image1 = itemView.findViewById(R.id.juzhao_item_image1);
            juzhao_item_image2 = itemView.findViewById(R.id.juzhao_item_image2);
            juzhao_item_image3 = itemView.findViewById(R.id.juzhao_item_image3);
            juzhao_item_image4 = itemView.findViewById(R.id.juzhao_item_image4);
        }
    }
}
