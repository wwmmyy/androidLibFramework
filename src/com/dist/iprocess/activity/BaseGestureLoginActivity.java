package com.dist.iprocess.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.yhsj.yhutils.tools.sp.SharePreferenceUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.fragement.BaseMainActivity;
import com.dist.iprocess.util.UtilsTool;
import com.dist.iprocess.view.BaseGestureLockView;
import com.dist.iprocess.view.BaseGestureLockView.OnGestureFinishListener;
import com.lidroid.xutils.util.MyToast;

public class BaseGestureLoginActivity extends Activity { 
        Context mContext=BaseGestureLoginActivity.this;
	private BaseGestureLockView baseGestureLockView;
	private TextView gesture_set_textview;
	private Animation animation;
	  private ProgressDialog mdialog; 
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_gesture_set_main);
		 
		init();
		
		
		 validateDevice();
	}
	
	/**初始化*/
	public void init()
	{
		baseGestureLockView=(BaseGestureLockView)findViewById(R.id.gestureLockView);
		gesture_set_textview=(TextView)findViewById(R.id.gesture_set_textview);
		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		TextView login_by_text=(TextView)findViewById(R.id.login_by_text);
		login_by_text.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO 自动生成的方法存根
                        Intent intent2 = new Intent();
                        intent2.setClass(mContext, BaseLoginActivity.class);
                        startActivity(intent2);
                        finish();                               
                    }
                });
		
