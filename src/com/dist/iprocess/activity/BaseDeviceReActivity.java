package com.dist.iprocess.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.util.UtilsTool;
import com.lidroid.xutils.util.MyToast;
public class BaseDeviceReActivity extends Activity implements OnClickListener{
    Context mContext = BaseDeviceReActivity.this;
    String titlename;
    TextView device_re_submit;
    EditText device_re_remark;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //            WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_loginregister);

        mSettings = getSharedPreferences("com.dist.iportal.password",
                android.content.Context.MODE_PRIVATE);

        device_re_submit = (TextView) findViewById(R.id.device_re_submit);
        device_re_remark = (EditText) findViewById(R.id.device_re_remark);
        device_re_submit.setOnClickListener(this);
        device_re_remark.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
        case R.id.device_re_submit:
            sendToServer();
            break;
  
        default:
            break;
        }

    }

    /**
     * @Title: sendToServer
     * @Description: 上传到服务器
     * @param
     * @return void
     * @throws
     */
    public void sendToServer() {
//        final String requestURL = DistApp.serverAbsolutePath + "/mobilevalidate/app-mobileDeviceReg.action";//接口加密后需要采用这种方式注册，
         final String requestURL = DistApp.serverAbsolutePath + "/mobile/app-mobileDeviceReg.action";//接口未加密后需要采用这种方式注册，
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //                    if (file != null) {
                Message msg = new Message();
                msg.what = Loading;
                mHandler.sendMessage(msg);
                try {

                  
                    //请求普通信息
                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("userId", mSettings.getString("userid", ""));//用户登录以后获取用户的userID并保存
                    map.put("remark", device_re_remark.getText().toString());
                    map.put("deviceNumber", DistApp.ALL_deviceNumber);   
                    
//                    TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
//                    map.put("name",  tm.getDeviceSoftwareVersion());
                    map.put("name",device_re_remark.getText().toString());
                    map.put("system","Android");
                    map.put("hardware",  UtilsTool.isPad(mContext)==true?"Pad":"Phone");
                    
                    String totalresult = UtilsTool.getStringFromServer(requestURL, map);
                    Message msg2 = new Message();

                    Log.d("获取到的返回结果是是", totalresult);
                    
                    if (totalresult != null && !totalresult.equals("")) {
                        JSONObject obj = new JSONObject(totalresult);
                        String result=obj.optString("result");
                        
                     
                        
                        if (result != null && !result.equals("")) {
                            msg2.what = END;
                        } else {
                            msg2.what = WRONG;
                        }
                    } else {
                        msg2.what = WRONG;
                    }

                    mHandler.sendMessage(msg2);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }

                //                    }
            }
        });
        t.start();
    }

    ProgressDialog pg;
    private final int Loading = 1;
    private final int END = 2;
    private final int WRONG = 3;

    /**
     * 上传到服务器是加载进度框
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Loading:
                pg = UtilsTool.initProgressDialog(mContext, "正在上传.....");
                pg.show();
                break;
            case END:
                pg.dismiss();
                MyToast.show(getApplicationContext(), "申请成功，等待审核", Toast.LENGTH_LONG);
                
                //2跳转到主页面
                Intent intent1 = new Intent();
                intent1.setClass(mContext, BaseLoginActivity.class);
                startActivity(intent1);
                finish();
                break;

            case WRONG:
                pg.dismiss();
                MyToast.show(getApplicationContext(), "申请失败", Toast.LENGTH_LONG);
                finish();
                break;
            }

            super.handleMessage(msg);
        }
    };

}
