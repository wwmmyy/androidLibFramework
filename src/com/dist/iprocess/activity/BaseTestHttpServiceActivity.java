package com.dist.iprocess.activity;
 
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.util.HttpManager;
import com.dist.iprocess.util.HttpRequestVo;
import com.dist.iprocess.util.HttpService;
import com.dist.iprocess.util.HttpService.DataCallback;
import com.dist.iprocess.view.BaseView_LabelView;
import com.lidroid.xutils.util.MyToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class BaseTestHttpServiceActivity extends Activity {
    Context mContext=this;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private String mImageUrl="http://img.shendu.com/forum/201108/23/123108h3dqhpj0bphzs9hp.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_test_httpservice);  
        
        
        
        
        final TextView tv_net_content =(TextView) findViewById(R.id.tv_net_content);
        Button bt_net_content =(Button) findViewById(R.id.bt_net_content);
        bt_net_content.setOnClickListener(new OnClickListener() { 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                HttpRequestVo vo = new HttpRequestVo();
                vo.requestUrl = DistApp.serverAbsolutePath+"/mobileMeeting!getMeetMaterialList.action";
//                "http://58.246.138.178:8000/DistMobile/mobile/app-newsListByUser.action";
                vo.context = mContext;
//              采用get方法传递方式
                HashMap<String, String> map =new HashMap<String, String>();
                map.put("userId", "33"); 
                map.put("meetingId", "4028816f4ffe168501500313b9f000ac"); 
                vo.requestDataMap=map; 
                HttpService test=new HttpService(mContext);
                test.getDataFromServer(HttpManager.POST_MOTHOD, vo,
                        new DataCallback<  Object>(){ 
                            @Override
                            public void onStart() {
                                // TODO Auto-generated method stub
                                Log.d("获取到的返回值为StringonStart：",  "onStart");
                            }

                            @Override
                            public void onFailed() {
                                // TODO Auto-generated method stub
//                                MyToast.show(mContext, "获取返回值失败");
                            }

                            @Override
                            public void processData(Object paramObject, boolean paramBoolean) {
                                // TODO Auto-generated method stub
                                Log.d("获取到的返回值为String：", paramObject+"");
                                
                                tv_net_content.setText(paramObject+"");
                            }

                            @Override
                            public void onFinish() {
                                // TODO Auto-generated method stub
                                Log.d("获取到的返回值为onFinish：",  "onFinish");
                            }
                    
                });
                
            }
        });
        progressBar = (ProgressBar)  findViewById(R.id.loading);
        mImageView = (ImageView) findViewById(R.id.image);
        
        
        final BaseView_LabelView label = new BaseView_LabelView(this);
        label.setText("Kunqu");
        label.setBackgroundColor(0xffE91E63);
        label.setTargetView(mImageView, 12,
                        BaseView_LabelView.Gravity.RIGHT_TOP);
        
        ((Button) findViewById(R.id.bt_image_content)).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                loadingImage();
                 loadingImage2();
            }

            /** 
            * @Title: loadingImage2 
            * @Description: TODO
            * @param    
            * @return void   
            * @throws 
            */
            public void loadingImage2() {
                DisplayImageOptions options = new DisplayImageOptions.Builder()//
                                .cacheInMemory(true)//
                                .cacheOnDisk(true)//
                                .bitmapConfig(Config.RGB_565)//
                                .build();
                ImageLoader.getInstance().displayImage(mImageUrl, mImageView, options);
            }

        });
        
        
        
    }

            private void loadingImage() {
                // TODO Auto-generated method stub
                ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                            progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            String message = null;
                            switch (failReason.getType()) {
                            case IO_ERROR:
                                    message = "下载错误";
                                    break;
                            case DECODING_ERROR:
                                    message = "图片无法显示";
                                    break;
                            case NETWORK_DENIED:
                                    message = "网络有问题，无法下载";
                                    break;
                            case OUT_OF_MEMORY:
                                    message = "图片太大无法显示";
                                    break;
                            case UNKNOWN:
                                    message = "未知的错误";
                                    break;
                            }
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            progressBar.setVisibility(View.GONE); 
                    }
            }); 
            }
}
