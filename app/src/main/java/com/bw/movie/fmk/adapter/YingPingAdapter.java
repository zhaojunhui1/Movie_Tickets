package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.bean.DianYingPingLunBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 9:40
 * @Description:
 */
public class YingPingAdapter extends XRecyclerView.Adapter<YingPingAdapter.Holder> {

    private List<DianYingPingLunBean.ResultBean> data;
    private Context mContext;

    public YingPingAdapter(List<DianYingPingLunBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.yingping_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String commentUserName = data.get(i).getCommentUserName();
        holder.yingping_item_name.setText(commentUserName);

        String commentContent = data.get(i).getCommentContent();
        holder.yingping_item_ping.setText(commentContent);

        int greatNum = data.get(i).getGreatNum();
        holder.yingping_item_zan_shu.setText(greatNum+"");

        long commentTime = data.get(i).getCommentTime();
        holder.yingping_item_time.setText(commentTime+"");

        int commentId = data.get(i).getCommentId();
        holder.yingping_item_pinglun_shu.setText(commentId+"");

        String commentHeadPic = data.get(i).getCommentHeadPic();
        holder.yingping_item_image1.setImageURI(Uri.parse(commentHeadPic));

        //点击
        holder.yingping_item_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItem!=null){
                    onItem.onClick(i,data.get(i).getIsGreat()+1);
                    //holder.yingping_item_zan_shu.setText(data.get(i).getIsGreat()+1);

                    onItem.onClick(i,data.get(i).getIsGreat());
                }
            }
        });
    }


    //接口
    public interface OnItem{
        void onClick(int position, int commentId);
    }

    private OnItem onItem;

    //一个公共的方法
    public void setOnItem(OnItem onItem) {
        this.onItem = onItem;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Holder extends XRecyclerView.ViewHolder {

        private TextView yingping_item_name,yingping_item_ping,yingping_item_time,yingping_item_zan_shu,yingping_item_pinglun_shu;
        private SimpleDraweeView yingping_item_image1;
        private ImageView yingping_item_zan;

        public Holder(@NonNull View itemView) {
            super(itemView);
            yingping_item_image1 = itemView.findViewById(R.id.yingping_item_image1);
            yingping_item_name = itemView.findViewById(R.id.yingping_item_name);
            yingping_item_ping = itemView.findViewById(R.id.yingping_item_ping);
            yingping_item_time = itemView.findViewById(R.id.yingping_item_time);
            yingping_item_zan_shu = itemView.findViewById(R.id.yingping_item_zan_shu);
            yingping_item_pinglun_shu = itemView.findViewById(R.id.yingping_item_pinglun_shu);
            yingping_item_zan = itemView.findViewById(R.id.yingping_item_zan);

        }
    }


}
