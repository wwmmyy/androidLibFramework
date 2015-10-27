package com.dist.iprocess.test;

import xyz.yhsj.yhutils.tools.logutils.LogUtils;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dist.iprocess.R;
import com.dist.iprocess.activity.BaseView_CalendarActivity;
import com.dist.iprocess.activity.BaseView_RecyclerView_Activity;
import com.dist.iprocess.app.DistApp;
import com.lidroid.xutils.util.MyToast;

public class MyServiceTestActivity extends Activity implements OnClickListener {

    Context mContext = MyServiceTestActivity.this;
    private ServiceConnection sc=new MyServiceConnection();
    Myservice myservice = null;
    private boolean isBind;
    public static final String TAG = "MyTestActivity";
    Intent mIntent = null;

//    //    2）Using a Messenger 
//    //      Messenger，在此可以理解成”信使“，通过Messenger方式返回Binder对象可以不用考虑Clinet - Service是否属于同一个进程的问题，
//    //      并且，可以实现Client - Service之间的双向通信。极大方便了此类业务需求的实现。
//    //      局限：不支持严格意义上的多线程并发处理，实际上是以队列去处理
//    private Messenger mServerMessenger;
//    private Handler mClientHandler = new MyClientHandler();
//    private Messenger mClientMessenger = new Messenger(mClientHandler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_conpotents_test);

        mIntent = new Intent(MyServiceTestActivity.this, Myservice.class);

        //注册广播
        registerBoradcastReceiver();



        initBotton();
    }
 
    
    private void initBotton() {
        // TODO Auto-generated method stub
        ((Button) findViewById(R.id.test_brodcast)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_start_service)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_bind_service)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_stop_service)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_unbind_service)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_send_messageto_service)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_calendar)).setOnClickListener(this);
        ((Button) findViewById(R.id.test_recyclerview)).setOnClickListener(this);
        
        
        
    }

    //注册广播      
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(DistApp.ACTION_NAME);
        registerReceiver(new MyBrodcastReceiver(), myIntentFilter);
    }
    
    
    
    
    
    
    private class MyServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            Log.w(TAG, "in MyServiceConnection onServiceConnected");
            
//            mServerMessenger = new Messenger(binder);
            
            
            //调用bindService方法启动服务时候，如果服务需要与activity交互，
            //则通过onBind方法返回IBinder并返回当前本地服务
            myservice = ((Myservice.SimpleBinder) binder).getService();
            //这里可以提示用户,或者调用服务的某些方法
            MyToast.show(mContext, " 获取到的返回值为：" + myservice.sBinder.add(10, 20));

    
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myservice = null;
            //这里可以提示用户   
            //                当前不知为什么，此处代码始终没有调用
            LogUtils.i("Myservice  onServiceDisconnected" + componentName);
            isBind = false;
            MyToast.show(mContext, "onServiceDisconnected" + componentName);
        }
    };

//    /**
//     * 用来接收从service传过来的消息
//     * 
//     * @类名: MyClientHandler
//     * @描述: TODO
//     * @作者: 王明远
//     * @日期: 2015年10月21日 下午2:49:03
//     * @修改人:
//     * @修改时间:
//     * @修改内容:
//     * @版本: V1.0
//     * @版权:Copyright © All rights reserved.
//     */
//    private class MyClientHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == Myservice.MSG_FROM_SERVER_TO_CLIENT) {
//                Log.w(TAG, "接收到来自service的信息，reveive msg from server");
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.test_brodcast:
            Intent intent = new Intent(DistApp.ACTION_NAME);
            intent.putExtra("result", "这是向广播发送额消息XXX");
            //发送广播  
            mContext.sendBroadcast(intent);
            break;
        case R.id.test_start_service:
            //			第一种方式启动服务
            startService(new Intent(mContext, Myservice.class));
            isBind = true;
            break;
        case R.id.test_bind_service:
            //			采用绑定的方式启动服务
            bindService(mIntent, sc, Context.BIND_AUTO_CREATE);
            isBind = true;
            break;
        case R.id.test_stop_service:
            stopService(mIntent);
            isBind = false;
            break;
        case R.id.test_unbind_service:
            if (isBind) {
                unbindService(sc);
                isBind = false;
            }

            break;
        //    向service端发送消息
        case R.id.test_send_messageto_service:
//            if (isBind) {
//                Message msg = Message.obtain(null, Myservice.MSG_FROM_CLIENT_TO_SERVER, 0, 0);
//                // 通过replyTo把client端的Messenger(信使)传递给service
//                msg.replyTo = mClientMessenger;
//                try {
//                    mServerMessenger.send(msg);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }

            break;
            
        case R.id.test_calendar:
            Intent  temp=new Intent(mContext, BaseView_CalendarActivity.class);
            startActivity(temp);
            
            break;
        case  R.id.test_recyclerview:    
            Intent  temp2=new Intent(mContext, BaseView_RecyclerView_Activity.class);
            startActivity(temp2);
            break;
        default:
            break;
        }
    }

}
