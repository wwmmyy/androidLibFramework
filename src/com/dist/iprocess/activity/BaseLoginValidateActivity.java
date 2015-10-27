package com.dist.iprocess.activity;

import xyz.yhsj.yhutils.tools.sp.SharePreferenceUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.fragement.BaseMainActivity;
import com.dist.iprocess.service.BaiduPushPreUtils;
import com.lidroid.xutils.util.DeviceUuidFactory;

public class BaseLoginValidateActivity extends Activity {
    Context mContext = BaseLoginValidateActivity.this;   //上下文Context
    private ProgressDialog dialog;//联网进度条
    private long waitTime = 2000;//双击间隔退出系统时间
    private long touchTime = 0;
    String ALL_deviceid;
    
    private LocationClient mLocationClient;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_login);
        DeviceUuidFactory xxa = new DeviceUuidFactory(this);	//获取设备的 唯一标识码
        ALL_deviceid = xxa.getDeviceUuid().toString();

        DistApp.ALL_deviceNumber=ALL_deviceid;
        
        
////      启动终端定位服务  直接启动
//        new Thread(new Runnable() {            
//            @Override
//            public void run() {
//                // TODO 自动生成的方法存根
//                mLocationClient = ((DistApp)getApplication()).mLocationClient;
//                InitLocation();        
//                mLocationClient.start();
//            }
//        }).start();
        
        
        new Thread(){
        public void run() {
//          启动百度云推送功能
           autoBindBaiduYunTuiSong();
        };
        }.start();
        
        
        // 判断手势密码是否开启 
        
        Boolean soguduIsOpen = SharePreferenceUtil.getBoolean(mContext, "gestureIsOpen", false); 
        if (!soguduIsOpen) {
            Intent intent2 = new Intent();
            intent2.setClass(mContext, BaseLoginActivity.class);
            startActivity(intent2);
           finish();
        } else{                                
            Intent intent2 = new Intent();
            intent2.setClass(mContext, BaseGestureLoginActivity.class);
            startActivity(intent2);
            finish();                               
        }    

    }
    
    
    
    
    
    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
//      option.setLocationMode(tempMode);
        option.setLocationMode(LocationMode.Hight_Accuracy);
//      option.setCoorType(tempcoor);
        option.setCoorType("bd09ll");           
        int span=600*1000;
//      try {
//              span = Integer.valueOf(frequence.getText().toString());
//      } catch (Exception e) {
//              // TODO: handle exception
//      }
        option.setScanSpan(span);
//      option.setIsNeedAddress(checkGeoLocation.isChecked());
        mLocationClient.setLocOption(option);
}
    
    
    
    
    /**
     * 如果没有绑定百度云，则绑定，并记录在属性文件中
     */
    private void autoBindBaiduYunTuiSong()
    {
            if (!BaiduPushPreUtils.isBind(getApplicationContext()))
            {
                    PushManager.startWork(getApplicationContext(),
                                    PushConstants.LOGIN_TYPE_API_KEY,
                                    DistApp.KEY);
                    
//                      百度云推送添加
                        Resources resource = this.getResources();
                        String pkgName = this.getPackageName(); 
            }
    }
    
    
    