//	
                final String handPassword= SharePreferenceUtil.getString(mContext,"handpassword", "");
                final String loginName= SharePreferenceUtil.getString(mContext,"loginName", "");
                final String password= SharePreferenceUtil.getString(mContext,"password", "");
		
		//设置密码
		baseGestureLockView.setKey(handPassword);
		//手势完成后回调
		baseGestureLockView.setOnGestureFinishListener(new OnGestureFinishListener() {
			@Override
			public void OnGestureFinish(boolean success, String key) {
			    baseGestureLockView.clearPassWord();
				if(success)
				{
				    
				           mdialog = UtilsTool.initProgressDialog(mContext,"请稍等.....");
				           mdialog.show();
			                    UserLoginResult userloginresult = new UserLoginResult();
			                    userloginresult.execute(mdialog,loginName,password);   
                                            
				}
				else
				{
					gesture_set_textview.setTextColor(Color.parseColor("#FF2525"));
					gesture_set_textview.setVisibility(View.VISIBLE);
					gesture_set_textview.setText("密码错误！");
					gesture_set_textview.startAnimation(animation);
				}
			}
		});
	}
	 
	
	
    /**
     * 
    * @类名: UserLoginResult 
    * @描述: TODO 
    * @作者: 王明远 
    * @日期: 2015年10月3日 下午8:50:18 
    * @修改人: 
     * @修改时间: 
     * @修改内容:
     * @版本: V1.0
     * @版权:Copyright ©  All rights reserved.
     */
    class UserLoginResult extends AsyncTask<Object, Void, String> {// 在后台初始化加载在线资源界面
        ProgressDialog dialog;
        //BaseAdapter adapter;
        String muername;
        String mpassword;

        @Override
        protected String doInBackground(Object... params) {
            dialog = (ProgressDialog) params[0];
            muername = (String) params[1];
            mpassword = (String) params[2];
            String result = null;
            
            Log.d("得到的用户名和密码为：：：", muername+mpassword);

            //	            由于服务端接口采用了oauth token验证，因此必须获取token注入后才能登陆
//            if (MakeToken.getToken(muername, mpassword)) {
                String url = DistApp.serverAbsolutePath + "/mobile/app-mobileLogin.action";
                Map<String, String> map = new HashMap<String, String>();
                //	            map.put("type", "user");
                map.put("loginName", muername);
                map.put("password", mpassword);
                map.put("deviceNumber", DistApp.ALL_deviceNumber);
                //  为了记录终端用户的登录状态，login表示用户登录， exit表示用户退出
                map.put("action", "login");
//                map.put("appidentify", "com.dist.iportal");

                try {
                    result = UtilsTool.getStringFromServer(url, map);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                    return result;
                }
//            } else {
//                result = "fail";
//            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //                  MyToast.show(context, "连接服务器成功，result:::::::::"+result, 1);
            if (dialog != null) dialog.dismiss();
            if (result == null) {
                MyToast.show(mContext, "连接服务器异常，无法提交申请", 1);
                return;
            } else if (result.equals("fail")){
                MyToast.show(mContext, "连接服务器异常", Toast.LENGTH_LONG);            
            } else {
                try {
                    JSONObject obj = new JSONObject(result);
//	                    int tempresult =Integer.parseInt(obj.optString("result"));
                    int tempresult = -1;
                    if (!TextUtils.isEmpty(obj.optString("result"))) {
                        tempresult = Integer.parseInt(obj.optString("result"));
                    }
                    // 返回：0用户不存在；1用户已禁用；2密码错误；3登陆成功；4设备不匹配 // 登陆成功后，在后台启动服务定期检测设备可用性

                    switch (tempresult) {
                    case 0:
                        MyToast.show(mContext, "该设备正在审核中，请稍后。。。", 1);
                        //	                        imap_login_userlogin.setOnClickListener(null);
                        break;
                    case 1:

                        //	                        if(obj.optBoolean("state")){//说明用户成功登陆
                        //	                            DistApp.username =obj.optString("userName");
                        //	                            DistApp.userid = obj.optString("userId");;//表示设备的用户id 
//                        2跳转到主页面
                        Intent intent1 = new Intent();
                        intent1.setClass(mContext, BaseMainActivity.class);
                        startActivity(intent1);
                        finish();
                        //	                        }else{
                        //	                            MyToast.show(mContext, "登录名或密码错误，请重新登陆", 1);
                        //	                        }

                        break;
                    case 2://说明该设备已经挂失，需要删除用户的所有信息
                        MyToast.show(mContext, "该设备已挂失，稍后清除所有信息", 1); 
                        
                         
                        SharePreferenceUtil.clear(mContext);

                        break;
                    case 3:
                        MyToast.show(mContext, "该设备已被禁用，请联系管理员", 1);
                        break;
                    default:

                        MyToast.show(mContext, "服务器返回值异常，无法成功登陆", 1);
                        break;
                    }
                } catch (JSONException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }

            }

        }
    }	
	
	
	
	
	

	    
	    
	    
	    /**
	     * @Title: sendToServer
	     * @Description:设备状态验证
	     * @param
	     * @return void
	     * @throws
	     */
	    public void validateDevice() {
	        final String requestURL = DistApp.serverAbsolutePath + "/mobilevalidate/app-mobileDeviceValidate.action";

	        Thread t = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                //                    if (file != null) {
	          
	                try {
	                  
	                    //请求普通信息
	                    Map<String, String> map = new HashMap<String, String>();
	                    map.put("deviceNumber", DistApp.ALL_deviceNumber);                       
	                    String totalresult = UtilsTool.getInfoFromServer(requestURL, map);
	                    Message msg2 = new Message();
	                    if (totalresult != null && !totalresult.equals("")) {
	                        JSONObject obj = new JSONObject(totalresult);
	                        String result=obj.optString("result");
	                        if (result != null && result.trim().equals("-1")) {
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

	    private final int END = 2;
	    private final int WRONG = 3;

	    /**
	     * 上传到服务器是加载进度框
	     */
	    private Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	         
	            case END:
	                
	                //2跳转到主页面
//	                Intent intent1 = new Intent();
//	                intent1.setClass(mContext, DeviceReActivity.class);
//	                startActivity(intent1);
	                finish();
	                break;

	            case WRONG:
	               
	                break;
	            }

	            super.handleMessage(msg);
	        }
	    }; 
	    
	
	
}
