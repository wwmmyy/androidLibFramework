package com.dist.iprocess.base;

import xyz.yhsj.yhutils.tools.logutils.LogUtils;
import android.support.v4.app.FragmentActivity;

public abstract class BaseFragmentActivity extends FragmentActivity {
    
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtils.i(getFragmentActivityName(), " onStart()");
    }
    
    
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        LogUtils.i(getFragmentActivityName(), " onRestart()");
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtils.i(getFragmentActivityName(), " onDestroy()");
    }
    
    
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        LogUtils.i(getFragmentActivityName(), " onBackPressed()");
    }
    
    
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtils.i(getFragmentActivityName(), " onStop()");
    }
    
    
    /**
     * fragment name
     */
    public abstract String getFragmentActivityName();

}
