package com.dist.iprocess.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dist.iprocess.R;
import com.dist.iprocess.app.DistApp;
import com.lidroid.xutils.file.FileUtils;

public class DownLoadFileFromServer {
    
    Context mContext;
    String urlFilePath;
    String name;
    public DownLoadFileFromServer(Context context,String urlfilePath, String name) {
        // TODO 自动生成的构造函数存根
        mContext=context;
        this.urlFilePath=urlfilePath;
        this.name=name;
    }
    

    
    


    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载结束 */
    private static final int NO_FILE = 3;
    /* 下载结束 */
    private static final int CANNOT_OPEN = 4;

    /**
     * 显示软件下载对话框
     */
    public void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("正在下载");
        builder.setCancelable(false);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.base_apk_update, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);

        //            Button down_cancel = (Button) v.findViewById(R.id.down_cancel);
        //            down_cancel.setOnClickListener(new OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    // TODO 自动生成的方法存根
        //                    cancelUpdate = true;
        //                }
        //            });

        builder.setView(v);
        //            mDownloadDialog.setCanceledOnTouchOutside(false);

        // 取消更新
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 设置取消状态
                        cancelUpdate = true;
                    }
                });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        DownAndOpenOneFile(urlFilePath, name);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            // 正在下载
            case DOWNLOAD:
                // 设置进度条位置
                mProgress.setProgress(progress);
                break;
            case DOWNLOAD_FINISH:
                // 安装文件
                String filename = msg.obj + "";
                FileUtils.OpenWebFile(mContext,
                        DistApp.ALL_CachePathDirTemp + filename,
                        FileUtils.getFileType(filename));
                break;
            case NO_FILE:
                // 设置进度条位置
                Toast toast = Toast.makeText(mContext, "服务器端没有该文件！",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                if (mDownloadDialog.isShowing()) {
                    mDownloadDialog.dismiss();
                }
                break;
            case CANNOT_OPEN:
                // 安装文件
                Toast.makeText(mContext, "无法下载该文件", 1).show();

                break;

            default:
                break;
            }
        };
    };

    //    String filename;
    /**
     * 点击打开文件
     * 
     * @param count
     * @param map
     */
    public void DownAndOpenOneFile(final String filepath, final String filename) {

        new Thread() {
            @Override
            public void run() {
                try {
                    // 方法3
                    File tempfile = getFileFromServer(filepath, filename);
                    if (tempfile != null && tempfile.length() > 0) {
                    } else {
                        mHandler.sendEmptyMessage(NO_FILE);
                    }

                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    mHandler.sendEmptyMessage(CANNOT_OPEN);
                    e.printStackTrace();
                }
            };
        }.start();

    }

    /**
     * 从网络服务器获取文件资料信息
     */
    public File getFileFromServer(String path, String fileName) throws Exception {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                // 获取文件大小
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                FileUtils.deleteTempFile(DistApp.ALL_CachePathDirTemp);//删除临时文件

                File dir = new File(DistApp.ALL_CachePathDirTemp);
                if (dir.exists() == false) {
                    boolean b = dir.mkdirs();
                }

                File file = new File(DistApp.ALL_CachePathDirTemp, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWNLOAD);
                    if (numread <= 0) {
                        // 下载完成
                        if (length > 0) {//说明下载的文件大小不为空
                            Message msg = new Message();
                            msg.what = DOWNLOAD_FINISH; //(值随意定义，和handlemessage 匹配就可以)
                            msg.obj = fileName;//(传递的参数， 可不加)
                            mHandler.sendMessage(msg);
                        }
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (!cancelUpdate);// 点击取消就停止下载.
                fos.close();
                is.close();

                // 取消下载对话框显示
                mDownloadDialog.dismiss();
                return file;
            }
        }
        return null;
    }

    
    
    
    
    
}
