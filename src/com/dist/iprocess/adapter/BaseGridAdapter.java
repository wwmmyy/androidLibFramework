package com.dist.iprocess.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dist.iprocess.R;
import com.dist.iprocess.entity.Basematerial;
import com.dist.iprocess.util.MaterialTypePic;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class BaseGridAdapter extends BaseAdapter {
	private Context mContext;
	   private List<Basematerial> mMeetingmaterialList;
           private LayoutInflater mLayoutInflater;
//           private  in.srain.cube.image.ImageLoader imageLoader;
//	public String[] img_text = { "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
//                                        "当面付", "亲密付", "机票","当面付", "亲密付" };
//	public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
//			R.drawable.app_phonecharge, R.drawable.app_creditcard,
//			R.drawable.app_movie, R.drawable.app_lottery,
//			R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane,
//                        R.drawable.app_facepay, R.drawable.app_close};

           
           MaterialTypePic   temppictype;
	public BaseGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		temppictype=new MaterialTypePic(mContext);
	}

	public BaseGridAdapter(Context context, ArrayList<Basematerial> Meetingmateriallist) {
        // TODO 自动生成的构造函数存根
	           mContext = context;
	                mMeetingmaterialList = Meetingmateriallist;
//	                if (mLayoutInflater == null) {
//	                    mLayoutInflater = (LayoutInflater) context
//	                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	                }
	                
	                
//	                imageLoader = ImageLoaderFactory.create(mContext);
    }

	
	   

	
	
	
    @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMeetingmaterialList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.basse_grid_item, parent, false);
		}
//		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
//		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
//		View  update= BaseViewHolder.get(convertView, R.id.my_plan_isUpdate);
		
		
		
		
		TextView tv = (TextView) convertView.findViewById( R.id.tv_item);
                ImageView iv = (ImageView) convertView.findViewById( R.id.iv_item);
                TextView  update= (TextView) convertView.findViewById( R.id.my_plan_isUpdate);
		 
		
//		iv.setBackgroundResource(imgs[position]);
//		tv.setText(img_text[position]);
		
	      Basematerial information = null;
	      if (mMeetingmaterialList != null && mMeetingmaterialList.size() > 0) {
	          information = mMeetingmaterialList.get(position);
	          tv.setText(information.getName());
	          
//	          if(information.getNewsNum()>0){
//	              update.setVisibility(View.VISIBLE);  
//	              update.setText(information.getNewsNum()+"");
//	          }else{
//	              update.setVisibility(View.GONE);
//	          }
//	          
	          
	          
	          
	          
//	          iv.setBackgroundResource(information.getDrwableId());
	          temppictype=new MaterialTypePic(mContext);
	          if(temppictype.getCategoryFromPath(information.getName())!=null){
	              iv.setImageBitmap(temppictype.getCategoryFromPath(information.getName())) ;
	          } 
	 
	          
//	          String tempIcon = information.getIcon();
//	          if(!TextUtils.isEmpty(tempIcon)){  
//	              iv.loadImage(imageLoader, DistApp.appIconPath+"/"+tempIcon.trim());// 设为缓存图片   
//	          }
	          
	          
	          
//	          RelativeLayout grid_item = (RelativeLayout) convertView.findViewById( R.id.grid_item);
//	              grid_item.setBackgroundColor(mContext.getResources().getColor(information.getColorBack())); 
	              
	              
	      }
	  
		return convertView;
	}
	
	
	
	
	
	
	
	
//	    @Override
//	    public View getView(int position, View convertView, ViewGroup parent) {
//	        // TODO Auto-generated method stub
//	        ListViewHolder viewHolder = null;
//	        if (convertView == null) {
//	            convertView = mLayoutInflater.inflate(R.layout.my_plan_item, null);
//	            viewHolder = getListViewHolder(convertView);
//	            convertView.setTag(viewHolder);
//	        } else {
//	            viewHolder = (ListViewHolder) convertView.getTag();
//	        }
	//
//	        setContentView(viewHolder, position);
	//
//	        return convertView;
//	    }
	//	
//	    private void setContentView(ListViewHolder viewHolder, int position) {
//      // TODO Auto-generated method stub
//      Meetingmaterial information = null;
//      if (mMeetingmaterialList != null && mMeetingmaterialList.size() > 0) {
//          information = mMeetingmaterialList.get(position);
//          viewHolder.mnameView.setText(information.getName());
//          viewHolder.maliasView.setText(information.getAlias());
//          
//          //                        if(information.getImage().size()>0){
//          ////                          如果配置文件里面有图片地址，则显示出来
//          if(information.getIsUpdate()){
//              viewHolder.isUpdateView.setVisibility(View.VISIBLE);  
//          }
//          
//          
//          String tempIcon = information.getIcon();
//          
//          if(!TextUtils.isEmpty(tempIcon)){
////              mImageLoader.categoryloadImage(tempIcon.trim(), this, viewHolder);
//              viewHolder.miconView.loadImage(imageLoader, tempIcon.trim());// 设为缓存图片  
//              
//          }
//      }
//  }
//
//  private ListViewHolder getListViewHolder(View convertView) {
//      ListViewHolder holder = new ListViewHolder();
//      holder.mnameView = (TextView) convertView.findViewById(R.id.my_plan_name);
//      holder.maliasView = (TextView) convertView.findViewById(R.id.my_plan_alias);
//      holder.isUpdateView = (ImageView) convertView.findViewById(R.id.my_plan_isUpdate);     
//      holder.miconView = (CubeImageView) convertView.findViewById(R.id.my_plan_icon);  
//
//      return holder;
//  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
