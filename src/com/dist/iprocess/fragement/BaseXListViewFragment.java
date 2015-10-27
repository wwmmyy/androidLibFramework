package com.dist.iprocess.fragement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.base.BaseFragment;
import com.dist.iprocess.entity.Basematerial;
import com.dist.iprocess.util.OttoBus;
import com.dist.iprocess.util.UtilDateDeserializer;
import com.dist.iprocess.util.UtilsTool;
import com.dist.iprocess.view.XListView;
import com.dist.iprocess.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.adapter.CommonAdapter;
import com.lidroid.xutils.adapter.ViewHolder;
import com.lidroid.xutils.util.MyToast;
import com.squareup.otto.Bus;

public class BaseXListViewFragment extends BaseFragment implements OnItemClickListener,
        OnPageChangeListener, IXListViewListener {

    private static final String TAG = "BaseXListViewFragment"; 
    private Activity mActivity; 
    private XListView mListView; 
    private Handler mHandler= new Handler(); 
    private CommonAdapter<Basematerial> mAdapter;
    private ArrayList<Basematerial> Entitylist= new ArrayList<Basematerial>();;//记录当前显示的消息 
    private ArrayList<Basematerial> total_Entitylist = new ArrayList<Basematerial>();;//记录所有的最新消息 
    private int refreshCnt = 0;//表示已经下拉的次数
    private boolean pullstate = false;//记录是否已经下拉到最后一条
    private LinearLayout news_loading; //第一次进入时显示的加载进度框
    private LinearLayout daiban_loading_error;//加载失败时显示刷新 
    private int templogintime = 0; 
//    public static boolean subscribe = false; 
    private Bus mOttoBus;
//    private LinearLayout  gw_search;
//    private String methodName = "";
 

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity; 
        mOttoBus = OttoBus.getInstance();
        mOttoBus.register(this);  
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_xlistview_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {

        //          连接互联网获取最新的消息
        onLoadNewMessage();

        news_loading = (LinearLayout) view.findViewById(R.id.news_loading_progressbar);
        daiban_loading_error = (LinearLayout) view.findViewById(R.id.daiban_loading_error);

//          初始化加载消息数据， 
        mListView = (XListView) view.findViewById(R.id.lv);
//              mListView.setDivider(null);
        mListView.setPullLoadEnable(true);  
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this); 
        
        listViewSetAdapter(); 

        daiban_loading_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO 自动生成的方法存根
                daiban_loading_error.setVisibility(View.GONE);
                news_loading.setVisibility(View.VISIBLE);
                onLoadNewMessage();
            }
        });
        
//        gw_search = (LinearLayout) view.findViewById(R.id.gw_search); 
//       initSearch(view);

  }

  
