package com.dist.iprocess.tool.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dist.iprocess.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Utils_Bitmap extends Fragment {


    @ViewInject(R.id.img)
    private ImageView img;

    public Fragment_Utils_Bitmap() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bitmap_utils, container, false);
        ViewUtils.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.getimg)
    public void btnListener(View v) {

        BitmapUtils bitmapUtils = new BitmapUtils(getActivity());

        // 加载网络图片
        bitmapUtils.display(img, "http://img.shendu.com/forum/201108/23/123108h3dqhpj0bphzs9hp.jpg");

        // 加载本地图片(路径以/开头， 绝对路径)
        // bitmapUtils.display(img, "/sdcard/test.png");

        // 加载assets中的图片(路径以assets开头)
        // bitmapUtils.display(img, "assets/img/wallpaper.jpg");

        // 使用ListView等容器展示图片时可通过PauseOnScrollListener控制滑动和快速滑动过程中时候暂停加载图片
        // listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        // listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true, customListener));
    }


}
