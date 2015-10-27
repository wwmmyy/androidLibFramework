package com.dist.iprocess.activity;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import xyz.yhsj.yhutils.tools.sp.SharePreferenceUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.fragement.BaseMainActivity;
import com.dist.iprocess.fragement.BaseMainActivity0;
import com.dist.iprocess.util.UpdateManager;
import com.dist.iprocess.util.UtilsTool;
import com.lidroid.xutils.util.MyToast;

public class BaseLoginActivity extends Activity implements OnClickListener{
    
    Context mContext=BaseLoginActivity.this;
    private  EditText imap_login_uername;
    private  EditText imap_login_password;
    private  EditText imap_login_IP;
    private  Button imap_login_userlogin;
    private  CheckBox login_save_pwd; 
    private ProgressDialog mdialog; 
    
    UpdateManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        
//      防止键盘自动弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        setContentView(R.layout.base_login);

         
//        Bundle extras = getIntent().getExtras(); 
//        if(extras!=null){
//            username_fromserver = extras.getString("username"); 
//        }
        
  
        initView();   
//        validateDevice(); 
        
        // 检查文件更新
        manager = new UpdateManager(mContext);
        manager.checkUpdateMe();
    }

 
    
    @SuppressLint("NewApi")
    private void initView() {
        // TODO 自动生成的方法存根
         imap_login_uername= (EditText) this.findViewById(R.id.imap_login_uername);
         imap_login_password= (EditText) this.findViewById(R.id.imap_login_password);
         imap_login_userlogin= (Button) this.findViewById(R.id.imap_login_userlogin);
         imap_login_IP= (EditText) this.findViewById(R.id.imap_login_IP);
         login_save_pwd= (CheckBox) this.findViewById(R.id.login_save_pwd); 
         TextView login_by_gesture= (TextView) this.findViewById(R.id.login_by_gesture); 

         
         
         imap_login_uername.setOnClickListener(this);
         imap_login_password.setOnClickListener(this);
         imap_login_userlogin.setOnClickListener(this);
         login_save_pwd.setOnClickListener(this);
         login_by_gesture.setOnClickListener(this);
         
         
         
         
         imap_login_IP.setText(SharePreferenceUtil.getString(mContext,"imap_login_IP", DistApp.serverIP+":"+DistApp.serverPort));
         
         
           
         
         
//         如果是设置保存了密码，则自动注入用户名及密码
         Boolean login_save_pwd_check = SharePreferenceUtil.getBoolean(mContext,"login_save_pwd_check", false);
         login_save_pwd.setChecked(login_save_pwd_check);
         
         if(login_save_pwd_check){ 
             imap_login_password.setText(SharePreferenceUtil.getString(mContext,"password", ""));  }
         imap_login_uername.setText(SharePreferenceUtil.getString(mContext,"loginName", "")); 
         

        
//        imap_login_uername.setText(DistAndroidApp.username);
//         if(!DistAndroidApp.passwd.endsWith("")){
//             imap_login_password.setText(DistAndroidApp.passwd);
//         }
//        TextView login_by_text=(TextView)findViewById(R.id.login_by_text);
//        login_by_text.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO 自动生成的方法存根
//                imap_login_uername.setText(""); 
//                username_fromserver=null;
//            }
//        });
//        
//        
//        if(username_fromserver!=null){
////          将从服务器已登录的用户名赋值到输入框中
//            imap_login_uername.setText(username_fromserver); 
////            使底部切换用户按钮可见登录可见
//            login_by_text.setVisibility(View.VISIBLE);
//            
//        }
        
        
        
        
    }
    
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	manager.closeDialog();
    	 if (dialog != null) dialog.dismiss();
    	super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
        case R.id.imap_login_userlogin:
            Intent temp=new Intent(mContext, BaseMainActivity.class);
//            Intent temp=new Intent(mContext, BaseTestHttpServiceActivity.class);
            startActivity(temp);
            finish();
            if(1==1)return;
            
            if(imap_login_uername.getText().toString().trim().equals("")
//                    ||imap_login_password.getText().toString().trim().equals("")
                    ){
                MyToast.show(mContext, "用户名或密码不能为空", Toast.LENGTH_LONG);
            }else{
                mdialog = UtilsTool.initProgressDialog(mContext,"请稍等.....");
                mdialog.show();
                UserLoginResult userloginresult = new UserLoginResult();
                userloginresult.execute(mdialog, imap_login_uername.getText().toString(), imap_login_password.getText().toString());
             } 
            break;  
            
            
        case R.id.login_by_gesture:
            