//  //  用于接收显示或隐藏搜索框
//    @Subscribe
//    public void onshowSearchEvent(showSearchEvent sEvent) { 
//        if(sEvent.getIndex()==1){ 
//            gw_search.setVisibility(sEvent.isState()==true?View.VISIBLE:View.GONE); 
//        } 
//    }
// 
//    //  用于快速搜索
//      private ProgressBar mprogressBar1;//进度条
//      private EditText metSearch;//快速搜索 EditText
//      private ImageButton mimgBtnClear;//快速搜索清空按钮
//     private void initSearch(View view) {
//        // TODO 自动生成的方法存根  
//         metSearch = (EditText) view.findViewById(R.id.etSearch);
//         metSearch.setText("");
//         mimgBtnClear = (ImageButton) view.findViewById(R.id.imgBtnClear);
//         mprogressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
//         ImageButton imgBtnSearch = (ImageButton) view.findViewById(R.id.imgBtnSearch);
//         imgBtnSearch.requestFocus(); 
//         setListener();
//    } 
//    
//     
//     /**
//      * 布局头部文件快速搜索框的监听
//      */
//     private void setListener() {//
//         mimgBtnClear.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 metSearch.setText("");
//             }
//         });
//         metSearch.addTextChangedListener(new TextWatcher() {
//             @Override
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//             }
//
//             @Override
//             public void afterTextChanged(Editable s) {
//                 loadSearchBoxData();
//             }
//
//             @Override
//             public void onTextChanged(CharSequence s, int start, int before, int count) {
//             }
//         });
//
//     }
//
//     String searchdata;
//
//     private void loadSearchBoxData() {   //用于处理快速搜索框搜索操作
//         mimgBtnClear.setVisibility(View.GONE);
//         mprogressBar1.setVisibility(View.VISIBLE);
//         // 新建线程
//         new Thread() {
//             @Override
//             public void run() {
//                 // 需要花时间计算的方法
//                 refreshCnt = 0;//重置下拉的页数为0 
//                 pullstate=false;
//                 
//                 if (!(metSearch.getText().toString().trim().equals(""))) {
//                     searchdata = searchData(metSearch.getText().toString().trim());  
//                     // 向handler发消息 
//                 }else{
//                     int templength=0; 
//                     if (total_Entitylist.size() <= (XListView.SHOWSIZE )) { 
//                         templength = total_Entitylist.size();
//                     } else {
//                         templength = XListView.SHOWSIZE;
//                     }
//                     for (int i = 0; i < templength; i++) {
//                         Entitylist.add(total_Entitylist.get(i));
//                     }
//                     
//                     
//                 }
//                 mhandler2.sendEmptyMessage(0);
////                     else{ 
////                     Entitylist.clear();
////                     Message msg = new Message();
////                     msg.what = DOWNLOADED_NEWSMESSAGE; //(值随意定义，和handlemessage 匹配就可以) 
////                     mtotalHandler.sendMessage(msg);
////                 }
//
//             }
//
//             
//         }.start();
//     }
//     private String searchData(String SearchInfo) {
//         
//         ArrayList<Basematerial>  tempEntitylist = new ArrayList<Basematerial>();
// 
//         for(Basematerial message:total_Entitylist){
//             if(message.getProjectName()!=null&&message.getProjectName().contains(SearchInfo)){
//                 tempEntitylist.add(message); 
//             } 
//         }
//          Entitylist=tempEntitylist; 
//         
//         return  null;
//         
//     
//     } 
//    
//     /**
//      * 用Handler来更新UI
//      */
//     private Handler mhandler2 = new Handler() {//用于处理快速搜索框搜索操作
//         @Override
//         public void handleMessage(Message msg) {
//             mprogressBar1.setVisibility(View.GONE);
//             if (metSearch.getText().toString().trim().equals("")) {
//                 mimgBtnClear.setVisibility(View.GONE);
//             } else {
//                 mimgBtnClear.setVisibility(View.VISIBLE);
//             }
////             ShowSearchList(searchdata);
////             mAdapter.notifyDataSetChanged();
//             
//             listViewSetAdapter();
//         }
//
//     };
    
 
    
    
    /** 
    * @Title: listViewSetAdapter 
    * @Description: TODO
    * @param    
    * @return void   
    * @throws 
    */
    public void listViewSetAdapter() { 
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
        mListView.setAdapter(mAdapter = new CommonAdapter<Basematerial>(
                mActivity, Entitylist, R.layout.base_xlv_item) {
            @Override
            public void convert(ViewHolder helper, Basematerial item)
                  { 
                    helper.setText(R.id.base_xlv_title, item.getName());
                    helper.setText(R.id.base_xlv_content, item.getMurl());   
                    
//                   helper.setText(R.id.tv_title, item.getTitle()); 
////                 helper.getView(R.id.tv_title).setOnClickListener(l);  
//                   helper.getView(R.id.home_searchresult_name).setBackgroundDrawable(getResources().getDrawable(R.drawable.unread));
                    } 
                });
    }
     
    
    

    /**
     * @Title: onLoadNewMessage
     * @Description: 刚进进入时加载最新的消息
     * @param
     * @return void
     * @throws
     */
    String state;

    private void onLoadNewMessage() {
        templogintime = templogintime + 1;
        // TODO 自动生成的方法存根
        new Thread(new Runnable() { 
//            String url = DistApp.serverAbsolutePath + "/mobile/app-newsListByUser.action";//根据用户订阅获取相应的新闻通知 
            @Override
            public void run() {  
                   Map<String, String> map = new HashMap<String, String>(); 
                   map.put("type", "smartplan"); 
                   map.put("user", DistApp.userid);
                  map.put("token", System.currentTimeMillis()+""); 
                  map.put("pagesize", "200");  
                  map.put("pageindex", "0");     
                   try {
                       String totalresult = UtilsTool.getStringFromServer(DistApp.serverAbsolutePath, map);
                       if (null != totalresult) {
                           Entitylist.clear(); 
                     	   total_Entitylist.clear();  
                           //                              Log.d("获取到的totalresulttotalresulttotalresulttotalresult：",totalresult + "");
                           JSONObject obj = new JSONObject(totalresult);
                           if(obj.optBoolean("success")){ 
                           	
                               String result = obj.optString("result"); 
                               Log.d("获取到的result：",result + "");
//                               ,"result":[{"title":"","identity":"1130","time":"2015/09/02","code":"GW20150586"},{
                               if(result!=null){  
                                  java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Basematerial>>() {
                                  }.getType();
                                  Entitylist.clear();
                                  total_Entitylist.clear();

                                  Gson gson1 = new GsonBuilder()
                                          .registerTypeAdapter(java.util.Date.class,
                                                  new UtilDateDeserializer())
                                          .setDateFormat(DateFormat.LONG).create();
                                  total_Entitylist = gson1.fromJson(result, type); 
                              	  Log.d("获取到的attachments：",total_Entitylist.toString() + "");
                              	  
                              	  Message msg = new Message();
                                if (total_Entitylist.size() == 0) { 
                                        msg.what = NO_MESSAGE; //(值随意定义，和handlemessage 匹配就可以) 
   	                         } else { 
       	                                 msg.what = DOWNLOADED_NEWSMESSAGE; //(值随意定义，和handlemessage 匹配就可以)
       	                                 msg.obj = result;//(传递的参数， 可不加) 
   	                         }
                                         mtotalHandler.sendMessage(msg);
                               } 
                           } else {//说明还没有消息                            
                               Message msg = new Message();
                               msg.what = NO_MESSAGE; //(值随意定义，和handlemessage 匹配就可以)
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




    public static final int DOWNLOADED_NEWSMESSAGE = 0;
    public static final int DOWNLOAD_NEWSERROR = 1;//表示连接服务器异常
    public static final int NO_ADD = 2;//说明还没有订阅
    public static final int NO_MESSAGE = 3;//说明还没有消息

    private Handler mtotalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshState=true;
            switch (msg.what) {
            // 正在下载
            case DOWNLOADED_NEWSMESSAGE:
                getData2();
                mListView.setVisibility(View.VISIBLE);
                news_loading.setVisibility(View.GONE);
                daiban_loading_error.setVisibility(View.GONE); 
                listViewSetAdapter();
                stopOnLoad();
                break;
            case DOWNLOAD_NEWSERROR:
                MyToast.show(mActivity, "连接服务器异常,请检查网络～", Toast.LENGTH_LONG); 
                mListView.setVisibility(View.GONE); 
                loadingError();

                break;
            case NO_ADD://说明还没有订阅，提醒用户订阅
                if (templogintime != 1) {
                    Entitylist.clear();
                    total_Entitylist.clear();
                    mListView.setVisibility(View.GONE);
                    MyToast.show(mActivity, "暂无发文", Toast.LENGTH_LONG); 
                }
                loadingError();
                break;
            case NO_MESSAGE://暂无新闻通知
                if (templogintime != 1) {
                    mListView.setVisibility(View.GONE);
                    MyToast.show(mActivity, "暂无发文", Toast.LENGTH_LONG);
                }
                loadingError();

                break;
            default:
                break;
            }
        };
    };

    public void onResume() {
        super.onResume();
//        if (subscribe) {
//            subscribe = false;
//            news_loading.setVisibility(View.VISIBLE);
//            daiban_loading_error.setVisibility(View.GONE);
//            onRefresh();
//        }
    }

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
        // TODO 自动生成的方法存根
//        Intent intent2 = new Intent();
////        intent2.setClass(mActivity, GongWenDetailActivity.class);
//        intent2.setClass(mActivity, ShouFaWenDetailActivity.class); 
//        intent2.putExtra("gongwenId", Entitylist.get(position-1).getProjectId());   
//        startActivity(intent2);

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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData2();
                mAdapter.notifyDataSetChanged();
                stopOnLoad();
            }
        }, 2000);
    }

    private void stopOnLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(new Date().toLocaleString());
    }

    public void getData2() {
        if (!pullstate) {
            ++refreshCnt;
            int templength;
            if (total_Entitylist.size() <= (XListView.SHOWSIZE * refreshCnt)) {
                pullstate = true;//表示已经拉到底了
                templength = total_Entitylist.size();
            } else {
                templength = XListView.SHOWSIZE * refreshCnt;
            }
            for (int i = XListView.SHOWSIZE * (refreshCnt - 1); i < templength; i++) {
                Entitylist.add(total_Entitylist.get(i));
            }
        } else {
            mListView.setPullLoadEnable(false);
            Toast.makeText(mActivity, "已经是最后一页了", Toast.LENGTH_SHORT).show();

        }
    }

    /** 
    * @Title: loadingError 
    * @Description: TODO
    * @param    
    * @return void   
    * @throws 
    */
    public void loadingError() {
        news_loading.setVisibility(View.GONE);
        daiban_loading_error.setVisibility(View.VISIBLE);
    }

}