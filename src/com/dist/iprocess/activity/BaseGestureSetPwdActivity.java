package com.dist.iprocess.activity;

import xyz.yhsj.yhutils.tools.sp.SharePreferenceUtil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.view.BaseGestureLockView;
import com.dist.iprocess.view.BaseGestureLockView.OnGestureFinishListener;

public class BaseGestureSetPwdActivity extends Activity {
    Context mContext=BaseGestureSetPwdActivity.this;
	private BaseGestureLockView baseGestureLockView;
//	private TextView gesture_set_textview;
	private Animation animation;
	
	String setpassword=""; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		 
		setContentView(R.layout.base_gesture_set_main);
		init();
	}
	
	/**初始化*/
	public void init()
	{
		baseGestureLockView=(BaseGestureLockView)findViewById(R.id.gestureLockView);
//		gesture_set_textview=(TextView)findViewById(R.id.gesture_set_textview);
		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		
		TextView login_by_text=(TextView)findViewById(R.id.login_by_text);
		login_by_text.setText("重置");
		login_by_text.setTextSize(20);
		login_by_text.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View arg0) {
                        // TODO 自动生成的方法存根
                        setpassword="";
                        baseGestureLockView.clearPassWord();  
                    }
                });
		
		
		TextView save_gesture=(TextView)findViewById(R.id.save_gesture);
		save_gesture.setVisibility(View.VISIBLE);
		save_gesture.setTextSize(20);
		save_gesture.setOnClickListener(new OnClickListener() {                    
                    @Override
                    public void onClick(View arg0) {
                        // TODO 自动生成的方法存根
                      if(setpassword.length()>1){
                          SharePreferenceUtil.setValue(mContext,"handpassword", setpassword); 
                          Toast.makeText(mContext, "设置手势密码成功", Toast.LENGTH_LONG).show();
                          
                          baseGestureLockView.clearPassWord();
                          BaseGestureSetPwdActivity.this.finish();                          
                      }else{
                          setpassword="";
                          baseGestureLockView.clearPassWord();
                          Toast.makeText(mContext, "请先设置手势密码", Toast.LENGTH_LONG).show();
                      }
             
                    }
                });
		
		
//		获取保存的密码
                String password= SharePreferenceUtil.getString(mContext, "handpassword", ""); 
		//设置密码
		baseGestureLockView.setKey(password);
		//手势完成后回调
		baseGestureLockView.setOnGestureFinishListener(new OnGestureFinishListener() {
			@Override
			public void OnGestureFinish(boolean success, String key) {
//			    gestureLockView.clearPassWord();
			    setpassword=key;
			}
		});
	}
}
