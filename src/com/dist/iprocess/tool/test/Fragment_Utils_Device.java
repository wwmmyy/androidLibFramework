package com.dist.iprocess.tool.test;


import xyz.yhsj.yhutils.tools.phone.DeviceUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dist.iprocess.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Utils_Device extends Fragment {


    @ViewInject(R.id.info1)
    private TextView info1;

    @ViewInject(R.id.info2)
    private TextView info2;

    @ViewInject(R.id.info3)
    private TextView info3;

    @ViewInject(R.id.info4)
    private TextView info4;


    public Fragment_Utils_Device() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_utils, container, false);
        ViewUtils.inject(this, rootView);

        info1.setText("IMEI标识:" + DeviceUtils.getIMEI(getActivity()));
        info2.setText("设备名称:" + DeviceUtils.getDeviceName());
        info3.setText("系统版本:" + DeviceUtils.getDeviceSDK() + "");
        info4.setText("IP地址:" + DeviceUtils.getIp(getActivity()));

        return rootView;
    }


}
