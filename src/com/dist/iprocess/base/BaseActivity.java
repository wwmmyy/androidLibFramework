package com.dist.iprocess.base;
 
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dist.iprocess.R;
import com.dist.iprocess.util.UtilsTool;
/**
 * 具有top的dactivity基类
* @类名: BaseTopActivity 
* @描述: TODO 
* @作者: 王明远 
* @日期: 2015年10月3日 下午5:02:24 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */ 
public abstract class BaseActivity extends Activity {
    TextView base_top_back,base_top_title,base_top_right_tx;
    ImageView base_top_right_im;
    RelativeLayout base_top;
    Context mContext=this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);  
        //加载layout
        setContentView(mainViewId());
        //初始化
        initView();
        //其他
        initTopView(); 
    }



    protected void initTopView() {
        // TODO 自动生成的方法存根
        base_top= findViewId(R.id.base_top); 
        base_top_back = findViewId(R.id.base_top_back); 
        base_top_title = findViewId(R.id.base_top_title); 
        
        base_top_right_tx= findViewId(R.id.base_top_right_tx); 
        base_top_right_im= findViewId(R.id.base_top_right_im);   
        base_top_back.setOnClickListener(new topOnclickListener()); 
        
        dealTitle(base_top,base_top_back,base_top_title,base_top_right_tx,base_top_right_im);

    }

    
    
    public class topOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO 自动生成的方法存根
            switch (v.getId()) {
            case R.id.base_top_back: 
//                UtilsTool.hideSoftKeybord(BaseTopActivity.this);
                BaseActivity.this.finish();
                break;                
            default:
                break;
            }
        }

    }
    protected abstract int mainViewId(); 
    protected abstract void initView(); 
    
//    public abstract String setTopTitleName();
    public abstract void dealTitle(RelativeLayout base_top2, TextView title, TextView pop_right, TextView base_top_right_tx2, ImageView base_top_right_im2);
    
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewId(int viewId) {
        return (T) findViewById(viewId);
    }

}
