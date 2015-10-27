package com.dist.iprocess.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.dist.iprocess.app.DistApp;
import com.lidroid.xutils.util.MyToast;

public class MyBrodcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) { 
		
        String action = intent.getAction();  
        if(action.equals(DistApp.ACTION_NAME)){  
//            MyToast.show(context, "该设备已被挂失，稍后将销毁所有数据", Toast.LENGTH_LONG);
           String result= intent.getStringExtra("result");  
           MyToast.show(context, "接收到的信息为："+result, Toast.LENGTH_LONG);
            
            
        }  
    

	}

}
