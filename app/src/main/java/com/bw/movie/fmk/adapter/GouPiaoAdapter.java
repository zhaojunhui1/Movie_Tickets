package com.bw.movie.fmk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fmk.bean.GouPiaoBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/15 9:40
 * @Description:
 */
public class GouPiaoAdapter extends XRecyclerView.Adapter<GouPiaoAdapter.Holder> {

    private List<GouPiaoBean.ResultBean> data;
    private Context mContext;

    public GouPiaoAdapter(List<GouPiaoBean.ResultBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goupiao_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String name = data.get(i).getName();
        holder.gou_item_name.setText(name);

        String address = data.get(i).getAddress();
        holder.gou_item_dizhi.setText(address);

        String logo = data.get(i).getLogo();
        holder.gou_item_image.setImageURI(Uri.parse(logo));

        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItem!=null){
                    onItem.onClick(i,data.get(i).getId()+"",data.get(i).getName(),data.get(i).getAddress());
                }
            }
        });
    }

    //接口
    public interface OnItem{
        void onClick(int position, String movieId, String gou_item_name, String gou_item_dizhi);
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

        private TextView gou_item_name,gou_item_dizhi,gou_item_mi;
        private SimpleDraweeView gou_item_image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            gou_item_image = itemView.findViewById(R.id.gou_item_image);
            gou_item_name = itemView.findViewById(R.id.gou_item_name);
            gou_item_dizhi = itemView.findViewById(R.id.gou_item_dizhi);
            gou_item_mi = itemView.findViewById(R.id.gou_item_mi);
        }
    }
}