//            Intent intent2 = new Intent();
//            intent2.setClass(mContext, Sudoku_LoginActivity.class);
//            startActivity(intent2);
//            finish();  
            
            break;  
            
        default:
            break;
        }
        
    }
    
    

    /**
     * 用户登录结果
     * @author wmy
     * 
     *
     */
    ProgressDialog dialog;
    class UserLoginResult extends AsyncTask<Object, Void, String> {// 在后台初始化加载在线资源界面
       
        //BaseAdapter adapter;
        String muername;
        String mpassword;

        @Override
        protected String doInBackground(Object... params) {
            dialog = (ProgressDialog) params[0];
            muername = (String) params[1];
            mpassword = (String) params[2];
            String result=null;
            
//            由于服务端接口采用了oauth token验证，因此必须获取token注入后才能登陆
 //              String url = DistApp.serverAbsolutePath+"/mobile/app-mobileLogin.action";
                String url =  "http://"+imap_login_IP.getText().toString().trim()+"/"+DistApp.serverName+"/mobile/app-mobileLogin.action";
                Map<String, String> map = new HashMap<String, String>();
//                map.put("type", "user");
//               
                TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
                map.put("deviceName",  tm.getDeviceSoftwareVersion()+".");
//                System.out.println("获取到的设备名称为：：：：："+tm.getDeviceSoftwareVersion());
                map.put("loginName", muername);
                map.put("password", mpassword);
                map.put("deviceNumber", DistApp.ALL_deviceNumber+"");            
                
//              为了记录终端用户的登录状态，login表示用户登录， exit表示用户退出
                map.put("action", "login");
//                map.put("appidentify", "com.dist.iportal");            
                try {
                    result = UtilsTool.getStringFromServer(url, map);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                    return result;
                }
           
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //                  Toast.makeText(context, "连接服务器成功，result:::::::::"+result, 1).show();
            if (dialog != null) dialog.dismiss();
            if (result == null) {
                MyToast.show(mContext, "连接服务器异常，无法提交申请", 1);
                return;
            } else if (result.equals("fail")){
                MyToast.show(mContext, "连接服务器异常", Toast.LENGTH_LONG);
            }else {
                try {
//                    Log.d("登陆获取到的验证返回值为：：：：：",result);
                    JSONObject obj = new JSONObject(result);
//                    int tempresult =Integer.parseInt(obj.optString("result"));
                   
                    int tempresult =-1;
                    if(!TextUtils.isEmpty(obj.optString("result"))){
                        tempresult =Integer.parseInt(obj.optString("result"));
                    }
                    // 返回：0待审核；1正常；2挂失；3禁用；// 登陆成功后，在后台启动服务定期检测设备可用性
                    Intent intent1 = new Intent();

                    switch (tempresult) {
//                    case -1:
//                        break;
                    case 0:
                        Toast.makeText(mContext, "该设备正在审核中，请稍后。。。", 1).show();
//                        imap_login_userlogin.setOnClickListener(null);
                        break;
                    case 1://说明设备状态正常
                        
                        if(obj.optBoolean("state")){//说明用户成功登陆
                            DistApp.username =obj.optString("userName");
                            DistApp.userid = obj.optString("userId");//表示设备的用户id 
                            
//                          将用户名及id保存起来，为以后用户手势登陆做准备  
                            SharePreferenceUtil.setValue(mContext,"imap_login_IP",imap_login_IP.getText().toString().trim());  
                            DistApp.serverAbsolutePath = "http://"+imap_login_IP.getText().toString().trim()+"/"+DistApp.serverName;  // dist 
                            
                            
                            SharePreferenceUtil.setValue(mContext,"username",   DistApp.username);
                            SharePreferenceUtil.setValue(mContext,"password",   mpassword);
                            SharePreferenceUtil.setValue(mContext,"loginName",   imap_login_uername.getText().toString());
                            SharePreferenceUtil.setValue(mContext,"userid", obj.optString("userId"));
                            SharePreferenceUtil.setValue(mContext,"userRole", obj.optString("userRole"));
                            SharePreferenceUtil.setValue(mContext,"userOrganzation", obj.optString("userOrganzation"));
//                             保存密码是否保存勾选状态
                            SharePreferenceUtil.setValue(mContext, "login_save_pwd_check", login_save_pwd.isChecked());    
                            
                             
                            //2跳转到主页面
                            intent1.setClass(mContext, BaseMainActivity.class);
                            startActivity(intent1);
                            finish();
                        }else{
                            Toast.makeText(mContext, "登录名或密码错误，请重新登陆", 1).show();
                            imap_login_uername.setText("");
                            imap_login_password.setText("");
                        }
                        
                        
                        break;
                    case 2://说明该设备已经挂失，需要删除用户的所有信息
                        Toast.makeText(mContext, "该设备已挂失，稍后清除所有信息", 1).show();
                        SharePreferenceUtil.clear(mContext);
                        break;
                    case 3:
                        Toast.makeText(mContext, "该设备已被禁用，请联系管理员", 1).show();
                       imap_login_userlogin.setOnClickListener(null);
                        break;
                    default:
                        //2跳转到主页面
                        intent1.setClass(mContext, BaseDeviceReActivity.class);
                        startActivity(intent1);
                        finish();
//                        Toast.makeText(mContext, "服务器返回值异常，无法成功登陆", 1).show();
                        break;
                    }
                } catch (Exception e) {
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
                    String totalresult = UtilsTool.getInfoFromServer(requestURL, map );
//                    Log.d("获取到的服务器返回验证信息为：", totalresult);
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
                Intent intent1 = new Intent();
                intent1.setClass(mContext, BaseDeviceReActivity.class);
                startActivity(intent1);
                finish();
                break;

            case WRONG:
//               MyToast.show(mContext, "无法连接到服务器", Toast.LENGTH_LONG);
                break;
            }

            super.handleMessage(msg);
        }
    }; 
    
    
}
