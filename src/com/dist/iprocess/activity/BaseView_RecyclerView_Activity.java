package com.dist.iprocess.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dist.iprocess.R;
import com.dist.iprocess.adapter.BaseViewRecyclerViewAdapter;
import com.dist.iprocess.adapter.BaseViewRecyclerViewAdapter.OnRecyclerViewListener;
import com.lidroid.xutils.util.MyToast;
 
/**
 * RecyclerView是一个比ListView更灵活的一个控件，以后可以直接抛弃ListView了
* @类名: BaseView_RecyclerView_Activity 
* @描述: TODO 
* @作者: 王明远 
* @日期: 2015年10月22日 下午4:45:29 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public class BaseView_RecyclerView_Activity extends Activity implements OnRecyclerViewListener{

//    @InjectView(R.id.recyclerView)
    private RecyclerView recyclerView;

//    @InjectView(R.id.swipeLayout)
    private SwipeRefreshLayout swipeLayout;

    private BaseViewRecyclerViewAdapter adapter;

    
    String[] urls={"http://b.hiphotos.baidu.com/image/pic/item/b21c8701a18b87d65ee407d7040828381e30fd50.jpg",
            "http://bbs.unpcn.com/showtopic-1014960.aspx",
            "http://f.hiphotos.baidu.com/image/pic/item/8b13632762d0f7036cc18ed60afa513d2697c54d.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/d4628535e5dde711e87f79f8a5efce1b9d16610a.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/902397dda144ad340c15a48ed2a20cf431ad8514.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c3fa37c79f9f2b21193138a11.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/6c224f4a20a4462302e1be689a22720e0cf3d794.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/5243fbf2b2119313e4c7c5be67380cd791238d33.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/3c6d55fbb2fb4316f6c7d57322a4462308f7d3db.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f413402142d77271f95cad1c85e93.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/8718367adab44aedd0c984eab11c8701a18bfb3d.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_view_recyclerview_activity);

        adapter = new BaseViewRecyclerViewAdapter(urls); 
        
      swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipeLayout); 
      recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

////        // 线性布局管理器
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);  
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  
//        // 设置布局管理器
//        recyclerView.setLayoutManager(linearLayoutManager);
        
     // 网格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);
        
        
        
        
//     // 交错网格布局管理器
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
//        // 设置布局管理器
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        
         
        

        recyclerView.setAdapter(adapter); 

        
        swipeLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        // 模拟下拉刷新
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        MyToast.show(getApplicationContext(), "调用了刷新功能");
                    }
                }, 2000);
            }
        });
        
        
        adapter.setOnRecyclerViewListener(this); 
//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            
//            @Override
//            public void onItemClick(int position, Object object) {
//                // TODO Auto-generated method stub
//                MyToast.show(getApplicationContext(), "o点击了"+position);
//            }
//        });
        
        

    }
    @Override
    public void onItemClick(int position) {
        // TODO Auto-generated method stub
        MyToast.show(getApplicationContext(), "点击了"+position);
        
        
        
//        ModelBean bean = new ModelBean();
//        bean.setTitle("这是新添加的");
//        bean.setResId(R.drawable.img5);
//        beanList.add(0, bean);
//       // adapter.notifyDataSetChanged();//更新全部数据
//       // adapter.notifyItemInserted(0);//在
//       // adapter.notifyItemRemoved(0);
//       // adapter.notifyItemChanged(0);
//       // adapter.notifyItemMoved(0,1);
//       // adapter.notifyItemRangeChanged(0,2);
//       // adapter.notifyItemRangeInserted(0,2);
//       // adapter.notifyItemRangeRemoved(0,2);
        
    }
    @Override
    public boolean onItemLongClick(int position) {
        // TODO Auto-generated method stub
        MyToast.show(getApplicationContext(), "长按了"+position); 
        return false;
    }

}