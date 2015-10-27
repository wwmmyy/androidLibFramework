package com.dist.iprocess.adapter;

import android.graphics.Bitmap.Config;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dist.iprocess.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 适配器
 * Created by lizheng on 14/10/19.
 */
public class BaseViewRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewRecyclerViewAdapter.DemoViewHolder> {
    String[] picUrls;

    
    public static interface OnRecyclerViewListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
    
    
    
    
    public BaseViewRecyclerViewAdapter(String[] picUrls) {
        this.picUrls = picUrls;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 加载数据item的布局，生成VH返回
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_view_recyclerview_item, viewGroup, false);
        return new DemoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder demoViewHolder, int i) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
        .cacheInMemory(true)//
        .cacheOnDisk(true)//
        .bitmapConfig(Config.RGB_565)//
        .build(); 
        demoViewHolder.position = i;
        // 数据绑定
        ImageLoader.getInstance().displayImage(picUrls[i], demoViewHolder.imavPic, options);
        demoViewHolder.tvUrl.setText(picUrls[i]);
        
       
        
        
        
////        另外一种点击item监听事件
//        final int position=i;
//        final String bean = picUrls[i];//list.get(position);  
//        demoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != listener)
//                    listener.onItemClick(position, bean);
//            }
//        });
        
        
        
    }

    
//    private OnItemClickListener listener;
//    /**
//     * 内部接口回调方法
//     */
//    public interface OnItemClickListener {
//        void onItemClick(int position, Object object);
//    } 
//    /**
//     * 设置监听方法
//     *
//     * @param listener
//     */
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
    
    
    
    
    
    
    @Override
    public int getItemCount() {
        // 返回数据有多少条
        if (null == picUrls) {
            return 0;
        }
        return picUrls.length;
    }

//    // 可复用的VH
//    public static 
    class DemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // 大图
        public ImageView imavPic;
        // 图片url
        public TextView tvUrl;
        
        public int position;

        public DemoViewHolder(View itemView) {
            super(itemView);
            imavPic = (ImageView) itemView.findViewById(R.id.imavPic);
            tvUrl = (TextView) itemView.findViewById(R.id.tvUrl);
            
//            rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(null != onRecyclerViewListener){
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    } 
}