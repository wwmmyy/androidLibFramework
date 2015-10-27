package com.dist.iprocess.service;  

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.lidroid.xutils.util.MyToast;

/**
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * 
 * 返回值中的errorCode，解释如下： 
 *  0 - Success
 *  10001 - Network Problem
 *  30600 - Internal Server Error
 *  30601 - Method Not Allowed 
 *  30602 - Request Params Not Valid
 *  30603 - Authentication Failed 
 *  30604 - Quota Use Up Payment Required 
 *  30605 - Data Required Not Found 
 *  30606 - Request Time Expires Timeout 
 *  30607 - Channel Token Timeout 
 *  30608 - Bind Relation Not Found 
 *  30609 - Bind Number Too Many
 * 
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 * 
 */
public class BaiduPushReceiver extends FrontiaPushMessageReceiver {
    /** TAG to Log */
    public static final String TAG = BaiduPushReceiver.class
            .getSimpleName();

    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     * 
     * @param context
     *            BroadcastReceiver的执行Context
     * @param errorCode
     *            绑定接口返回值，0 - 成功
     * @param appid
     *            应用id。errorCode非0时为null
     * @param userId
     *            应用user id。errorCode非0时为null
     * @param channelId
     *            应用channel id。errorCode非0时为null
     * @param requestId
     *            向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
    @Override
    public void onBind(Context context, int errorCode, String appid,
            String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.e(TAG, responseString);

        // 绑定成功，设置已绑定flag，可以有效的减少不必要的绑定请求
        if (errorCode == 0) {
            BaiduPushPreUtils.bind(context);
        }
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        updateContent(context, responseString);
    }

    /**
     * 接收透传消息的函数。
     * 
     * @param context
     *            上下文
     * @param message
     *            推送的消息
     * @param customContentString
     *            自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message,
            String customContentString) {
        String messageString = "透传消息 message=\"" + message
                + "\" customContentString=" + customContentString;
        Log.e(TAG, messageString);

        // 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context,message,customContentString);
    }

    
    


    /**
     * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
     * 
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
            String description, String customContentString) {
        
        
        String notifyString = "通知点击 title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.e(TAG, notifyString);

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (customJson.isNull("mykey")) {
                    myvalue = customJson.getString("mykey");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, notifyString);
    }

    /**
     * setTags() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags
     *            设置成功的tag
     * @param failTags
     *            设置失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
            List<String> sucessTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " sucessTags=" + sucessTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.e(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, responseString);
    }

    /**
     * delTags() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags
     *            成功删除的tag
     * @param failTags
     *            删除失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
            List<String> sucessTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " sucessTags=" + sucessTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.e(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, responseString);
    }

    /**
     * listTags() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示列举tag成功；非0表示失败。
     * @param tags
     *            当前应用设置的所有tag。
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
            String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.e(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, responseString);
    }

    /**
     * PushManager.stopWork() 的回调函数。
     * 
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.e(TAG, responseString);

        // 解绑定成功，设置未绑定flag，
        if (errorCode == 0) {
            BaiduPushPreUtils.unbind(context);
        }
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        updateContent(context, responseString);
    }

    
    
    private void updateContent(Context context, String message, String customContentString) {
        // TODO Auto-generated method stub
        initNotify(context);
        showNotify(context,message,customContentString); 
    }
    
    
    
    private void updateContent(Context context, String content) {
        Log.e(TAG, "updateContent");
        //String logText = "" + Utils.logStringCache;

//        if (!logText.equals("")) {
//            logText += "\n";
//        }

//        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
//        logText += sDateFormat.format(new Date()) + ": ";
//        logText += content;

        //Utils.logStringCache = logText;
//        Intent intent = new Intent();
//        intent.putExtra("result", content);
//        intent.setClass(context.getApplicationContext(), BaiduMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.getApplicationContext().startActivity(intent);
        
        
        
        
        initNotify(context);
        showNotify(context,"会议通知",content); 
        
    }
    
    
    
    
    /** Notification构造器 */
    NotificationCompat.Builder mBuilder;
    /** 初始化通知栏 */
    private void initNotify(Context context){
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("测试标题")
                            .setContentText("测试内容")
                            .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL,context))
