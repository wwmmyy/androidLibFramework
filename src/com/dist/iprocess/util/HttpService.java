package com.dist.iprocess.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.dist.iprocess.R;
import com.lidroid.xutils.util.MyToast;
 
public class HttpService {
    
    
    protected Context mContext;
//  protected AmbowApplication app;
//    public HttpManager httpManager;
    
    public HttpService(Context mContext){ 
        this.mContext=mContext; 
    }
    
    /**
     * 类功能描述：回调接口，用于后台处理数据
     * 
     * @author mwqi
     * @version 1.0
     * @param <T>
     *            </p> 修改时间：</br> 修改备注：</br>
     */
    public abstract interface DataCallback<T> {
            public abstract void onStart();
            public abstract void onFailed();

            public abstract void processData(T paramObject, boolean paramBoolean);

            public abstract void onFinish();
    }

    
 
    /**
     * 从服务器上获取数据，并回调处理
     * 
     * @param reqVo
     * @param callBack
     */
    public void getDataFromServer(int requestType, HttpRequestVo reqVo,
                    DataCallback callBack) {
            final BaseHandler handler = new BaseHandler(mContext, callBack);
            HttpManager httpManager = new HttpManager(mContext, new HttpRequestListener() {

                    @Override
                    public void action(int actionCode, Object object) {

                            Message msg = new Message();
                            switch (actionCode) {
                            case HttpRequestListener.EVENT_NOT_NETWORD:
                                    msg.what = HttpRequestListener.EVENT_NOT_NETWORD;
                                    break;

                            case HttpRequestListener.EVENT_NETWORD_EEEOR:
                                    msg.what = HttpRequestListener.EVENT_NETWORD_EEEOR;
                                    break;
                            case HttpRequestListener.EVENT_CLOSE_SOCKET:
                                    msg.what = HttpRequestListener.EVENT_CLOSE_SOCKET;
                                    break;

                            case HttpRequestListener.EVENT_GET_DATA_EEEOR:
                                    msg.what = HttpRequestListener.EVENT_GET_DATA_EEEOR;
                                    msg.obj = null;
                                    break;
                            case HttpRequestListener.EVENT_GET_DATA_SUCCESS:
                                    msg.obj = object;
                                    msg.what = HttpRequestListener.EVENT_GET_DATA_SUCCESS;
                                    break;
                            default:
                                    break;
                            }
                            handler.sendMessage(msg);

                    }
            }, reqVo);
            callBack.onStart();
            if (requestType == HttpManager.GET_MOTHOD) {
                    httpManager.getRequeest();
            } else if (requestType == HttpManager.POST_MOTHOD) {
                    httpManager.postRequest();
            }

    }
    
    
    
    
    
    /**
     * 类功能描述：处理网络数据的显示</br>
     * 
     * @author mwqi
     * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
     */

    class BaseHandler extends Handler {
            private Context context;
            private DataCallback callBack;

            public BaseHandler(Context context, DataCallback callBack
                            ) {
                    this.context = context;
                    this.callBack = callBack;
            }

            public void handleMessage(Message msg) {
                    if (msg.what == HttpRequestListener.EVENT_GET_DATA_SUCCESS) {
                            if (msg.obj == null) {
                                    callBack.onFailed(); 
                                    MyToast.show(mContext, "获取返回值为空");
                            } else {
                                    // 后台处理数据
                                    callBack.processData(msg.obj, true);
                            }
                    } else if (msg.what == HttpRequestListener.EVENT_NOT_NETWORD) {
//                            showNetFailedDialog(); 
//                             CommonUtil.showInfoDialog(context,
//                             getString(R.string.net_error));
                        callBack.onFailed();
                        
                        showNoNetWork(context);
//                        MyToast.show(mContext, "网络没打开,请开启网络");
                    } else if (msg.what == HttpRequestListener.EVENT_NETWORD_EEEOR) {
                            callBack.onFailed();
                            MyToast.show(mContext, "网络连接失败");

                    } else if (msg.what == HttpRequestListener.EVENT_GET_DATA_EEEOR) {
                            callBack.onFailed();
                            MyToast.show(mContext, "获取返回值失败");

                    } else if (msg.what == HttpRequestListener.EVENT_CLOSE_SOCKET) {
                            
                    }
                    callBack.onFinish();
            } 

    }
 
    /**
     * 当判断当前手机没有网络时使用
     * 
     * @param context
     */
    public static void showNoNetWork(final Context context) {
            AlertDialog.Builder builder = new Builder(context);
            builder.setIcon(R.drawable.ic_launcher)//
                            .setTitle("网络设置")//
                            .setMessage("网络没打开，请开启网络").setPositiveButton("设置", new OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                            // 跳转到系统的网络设置界面
//                                            Intent intent = new Intent();
//                                            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                                          
                                            
                                            Intent intent =  new Intent(
                                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                            
                                            context.startActivity(intent);

                                    }
                            }).setNegativeButton("取消 ",null).show();
    }

}
