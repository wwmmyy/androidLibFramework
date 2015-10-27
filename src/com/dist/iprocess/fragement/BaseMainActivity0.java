package com.dist.iprocess.fragement;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.base.BaseFragmentActivity;
import com.dist.iprocess.tool.test.Fragment_Utils_App;
import com.dist.iprocess.tool.test.Fragment_Utils_Asset;
import com.dist.iprocess.tool.test.Fragment_Utils_Bitmap;
import com.dist.iprocess.tool.test.Fragment_Utils_Date;
import com.dist.iprocess.tool.test.Fragment_Utils_Density;
import com.dist.iprocess.tool.test.Fragment_Utils_Device;
import com.dist.iprocess.tool.test.Fragment_Utils_IO;
import com.dist.iprocess.tool.test.Fragment_Utils_Image;
import com.dist.iprocess.tool.test.Fragment_Utils_KeyBoard;
import com.dist.iprocess.tool.test.Fragment_Utils_NetWork;
import com.dist.iprocess.tool.test.Fragment_Utils_Notification;
import com.dist.iprocess.tool.test.Fragment_Utils_SDCard;
import com.dist.iprocess.tool.test.Fragment_Utils_Screen;
import com.dist.iprocess.view.BaseTabWidget;
import com.dist.iprocess.view.BaseTabWidget.OnTabSelectedListener;

/**
 * 采用的是BaseTabWidget底部导航条
 * @类名: Base2MainActivity
 * @描述: TODO
 * @作者: 王明远
 * @日期: 2015年10月3日 下午1:23:13
 * @修改人:
 * @修改时间:
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright © All rights reserved.
 */
public class BaseMainActivity0 extends BaseFragmentActivity implements  OnTabSelectedListener { 
 
    private long mExitTime;

    private RadioGroup rg; 
    Context mContext=this;
    private ViewPager mViewPager;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentManager fragmentManager;
//    底部导航菜单栏
    int[] tab_barid = { R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4, R.id.rb5 };
    int tabSize = tab_barid.length;
    RadioButton[] tab_bar = new RadioButton[tabSize];
    private BaseTabWidget mTopIndicator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_fragement_main);
        initFragement();
       
    }

    private void initFragement() {
        // TODO Auto-generated method stubp
        ((TextView) findViewById(R.id.base_top_title)).setText("DIST iProcess");
        
        
        mTopIndicator = (BaseTabWidget) findViewById(R.id.top_indicator);
        mTopIndicator.setVisibility(View.VISIBLE);
        mTopIndicator.setOnTabSelectedListener(this);
        
        ((LinearLayout) findViewById(R.id.tab2)).setVisibility(View.GONE);
        
        
        fragments.add(new BaseGridFragment()); 
        fragments.add(new BaseSettingFragment()); 
        fragments.add(new Fragment_Utils_App()); 
        fragments.add(new Fragment_Utils_Asset()); 
        fragments.add(new Fragment_Utils_Bitmap()); 
        fragments.add(new Fragment_Utils_Date()); 
        fragments.add(new Fragment_Utils_Density()); 
        fragments.add(new Fragment_Utils_Device()); 
        fragments.add(new Fragment_Utils_Image()); 
        fragments.add(new Fragment_Utils_IO()); 
        
        fragments.add(new Fragment_Utils_KeyBoard()); 
        fragments.add(new Fragment_Utils_NetWork()); 
        fragments.add(new Fragment_Utils_Notification()); 
        fragments.add(new Fragment_Utils_Screen()); 
        fragments.add(new Fragment_Utils_SDCard()); 
      
 
        mViewPager = (ViewPager) findViewById(R.id.view_pager); 
        this.fragmentManager = this.getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        this.mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() { 
            @Override
            public void onPageSelected(int position) { 
                //	                     滑动完成后底部的导航条也跟着跳转  
//                for (int i = 0; i < tabSize; i++) { 
//                        tab_bar[i].setChecked(i==position); 
//                } 
                
                mTopIndicator.setTabsDisplay(mContext, position);          
            } 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {   } 
            @Override
            public void onPageScrollStateChanged(int arg0) { }
        });
    }

 
    

    // 监听手机上的BACK键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断菜单是否关闭 
                // 判断两次点击的时间间隔（默认设置为2秒）
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                    super.onBackPressed();
                } 
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = fragments.get(position);// 取得位置，获取出来Fragment
            if (!fragment.isAdded()) { // 如果fragment还没有added
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                /**
                 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
                 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
                 */
                fragmentManager.executePendingTransactions();
            } 
            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // 为viewpager增加布局
            } 
            return fragment.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    @Override
    public String getFragmentActivityName() {
        // TODO Auto-generated method stub
        return "Base2MainActivity";
    }

    @Override
    public void onTabSelected(int index) {
        // TODO Auto-generated method stub
        mViewPager.setCurrentItem(index);
        
    }
}