//                          .setNumber(number)//显示数量
                            .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                            .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                            .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                          .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
                             .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                            .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                            //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                            .setSmallIcon(R.drawable.ic_launcher);
    }
    
    /** 显示通知栏 */
    public void showNotify(Context context, String message, String customContentString){//customContentString 当前测试没有值
    	
    	Log.d("获取到的百度推送结果为：：：：", message);
        int notifyId = 100;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            
        try {
            JSONObject  jsontemp=new JSONObject(message);
            
            
            
            if(!jsontemp.optString("meetingId").equals("")){ //说明是用来推送消息的
	            mBuilder.setContentTitle(jsontemp.optString("title"))
	            .setContentText(jsontemp.optString("meetingTime")+jsontemp.optString("meetingPlace"))
	//          .setNumber(number)//显示数量
	            .setTicker(jsontemp.optString("title"));//通知首次出现在通知栏，带上升动画效果的 
	            
	//       判断是否在参会人员之列
	      	  String[] meetinguserids= jsontemp.optString("meetingMmember").split(";");
	      	   if(meetinguserids!=null){
	      		   for(int i=0;i<meetinguserids.length;i++){ 
						if(meetinguserids[i].equals(DistApp.userid)){
				            
//				            //点击的意图ACTION是跳转到Intent
//				            Intent resultIntent = new Intent(context,MeetingDetailActivity_old.class);
//				            resultIntent.putExtra("meetingId", jsontemp.optString("meetingId"));
//				            resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//				            mBuilder.setContentIntent(pendingIntent);
//				            mNotificationManager.notify(notifyId, mBuilder.build());
//	//			          mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
				            break;
						}
	      		   } 
	      	   }  
           }else{//说明是用来挂失设备的
        	   
	        	   String status=jsontemp.optString("status");
	        	   String devicenum=jsontemp.optString("devicenum");
	        	   if(DistApp.ALL_deviceNumber .equals(devicenum)){
	        		   if(status.equals("2")){
	        			   SharedPreferences settings = context.getSharedPreferences( "com.dist.iportal.password",  android.content.Context.MODE_PRIVATE );
	        			   SharedPreferences.Editor editor = settings.edit();
                	                       editor.clear();
                	                       editor.commit();
	        			   
	        			   MyToast.show(context, "该设备已被挂失，稍后将销毁所有数据", Toast.LENGTH_LONG);
	        		   } else if(status.equals("3")){
	        			   MyToast.show(context, "该设备已被禁用,稍后退出系统！！", Toast.LENGTH_LONG);
	        		   }
	        		   
	        		   
	        		   
	        		   
	        		   
	//        	                   退出应用程序
	        		   if(status.equals("2")||status.equals("3")){
	        			  final  Context tempcontext=context; 
	        		       ( new Handler()).postDelayed(new Runnable() {
	        	            @Override
	        	            public void run() {
	             			    //            彻底退出应用程序，经测试，效果很好
				                Intent startMain = new Intent(Intent.ACTION_MAIN);
				                startMain.addCategory(Intent.CATEGORY_HOME);
				                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				                tempcontext.startActivity(startMain);
				                System.exit(0);
	        	            }
	        	        }, 5000); 
        	              
   
        		   }
        		   
        	   }
        	   
        	   Log.d("该设备被远程挂失掉了：：：", message);
           }
            

            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
            

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public void shwoNotify(Context context){
//        //先设定RemoteViews
//        RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.view_custom);
//        //设置对应IMAGEVIEW的ID的资源图片
//        view_custom.setImageViewResource(R.id.custom_icon, R.drawable.icon);
////      view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
//        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
//        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
////      view_custom.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
//        //设置显示
////      view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
////      view_custom.setLong(R.id.tv_custom_time,"setTime", System.currentTimeMillis());//不知道为啥会报错，过会看看日志
//        //设置number
////      NumberFormat num = NumberFormat.getIntegerInstance();
////      view_custom.setTextViewText(R.id.tv_custom_num, num.format(this.number));
//        Builder  mBuilder = new Builder(context);
//        mBuilder.setContent(view_custom)
//                        .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL,context))
//                        .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                        .setTicker("有新资讯")
//                        .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
//                        .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
//                        .setSmallIcon(R.drawable.icon);
////      mNotificationManager.notify(notifyId, mBuilder.build());
//        Notification notify = mBuilder.build();
//        notify.contentView = view_custom;
////      Notification notify = new Notification();
////      notify.icon = R.drawable.icon;
////      notify.contentView = view_custom;
////      notify.contentIntent = getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
//        mNotificationManager.notify(notifyId, notify);
//}

    
    
    
    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性:  
     * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
     * 点击去除： Notification.FLAG_AUTO_CANCEL 
     */
    public PendingIntent getDefalutIntent(int flags,Context context){
            PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
            return pendingIntent;
    }
    
    

}
 