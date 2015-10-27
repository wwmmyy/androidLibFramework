package com.dist.iprocess.fragement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.adapter.BaseGridAdapter;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.base.BaseFragment;
import com.dist.iprocess.entity.Basematerial;
import com.dist.iprocess.util.DownLoadFileFromServer;
import com.dist.iprocess.util.UtilsTool;
import com.dist.iprocess.view.BaseGridView;
import com.dist.iprocess.view.XListView;
import com.dist.iprocess.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.adapter.CommonAdapter;
import com.lidroid.xutils.adapter.ViewHolder;
import com.lidroid.xutils.util.MyToast;

public class BaseGridFragment extends BaseFragment implements OnItemClickListener,
        OnPageChangeListener, IXListViewListener {

    private static final String TAG = "MyPlanFragment";
    private Activity mActivity; 
    private BaseGridView mGridView;
    private LayoutInflater mInflater;
    private View mHeaderView;
    private Handler mHandler;
    private BaseGridAdapter mAdapter;
    private ArrayList<Basematerial> Meetingmateriallist;//记录当前显示的消息 
    private ArrayList<Basematerial> total_Meetingmateriallist = new ArrayList<Basematerial>();;//记录所有的最新消息 
    private int refreshCnt = 0;//表示已经下拉的次数
    private boolean pullstate = false;//记录是否已经下拉到最后一条 
    private LinearLayout news_loading; //第一次进入时显示的加载进度框
    private LinearLayout daiban_loading_error;//加载失败时显示刷新  
    private XListView mListView;
    private String meetingId ; 
 
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_category, container, false);
        View view = inflater.inflate(R.layout.base_fragment_xlv_home, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
//        initTopPic(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) { 
        
        if (mInflater == null) {
            mInflater = (LayoutInflater) mActivity
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        mHeaderView = mInflater.inflate(R.layout.base_fragment_grid, null);
        //          初始化加载消息数据，
        Meetingmateriallist = new ArrayList<Basematerial>();
        mGridView = (BaseGridView) mHeaderView.findViewById(R.id.myplan_gridview);
        mAdapter = new BaseGridAdapter(mActivity, Meetingmateriallist);
//      在没有加载完成时在页面上显示的加载进度圈
       news_loading = (LinearLayout) mHeaderView.findViewById(R.id.news_loading_progressbar);
       daiban_loading_error = (LinearLayout) mHeaderView.findViewById(R.id.daiban_loading_error);
                
       mGridView.setAdapter(mAdapter);
       mGridView.setOnItemClickListener(this); 
        
        mListView = (XListView) view.findViewById(R.id.lv_category);
        mListView.setPullLoadEnable(true); 
        mListView.addHeaderView(mHeaderView);
        mListView.setXListViewListener(this);
//        不设置的话头部view不显示  这里只是让其显示头部刷新，不放值进去 
        mListView.setAdapter( new CommonAdapter<Basematerial>(
        mActivity, new ArrayList<Basematerial>(), R.layout.basse_grid_item) {
            @Override
            public void convert(ViewHolder helper, Basematerial item)
            { }  });
        
        
//        使下拉翻页不可见
        mListView.getmFooterView().setVisibility(View.INVISIBLE);
         
        mHandler = new Handler(); 
//        onLoadNewMessage(); 
        Meetingmateriallist=getGwListinfo();
        daiban_loading_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO 自动生成的方法存根
                daiban_loading_error.setVisibility(View.GONE);
                news_loading.setVisibility(View.VISIBLE);
                onLoadNewMessage();
            }
        });
        onLoadNewMessage();
    }

    /**
     * @Title: onLoadNewMessage
     * @Description: 刚进进入时加载最新的消息
     * @param
     * @return void
     * @throws
     */
    private void onLoadNewMessage() {
        // TODO 自动生成的方法存根
        new Thread(new Runnable() {

            String url = DistApp.serverAbsolutePath+"/mobileMeeting!getMeetMaterialList.action";//根据用户id获取应用程序的应用列表信息 
            @Override
            public void run() { 
                
                Map<String, String> map = new HashMap<String, String>();
//                map.put("meetingId", meetingId); 
                map.put("meetingId", "2c9c22814fd50852014fd50c40320006"); 
                
                try {
                    String totalresult = UtilsTool.getStringFromServer(url, map );
//                   暂时不通过服务器，直接从assert中获取
//                    String totalresult =UtilsTool.getInfoFromAsserts(mActivity, "gongwen.txt");
                    
                    
                    System.out.println("获取到的json结果为："+totalresult);
                    if (null != totalresult  &&  !totalresult.equals("")) {
//                        JSONObject obj = new JSONObject(totalresult);
//                        String result = obj.optString("result");
//                        String state = obj.optString("state");
//                        if (!result.equals("") && null != result) {
//                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Basematerial>>() {
                            }.getType();
                            Meetingmateriallist.clear();
                            total_Meetingmateriallist.clear();
//                            total_Meetingmateriallist = gson.fromJson(result, type);
                            Gson gson1 = new GsonBuilder().create();
//                            .registerTypeAdapter(java.util.Date.class,	
//                                    new UtilDateDeserializer())
//                            .setDateFormat(DateFormat.LONG).create();
                            total_Meetingmateriallist = gson1.fromJson(totalresult, type);  
                           // Log.d("获取到的Meetingmateriallist为：",totalresult+ "");
                            if (total_Meetingmateriallist.size() == 0) {
                                Message msg = new Message();
                                msg.what = NO_NEWSMESSAGE; //(值随意定义，和handlemessage 匹配就可以)
                                mtotalHandler.sendMessage(msg);
                            } else {
                                
                                Meetingmateriallist = total_Meetingmateriallist;
                                Message msg = new Message();
                                msg.what = DOWNLOAD_NEWSMESSAGE; //(值随意定义，和handlemessage 匹配就可以)
//                                msg.obj = totalresult;//(传递的参数， 可不加)
                                mtotalHandler.sendMessage(msg);
                            }
                        

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = DOWNLOAD_NEWSERROR; //(值随意定义，和handlemessage 匹配就可以)
                    mtotalHandler.sendMessage(msg);
                } 
            }
    

        }).start();

    }

    
    
    private ArrayList<Basematerial> getGwListinfo() {
        // TODO 自动生成的方法存根 
        ArrayList<Basematerial> templist=new ArrayList<Basematerial>();
         
        Basematerial temp1=new Basematerial();
        temp1.setName("重要公文.jpg");
//        temp1.setNewsNum(12);
//        temp1.setDrwableId(R.drawable.gw_zhongyao);
//        temp1.setColorBack(R.color.item_bg1);
        templist.add(temp1);
        
        Basematerial temp2=new Basematerial();
        temp2.setName("收文.pdf");  
//        temp2.setDrwableId(R.drawable.gw_shouwen);
//        temp2.setColorBack(R.color.item_bg2);
        templist.add(temp2);
        
        Basematerial temp3=new Basematerial();
        temp3.setName("发文.doc"); 
//        temp3.setDrwableId(R.drawable.gw_fawen);
//        temp3.setColorBack(R.color.item_bg3);
        templist.add(temp3);
        
        Basematerial temp4=new Basematerial();
        temp4.setName("公文传阅.mp4"); 
//        temp4.setDrwableId(R.drawable.gw_chuanyue);
//        temp4.setColorBack(R.color.item_bg4);
        templist.add(temp4); 
        
        templist.add(temp1);
        templist.add(temp2);
        templist.add(temp3);
        templist.add(temp1);
        
        return templist;
    }
    
    
    
    //        /** 
    //         * 方法2 ，采用DMP 加载最新的消息
    //        * @Title: onLoadNewMessage 
    //        * @Description: 刚进进入时加载最新的消息
    //        * @param    
    //        * @return void   
    //        * @throws
    //         */
    //        private void onLoadNewMessage() {    
    //            
    //          new NewsAndNotice("newsandnotice").setResponseListener(new DmpResponseListener() {                
    //                @Override
    //                public void onSuccess(DmpResponse response) {
    //                    // TODO 自动生成的方法存根
    //                    org.json.dist.JSONObject obj =response.getResponseJSON();
    //                    String result =obj.optString("result");
    //                  if(!result.equals("")&& null!=result){
    //                  Gson gson = new Gson();
    //                  java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Meetingmaterial>>(){ }.getType(); 
    //                  Meetingmateriallist.clear();
    //                  Meetingmateriallist = gson.fromJson(result, type); 
    //                 // Log.d("获取到的gson转化list长度为：", Meetingmateriallist.size()+"");                  
    //                  Message msg = new Message();
    //                  msg.what = DOWNLOAD_NEWSMESSAGE; //(值随意定义，和handlemessage 匹配就可以)
    //                  msg.obj = result;//(传递的参数， 可不加)
    //                  mtotalHandler.sendMessage(msg);
    //                  }    
    //                }
    //                @Override
    //                public void onFailure(DmpFailResponse arg0) {
    //                    // TODO 自动生成的方法存根                   
    //                }
    //            });
    //        }	

    public static final int DOWNLOAD_NEWSMESSAGE = 0;
    public static final int DOWNLOAD_NEWSERROR = 1;//表示连接服务器异常
    public static final int NO_NEWSMESSAGE = 2;//表示连接服务器异常

    private Handler mtotalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshState=true;
            switch (msg.what) {
            // 正在下载
            case DOWNLOAD_NEWSMESSAGE:
                mGridView.setVisibility(View.VISIBLE);
                news_loading.setVisibility(View.GONE);
                mAdapter = new BaseGridAdapter(mActivity, Meetingmateriallist);
                mAdapter.notifyDataSetChanged();
                mGridView.setAdapter(mAdapter);
                onLoad();
                break;
            case DOWNLOAD_NEWSERROR:                
                MyToast.show(mActivity, "连接服务器异常,请检查网络～", Toast.LENGTH_LONG);
                news_loading.setVisibility(View.GONE);
//                daiban_loading_error.setVisibility(View.VISIBLE);
                onLoad();
                break;
            case NO_NEWSMESSAGE:
                mListView.stopRefresh();
                news_loading.setVisibility(View.GONE);
//                MyToast.show(mActivity, "您还没有授权的应用～", Toast.LENGTH_LONG);
         
                break;
            default:
                break;
            }
        };
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO 自动生成的方法存根

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//        Intent intent=null;
//        if(position==0){
////            intent = new Intent(mActivity,iGwZhongyaoGongwenMainActivity.class);
//            DownLoadFileFromServer tempdownloadfile=new DownLoadFileFromServer(mActivity,"http://f.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5c8ddb0dc52da81cb39db3d0f.jpg", "wer.jpg");
//            tempdownloadfile.showDownloadDialog();   
//            
//        }else {
////            intent = new Intent(mActivity,GwShouwen_FragmentActivity.class); 
//            
////            new Thread(){
////                @Override
////                public void run() { 
////                    ToolHttpUtils.OpenWebFile(mActivity,
////                            DistApp.ALL_CachePathDirTemp + "aaa.pdf",
////                            ToolHttpUtils.getFileType("aaa.pdf"));
////                }
////            }.start();
//            
//        	
//        	
//        	
//        	http://localhost:8000/DistMobile/meeting/filesDownload?fileName=jiekoushuom.txt
//        Toast.makeText(mActivity, "ssssssss"+position+" "+position, 2000).show();
            DownLoadFileFromServer tempdownloadfile=new DownLoadFileFromServer(mActivity,
            		DistApp.serverAbsolutePath+"/meeting/filesDownload?fileName="
		            +Meetingmateriallist.get(position).getId()+"."+Meetingmateriallist.get(position).getFiletype(),
		            Meetingmateriallist.get(position).getName());
            tempdownloadfile.showDownloadDialog();      
             
