package com.dist.iprocess.fragement;

import xyz.yhsj.yhutils.tools.sp.SharePreferenceUtil;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dist.iprocess.R;
import com.dist.iprocess.activity.BaseGestureSetPwdActivity;
import com.dist.iprocess.app.DistApp;
import com.dist.iprocess.base.BaseFragment;
import com.dist.iprocess.view.SwitchButton;

/**
 * 首页 设置 fragment
 * 
 * @author dewyze
 */
public class BaseSettingFragment extends BaseFragment implements OnClickListener {

    private static final String TAG = "SettingFragment";
    private Activity mActivity;
    //	private TextView mTitleTv;
    // 推荐给微信好友
    //	private RelativeLayout mRecommondToWeixinLayout;
    // 反馈意见
    //	private RelativeLayout mFeedbackLayout;
    // 关于我们
    //	private RelativeLayout mAboutUsLayout;
    // 应用推荐
    //	private RelativeLayout mAppRecommendLayout;
    // 清除缓存
    private RelativeLayout clear_cache_quit;
    TextView user_setting_ipinfo;
    SwitchButton show_sogudu_switch;
    TextView tx_finish_quit; 
//    private in.srain.cube.image.ImageLoader imageLoader;//加载用户头像

    public static BaseSettingFragment newInstance() {
        BaseSettingFragment baseSettingFragment = new BaseSettingFragment();

        return baseSettingFragment;
    }

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
        View view = inflater.inflate(R.layout.base_fragement_user_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initEvents();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        //		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        //		mTitleTv.setText(R.string.setting);

        //		mRecommondToWeixinLayout = (RelativeLayout) view.findViewById(R.id.recommond_to_weixin_layout);
        //		mFeedbackLayout = (RelativeLayout) view.findViewById(R.id.feedback_layout);
        //		mAboutUsLayout = (RelativeLayout) view.findViewById(R.id.about_us_layout);
        //		mAppRecommendLayout = (RelativeLayout) view.findViewById(R.id.app_recommend_layout);
        clear_cache_quit = (RelativeLayout) view.findViewById(R.id.clear_cache_quit);

        show_sogudu_switch = (SwitchButton) view.findViewById(R.id.show_sogudu_switch);

        tx_finish_quit = (TextView) view.findViewById(R.id.tx_finish_quit);

        LinearLayout user_info_detail = (LinearLayout) view.findViewById(R.id.user_info_detail);
        TextView user_info_detail_edit = (TextView) view.findViewById(R.id.user_info_detail_edit);
        ImageView user_info_detail_more = (ImageView) view.findViewById(R.id.user_info_detail_more);
        RelativeLayout grant_address_info_id = (RelativeLayout) view
                .findViewById(R.id.grant_address_info_id);
        RelativeLayout guihua_quan = (RelativeLayout) view
                .findViewById(R.id.guihua_quan);
        
        RelativeLayout user_feedback = (RelativeLayout) view
                .findViewById(R.id.user_feedback);
        user_feedback.setOnClickListener(this);
        
        

        guihua_quan.setOnClickListener(this);
        grant_address_info_id.setOnClickListener(this);
        user_info_detail_more.setOnClickListener(this);
        user_info_detail_edit.setOnClickListener(this);
        user_info_detail.setOnClickListener(this);

        user_setting_ipinfo = (TextView) view.findViewById(R.id.user_setting_ipinfo);
        user_setting_ipinfo.setText(DistApp.serverIP);
        RelativeLayout user_setting_iplay = (RelativeLayout) view
                .findViewById(R.id.user_setting_iplay);
        user_setting_iplay.setOnClickListener(this);
        user_setting_ipinfo.setOnClickListener(this);

//        //		        加载用户头像
//        imageLoader = ImageLoaderFactory.create(mActivity);
//        in.srain.cube.image.CircleCubeImageView user_login_pic = (in.srain.cube.image.CircleCubeImageView) view
//                .findViewById(R.id.user_login_pic);
//        user_login_pic.loadImage(imageLoader,
//                DistApp.userImagePath + mSettings.getString("userid", "") + ".jpg");// 设为缓存图片         

        TextView user_login_name = (TextView) view.findViewById(R.id.user_login_name);
        user_login_name.setText(SharePreferenceUtil.getString(mActivity,"username", ""));

    }

