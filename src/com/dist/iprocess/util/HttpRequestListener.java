package com.dist.iprocess.util;
/**
 * ============================================================
 * 
 * 版权 ：安博教育集团 版权所有 (c) 2013
 * 
 * 作者:mwqi
 * 
 * 版本 ：1.0
 * 
 * 创建日期 ： 2013-1-18 上午1:08:58
 * 
 * 描述 ：
 * 
 *      Http请求接口
 * 修订历史 ：
 * 
 * ============================================================
 **/
public interface HttpRequestListener {

    public static final int EVENT_BASE =  0x100;
    
    /**
     * 没有网络的信息提示
     * */
    public static final int EVENT_NOT_NETWORD =  EVENT_BASE + 1;
    
    /**
     * 网络异常的信息提示
     * */
    public static final int EVENT_NETWORD_EEEOR =  EVENT_BASE + 2;
    
    /**
     * 获取网络数据失败
     * */
    public static final int EVENT_GET_DATA_EEEOR =  EVENT_BASE + 3;

    /**
     * 获取网络数据成功
     * */
    public static final int EVENT_GET_DATA_SUCCESS =  EVENT_BASE + 4;
    /**
     * 获取网络数据成功
     * */
    public static final int EVENT_CLOSE_SOCKET =  EVENT_BASE + 5;
    
    public void action(int actionCode, Object object);
}