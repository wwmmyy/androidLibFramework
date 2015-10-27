package com.dist.iprocess.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.dist.iprocess.app.DistApp;
/**
 *                          获取终端位置定位服务 ， 暂时没用到
* @类名: LocationService 
* @描述: TODO 
* @作者: 王明远 
* @日期: 2015-6-5 下午4:29:01 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public class BaiduLocationService extends Service {
    private LocationClient mLocationClient;

    
    
    @Override
    public void onCreate() {
        // TODO 自动生成的方法存根
        super.onCreate();
        mLocationClient = ((DistApp)getApplication()).mLocationClient;
        InitLocation();        
        mLocationClient.start();
        
        Log.i("地图定位已经启动", "地图定位已经启动");
        
        
    }
    
    
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO 自动生成的方法存根
        mLocationClient.stop();
        super.onDestroy();
    }
    


    private void InitLocation(){
            LocationClientOption option = new LocationClientOption();
//          option.setLocationMode(tempMode);
            option.setLocationMode(LocationMode.Hight_Accuracy);
//          option.setCoorType(tempcoor);
            option.setCoorType("bd09ll");           
//            int span=120*1000;//两分钟定位一次
            int span=120*1000;//两分钟定位一次
//          try {
//                  span = Integer.valueOf(frequence.getText().toString());
//          } catch (Exception e) {
//                  // TODO: handle exception
//          }
            option.setScanSpan(span);
//          option.setIsNeedAddress(checkGeoLocation.isChecked());
            mLocationClient.setLocOption(option);
    }
    
    
    
}