    boolean initState = false;//记录button按钮的初始状态，假入手势是开启状态则初始置为true，同时避免监听响应该事件

    private void initEvents() {
        //		mRecommondToWeixinLayout.setOnClickListener(this);
        //		mFeedbackLayout.setOnClickListener(this);
        //		mAboutUsLayout.setOnClickListener(this);
        //		mAppRecommendLayout.setOnClickListener(this);
        clear_cache_quit.setOnClickListener(this);
        tx_finish_quit.setOnClickListener(this); 
        Boolean soguduIsOpen = SharePreferenceUtil.getBoolean(mActivity,"gestureIsOpen", false);
        if (soguduIsOpen) {
            show_sogudu_switch.setChecked(soguduIsOpen);
            initState = true;
        }

        show_sogudu_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean b) {
                if (b && !initState) {//说明开启了设置密码  
                    SharePreferenceUtil.setValue(mActivity,"gestureIsOpen", true);

                    Intent intent2 = new Intent();
                    intent2.setClass(mActivity, BaseGestureSetPwdActivity.class);
                    startActivity(intent2);

                } else {//说明关闭设置密码功能 
                    SharePreferenceUtil.setValue(mActivity,"gestureIsOpen", false); 
                    SharePreferenceUtil.remove(mActivity, "password"); 
                }
                initState = false;
            }
        });

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

    private void showDialog() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.base_ip_set_dialog, null);
        final Dialog dialog = new Dialog(mActivity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();

        Button ipset_setting_cancel = (Button) view.findViewById(R.id.ipset_setting_cancel);
        Button ipset_setting_sure = (Button) view.findViewById(R.id.ipset_setting_sure);
        TextView ipset_oldip = (TextView) view.findViewById(R.id.ipset_oldip);
        final EditText ipset_newip = (EditText) view.findViewById(R.id.ipset_newip);

        ipset_oldip.setText(user_setting_ipinfo.getText().toString());

        ipset_setting_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO 自动生成的方法存根
                dialog.dismiss();
            }
        });
        ipset_setting_sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) { 
                SharePreferenceUtil.setValue(mActivity,"serverIp", ipset_newip.getText().toString());
                
                user_setting_ipinfo.setText(ipset_newip.getText().toString() + "  ");
                dialog.dismiss();
            }
        });

        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) { 
        case R.id.clear_cache_quit:
        case R.id.tx_finish_quit:
            // 清除缓存
            mActivity.finish();
            break;
        case R.id.user_info_detail_more:
        case R.id.user_info_detail_edit:
        case R.id.user_info_detail:
//            intent.setClass(mActivity, UserDetailActivity.class);
            mActivity.startActivity(intent);
            break;
        case R.id.grant_address_info_id:

//            intent.setClass(mActivity, GrantSetActivity.class);
            mActivity.startActivity(intent);
            break;
        case R.id.user_setting_ipinfo:
        case R.id.user_setting_iplay:

            showDialog();

            break;
//        case R.id.guihua_quan:
//            intent.setClass(mActivity, GrantNewsPushActivity.class);
//            mActivity.startActivity(intent);
//            break;
            
        case R.id.user_feedback:
//            intent.setClass(mActivity, FeedbackActivity.class);
//            mActivity.startActivity(intent);
            break;
            
        case R.id.guihua_quan:
//            intent.setClass(mActivity, PlanCircleActivity.class);
            mActivity.startActivity(intent);
            break;        
            
            
 
            
        default:
            break;
        }
    }

}
