package com.dist.iprocess.app;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.dist.iprocess.R;
import com.dist.iprocess.util.UtilsTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
* @类名: DistApp 
* @描述: * 用于共享ApplicationContext和MapConfigJson，在Application中共享的数据，
 * 生命周期和整个应用的生命周期一样   工具类，主要用于存储各种常量
* @作者: 王明远 
* @日期: 2015-4-22 上午11:09:42 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public class DistApp extends FrontiaApplication {
    private static Context sContext;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public static  String   ACTION_NAME = "testBrodcastReceiver";  
 

    @Override
    public void onCreate() {
        super.onCreate();
        DistApp.sContext = getApplicationContext(); 
         
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
        .showImageForEmptyUri(R.drawable.ic_launcher) //
        .showImageOnFail(R.drawable.ic_launcher) //
        .cacheInMemory(true) //
        .cacheOnDisk(true) //
        .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
        .Builder(getApplicationContext())//
        .defaultDisplayImageOptions(defaultOptions)//
        .discCacheSize(50 * 1024 * 1024)//
        .discCacheFileCount(100)// 缓存一百张图片
        .writeDebugLogs()//
        .build();//
        ImageLoader.getInstance().init(config);
        
//        添加地图定位功能
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        
    }

    /**
     * 返回ApplicationContext。getApplicationContext()获得的Context是单例，因此这里也是单例
     * @return getApplicationContext()获得的Context
     */
    public static Context getAppContext() {
        return DistApp.sContext;
    }

 

    //  记录
    public static  String latitude = "";
    public static  String longitude = "";  
    public static  String Radius = "";  
    
    
    
   /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

            @Override
            public void onReceiveLocation(BDLocation location) {
//                    //Receive Location 
//                    StringBuffer sb = new StringBuffer(256);
//                    sb.append("time : ");
//                    sb.append(location.getTime());
//                    sb.append("\nerror code : ");
//                    sb.append(location.getLocType());
//                    sb.append("\nlatitude : ");
//                    sb.append(location.getLatitude());
//                    sb.append("\nlontitude : ");
//                    sb.append(location.getLongitude());
//                    sb.append("\nradius : ");
//                    sb.append(location.getRadius());
//                    if (location.getLocType() == BDLocation.TypeGpsLocation){
//                            sb.append("\nspeed : ");
//                            sb.append(location.getSpeed());
//                            sb.append("\nsatellite : ");
//                            sb.append(location.getSatelliteNumber());
//                            sb.append("\ndirection : ");
//                            sb.append("\naddr : ");
//                            sb.append(location.getAddrStr());
//                            sb.append(location.getDirection());
//                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//                            sb.append("\naddr : ");
//                            sb.append(location.getAddrStr());
//                            //运营商信息
//                            sb.append("\noperationers : ");
//                            sb.append(location.getOperators());
//                    }
//                    Log.i("获取到的定位信息为：：：", location.getLatitude()+"：：："+location.getLongitude());
////                    UtilsTool.saveErrorFile(sb.toString(), "获取到的坐标为11.txt");

                    
                latitude=location.getLatitude()+"";
                longitude =location.getLongitude()+"";
                Radius =location.getRadius()+"";

                mobileLocationLog();

                
            } 
    }
    
    
    
    
    

    /** 
    * @Title: mobileLocationLog 
    * @Description: 将终端的位置坐标上传到服务器
    * @param    
    * @return void   
    * @throws 
    */
    public void mobileLocationLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                String url = DistApp.serverAbsolutePath+"/mobile/app-mobileLog.action";
                Map<String, String> map = new HashMap<String, String>();
                map.put("deviceNumber", DistApp.ALL_deviceNumber);
                map.put("action", "mobileLocation");
//              把设备的坐标也传过去
                map.put("latitude", DistApp.latitude);
                map.put("longitude", DistApp.longitude);
                map.put("radius", DistApp.Radius);
                map.put("userId", (userid==null||userid.equals(""))?"无信息":userid);//用户登录以后获取用户的userID并保存
                try {
//                   退出时向服务器通报
//                    Log.d("获取到的返回结果为 ",
                            UtilsTool.getStringFromServer(url, map );
//                            );
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }                   
            }
        }).start();
    }
     
     
  

    
    public static  String username = "";
    public static  String passwd = "";
    public static  String userid = "";//表示设备的用户id
    public static String ALL_deviceNumber = "";//记录设备序列号
    
    

    public static final String ALL_CachePathDir = "/mnt/sdcard/Browsefile/";//下载文件的保存路径

 
//    public static String serverPort = "8080";  
//    public static String serverIP = "192.168.1.105"; 
//    public static String serverIP = "192.168.1.104";  
    public static String serverIP = "58.246.138.178";  
    public static String serverPort = "8000"; 
    public static String serverName = "DistMobile";  
    
    
//    新闻通知applist服务端地址
    public static String serverAbsolutePath = "http://"+serverIP+":"+serverPort+"/"+serverName;  // dist 
    public static String SDPATH=Environment.getExternalStorageDirectory()+"/iportal/";
  
    
    public static final String ALL_CachePathDirTemp = "/mnt/sdcard/tempdownload/";//下载文件的暂存路径
    
//    由于接口采用的加密措施，因此访问服务时需要验证
    public static String access_token = "0"; 
    public static String refresh_token = ""; 
    
//    百度云推送api_key
//    public static final String KEY ="25NbZEymeidjABIIQ60kWCBl";
    public static final String KEY ="fa4maolOXCNDtMBQoga9hSDn";


}
