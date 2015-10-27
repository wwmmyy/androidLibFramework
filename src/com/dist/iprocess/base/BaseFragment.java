package com.dist.iprocess.base;

import xyz.yhsj.yhutils.tools.logutils.LogUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
* @类名: BaseFragment 
* @描述: TODO 
* @作者: 王明远 
* @日期: 2015年10月4日 下午3:09:49 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public abstract class BaseFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtils.i(getFragmentName(), " onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i(getFragmentName(), " onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.i(getFragmentName(), " onCreateView()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogUtils.i(getFragmentName(), " onViewCreated()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.i(getFragmentName(), " onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtils.i(getFragmentName(), " onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtils.i(getFragmentName(), " onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.i(getFragmentName(), " onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtils.i(getFragmentName(), " onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtils.i(getFragmentName(), " onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.i(getFragmentName(), " onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtils.i(getFragmentName(), " onDetach()");
	}

	/**
	 * fragment name
	 */
	public abstract String getFragmentName();

}
