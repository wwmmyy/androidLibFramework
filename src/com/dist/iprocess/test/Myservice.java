package com.dist.iprocess.test;
 

import xyz.yhsj.yhutils.tools.logutils.LogUtils;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class Myservice extends Service {
        public static final String TAG = "Myservice";
	public SimpleBinder sBinder;
	
//	private Messenger mClientMessenger;
//	private Messenger mServerMessenger = new Messenger(new ServerHandler());
	
	     public static final int MSG_FROM_CLIENT_TO_SERVER = 1;
	   public static final int MSG_FROM_SERVER_TO_CLIENT = 2;

	@Override
	public void onCreate() {
		super.onCreate();
		// 创建 SimpleBinder
		LogUtils.i("Myservice  onCreate");
		sBinder = new SimpleBinder();
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 返回 SimpleBinder 对象
		LogUtils.i("Myservice  onBind");
		return sBinder; 
//		return mServerMessenger.getBinder();
	}

	
  @Override 
    public void onStart(Intent intent, int startId) { 
	  LogUtils.i("Myservice  onStart"); 
            super.onStart(intent, startId); 
    } 

    @Override 
    public int onStartCommand(Intent intent, int flags, int startId) { 
    	LogUtils.i("Myservice  onStartCommand"); 
        return START_STICKY;
    }

    
    
    @Override 
    public void onDestroy() { 
    	LogUtils.i("Myservice onDestroy"); 
            super.onDestroy(); 
    } 

	
	
	
//    class ServerHandler extends Handler {
//              @Override
//                 public void handleMessage(Message msg) {
//                     Log.w(TAG, "thread name:" + Thread.currentThread().getName());
//                   switch (msg.what) {
//                  case MSG_FROM_CLIENT_TO_SERVER:
//                     Log.w(TAG, "receive msg from client");
//                   mClientMessenger = msg.replyTo;
//    
//                     // service发送消息给client
//                        Message toClientMsg = Message.obtain(null, MSG_FROM_SERVER_TO_CLIENT);
//                       try {
//                            Log.w(TAG, "server begin send msg to client");
//                             mClientMessenger.send(toClientMsg);
//                      } catch (RemoteException e) {
//                           e.printStackTrace();
//                        }
//                        break;
//                  default:
//                        super.handleMessage(msg);
//                     }
//                 }
//           }	
	
	
	
	
	
	
	
	/**
	 * 在 Local Service 中我们直接继承 Binder 而不是 IBinder,因为 Binder 实现了 IBinder
	 * 接口，这样我们可以少做很多工作。
	 * 
	 * @author newcj
	 */
	public class SimpleBinder extends Binder {
		/**
		 * 获取 Service 实例
		 * 
		 * @return
		 */
		public Myservice getService() {
			LogUtils.i("Myservice  getService");
			return Myservice.this;
		}

		public int add(int a, int b) {
			LogUtils.i("Myservice  add");
			return a + b;
		}

	}

}
