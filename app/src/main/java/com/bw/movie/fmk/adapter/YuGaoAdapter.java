package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.fmk.bean.DianYingYuGaoBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 9:40
 * @Description:
 */
public class YuGaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DianYingYuGaoBean.ResultBean.ShortFilmListBean> data;
    private Context mContext;

    public YuGaoAdapter(List<DianYingYuGaoBean.ResultBean.ShortFilmListBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    //    public YuGaoAdapter(Context mContext) {
//        this.mContext = mContext;
//        //data=new ArrayList<>();
//    }

//       public void setaa(List<DianYingYuGaoBean.ResultBean.ShortFilmListBean> shortFilmList) {
//        if (shortFilmList!=null){
//            data.addAll(shortFilmList);
//        }
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.yugao_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        if (data.get(i).getVideoUrl() != null){
            boolean setUp = holder.yugao_bo.setUp(data.get(i).getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
            if (setUp) {
                holder.yugao_bo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext).load(data.get(i).getImageUrl()).into(holder.yugao_bo.thumbImageView);
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        private JCVideoPlayerStandard yugao_bo;

        public Holder(@NonNull View itemView) {
            super(itemView);
            yugao_bo = itemView.findViewById(R.id.yugao_bo);
        }
    }
}