//        }
        
        
        
//        else if(position==2){
//            intent = new Intent(mActivity,GwFawen_FragmentActivity.class);
//        }else if(position==3){
//            intent = new Intent(mActivity,GwChuanYue_FragmentActivity.class);
//        }   
//         startActivity(intent);
        
        
       
        
        
        

//        if (total_Meetingmateriallist.get(position).getPlatform() != null) {//说明该应用目录下有具体的应用程序
//            if (total_Meetingmateriallist.get(position).getPlatform().trim().equals("Web")) {//说明是web格式的app，直接通过url打开
//                String tempurl = total_Meetingmateriallist.get(position).getUrl();
//                if (tempurl != null && !tempurl.trim().equals("")) {
//                    Intent intent = new Intent(mActivity, NewsOneDetailActivity.class);
//                    intent.putExtra("name", total_Meetingmateriallist.get(position).getName());
//                    intent.putExtra("url", tempurl.indexOf("?") != -1?(tempurl+"&user="+ mSettings.getString("loginName",""))
//                            :(tempurl+"?user="+ mSettings.getString("loginName","")));
//                    
////                    MyToast.show(mActivity, intent.getStringExtra("url"), Toast.LENGTH_LONG);
//                    
//                    
//                    startActivity(intent);
//                }
//            } else if (total_Meetingmateriallist.get(position).getPlatform().trim().equals("Android")) {//说明是原生app，根据applicationidetify打开，打不开则提示下载最新应用
//                try {
//                    //               如 applicationidentify为  com.dist.ibusiness   则打开其他app应用的对外访问activity 路径
//                    //                    应该声明为：com.dist.ibusiness.activity.ibusinessActivity     
//                    String tmpstr = total_Meetingmateriallist.get(position).getApplicationIdentity()
//                            .toString();
//                    String activityname = tmpstr + ".activity."
//                            + tmpstr.substring(tmpstr.lastIndexOf(".") + 1).trim() + "Activity";
//                   // Log.d("点击了" + activityname + "", activityname + "");
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    ComponentName componentName = new ComponentName(tmpstr.trim(), activityname);
//                    intent.setComponent(componentName);
//                    startActivity(intent);
//                } catch (Exception e) {//说明没有安装应用程序，提示用户下载应用程序，下载的路径为：
//                    String downloadurl = DistApp.appPath + total_Meetingmateriallist.get(position).getPath();
//                    //                 Toast.makeText(mActivity, "没有安装相应的程序", Toast.LENGTH_LONG).show();
//                    UpdateManager manager = new UpdateManager(mActivity, downloadurl);
//                    manager.showDownLoad();
//
//                }
//
//            } else {
//                Toast.makeText(mActivity, "暂时无法打开应用", Toast.LENGTH_LONG).show();
//            }
//        } else {
//
//            Toast.makeText(mActivity, "该应用还没有Android版本的app", Toast.LENGTH_LONG).show();
//        }

    }

    
    boolean  refreshState=true;//为了确保短时间内多次刷新导致异常而作的标记，只有上一次刷新执行完毕才执行下一次的刷新动作 
    //        下拉刷新
    @Override
    public void onRefresh() { 
        
        if(refreshState){
            refreshState=false; 
        refreshCnt = 0;//重置下拉的页数为0
        pullstate = false;//表示还没有下拉到最后一页
       // mListView.setPullLoadEnable(true);//可以下拉
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() { 
                onLoadNewMessage();
            }
        }, 2000); 
        } 
    }


    // 上拉更新
    @Override
    public void onLoadMore() {
        // TODO 自动生成的方法存根
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getData2();
//                //                        mAdapter = new ListViewAdapter(mActivity, Meetingmateriallist);
//                //                        mListView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
//                onLoad();
//            }
//        }, 2000);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(new Date().toLocaleString());
    }

//    public void getData2() {
//        if (!pullstate) {
//            ++refreshCnt;
//            int templength;
//            if (total_Meetingmateriallist.size() <= (SHOWSIZE * refreshCnt)) {
//                pullstate = true;//表示已经拉到底了
//                templength = total_Meetingmateriallist.size();
//            } else {
//                templength = SHOWSIZE * refreshCnt;
//            }
//            for (int i = SHOWSIZE * (refreshCnt - 1); i < templength; i++) {
//                Meetingmateriallist.add(total_Meetingmateriallist.get(i));
//            }
//        } else {
////            mGridView.setPullLoadEnable(false);
//            Toast.makeText(mActivity, "已经是最后一页了", Toast.LENGTH_SHORT).show();
//
//        }
//    }


}