//
//    private void checkStart() {
//        if (UtilsTool.checkNet(context) == false) {
//            //判断网络连接是否正常
//            new AlertDialog.Builder(this).setTitle("网络错误").setMessage("网络连接失败，请确认网络连接")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            // System.exit(0);
//                        }
//                    }).show();
//        } else {
//            // 1向服务器发送信息，检测设备状态:
//            getDeviceState();
//
//        }
//    }
//
//    private void getDeviceState() {
//        // TODO 自动生成的方法存根
//        //		l  检测设备状态:
//        //			参数：type=device&action=validate&code=设备编码。
//        //			         返回：0不可用；1,2可用；3禁用；4挂失；5申请中。
//
//        dialog = new ProgressDialog(LoginValidateActivity.this);
//        // 设置进度条风格，风格为圆形，旋转的(ProgressDialog.STYLE_HORIZONTAL长形风格)
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        //	    		// 设置ProgressDialog 标题
//        //	    		progressdialog.setTitle("提示");
//        // 设置ProgressDialog 提示信息
//        dialog.setMessage("正在验证中，请稍等.....");
//        // 设置ProgressDialog 标题图标
//        // m_pDialog.setIcon(R.drawable.img1);
//        // 设置ProgressDialog 的进度条是否不明确
//        dialog.setIndeterminate(false);
//        // 设置ProgressDialog 是否可以按退回按键取消
//        dialog.setCancelable(true);
//        // 设置ProgressDialog 的一个Button
//        dialog.setButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int i)
//
//            {
//                // 点击“确定按钮”取消对话框
//                dialog.cancel();
//            }
//
//        });
//        // 让ProgressDialog显示
//        dialog.show();
//
//        InitDeviceResult initdeviceresult = new InitDeviceResult();
//        initdeviceresult.execute(dialog);
//
//    }
//
// /**
//  *  
//  * @author 
//  *
//  */
//    class InitDeviceResult extends AsyncTask<Object, Void, String> {
//        ProgressDialog dialog;
//        //BaseAdapter adapter;
//        String applicationinstructions;
//
//        @Override
//        protected String doInBackground(Object... params) {
//            dialog = (ProgressDialog) params[0];
//            String url = DistApp.serverAbsolutePath+"/mobile/applist.action";
//
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("userid", "device");
//            map.put("loginName", "validate");
//            map.put("deviceid", ALL_deviceid);
//            String result=null;
//            try {
//                result = UtilsTool.getStringFromServer(DistApp.ALL_PATH, map, "UTF-8");
//            } catch (Exception e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (dialog != null) dialog.dismiss();
//            if (result == null) {
//                //判断服务器返回值是否为null
//                Toast.makeText(context, "连接服务器异常，无法登录", 1).show();
//                return;
//            } else {
//                //对服务器返回值进行解析
//                try {
//                    JSONObject obj = new JSONObject(result);
//                    boolean loginstate = Boolean.parseBoolean(obj.optString("loginstate"));
//                    String username = obj.optString("username");
//                    if(loginstate){
////                      将  username loginstate 传入到loginActivity    Bundle    
//                        
//                      Intent intent2 = new Intent();
//                      intent2.setClass(context, LoginActivity.class);
//                      intent2.putExtra("username",username); 
//                      startActivity(intent2);
//                      
//                  }else{
//                      
//                      // 判断手势密码是否开启
//                      SharedPreferences mSettings = getSharedPreferences(
//                                      "com.android.pedometer_preferences", MODE_WORLD_READABLE);
//                      Boolean soguduIsOpen = mSettings.getBoolean("soguduIsOpen",
//                                      false);
//                      if (!soguduIsOpen) {
//                          Intent intent2 = new Intent();
//                          intent2.setClass(context, LoginActivity.class);
//                          startActivity(intent2);
//                         finish();
//                      } else{                                
//                          Intent intent2 = new Intent();
//                          intent2.setClass(context, Sudoku_LoginActivity.class);
//                          startActivity(intent2);
//                          finish();                               
//                      }                      
//                      
//                  }
//                    
//                    
//                    
//                    
//                    
//                    
//                    
//                    
//                    
//                    
////                    switch (loginstate) {
////                    case 0:// 不可用
////                    case 4:// 4挂失
////                           //删除用户所有终端保存信息
////                        Toast.makeText(context, "此设备已被挂失！，稍后将退出！", 3).show();
//////                        HttpUtils.guaShi();//进入设备挂失处理,删除文件			    		
////                        new Thread() {
////                            public void run() {
////                                try {
////                                    Thread.sleep(5000);
////                                    finish();
////                                    android.os.Process.killProcess(android.os.Process.myPid());
////
////                                } catch (InterruptedException e) {
////                                    // TODO 自动生成的 catch 块
////                                    e.printStackTrace();
////                                }
////                            };
////                        }.start();
////
////                        break;
////                    }
//
//                } catch (JSONException e1) {
//                    // TODO 自动生成的 catch 块
//                    e1.printStackTrace();
//
//                    if (dialog != null) dialog.dismiss();
//                }
//
//            }
//        }
//    }
//
//    
// 
//    
//    
//    /**
//     * 退出系统
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
//            long currentTime = System.currentTimeMillis();
//            if ((currentTime - touchTime) >= waitTime) {
//                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//                touchTime = currentTime;
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
