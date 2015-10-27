package com.dist.iprocess.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dist.iprocess.R;
 
public class MaterialTypePic {
    Context mActivity;

    public MaterialTypePic(Context activity) {
        // TODO Auto-generated constructor stub
        mActivity = activity;
    }

    private final int DEFAULT_WIDTH = 312;
    private final int DEFAULT_HEIGHT = 234;

    private int width;
    private int height;
    private int sampleSize = 1;

    public enum FileCategory {
        All, Music, Video, Picture, Theme, Doc, PPT, XSL, TXT, PDF, Zip, Apk, Custom, Other, Favorite
    }

    private String APK_EXT = "apk";
    private String THEME_EXT = "mtz";

    private String[] DOC_EXTS = new String[] { "doc", "docx" };
    private String[] PPT_EXTS = new String[] { "ppt", "pptx" };
    private String[] XSL_EXTS = new String[] { "XSL", "XSLX" };
    private String[] TXT_EXTS = new String[] { "txt", "log", "ini", "lrc" };
    private String PDF_EXTS = "pdf";

    private String[] ZIP_EXTS = new String[] { "zip", "rar" };
    private String[] VIDEO_EXTS = new String[] { "mp4", "wmv", "mpeg", "m4v", "3gp", "3gpp", "3g2",
            "3gpp2", "asf" };
    private String[] MUSIC_EXTS = new String[] { "mp3", "wma", "wav", "ogg", "ape", "acc", "amr" };
    private String[] PICTURE_EXTS = new String[] { "jpg", "jpeg", "gif", "png", "bmp", "wbmp" };

    public Bitmap getCategoryFromPath(String path) {
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                R.drawable.default_fileicon);
        int dotPosition = path.lastIndexOf('.');
        if (dotPosition == -1) return bitmap;

        String ext = path.substring(dotPosition + 1, path.length());
//        if (ext.equalsIgnoreCase(APK_EXT)) {
//            BitmapDrawable bd = (BitmapDrawable) getApkIcon(mActivity, path);
//            return bd.getBitmap();
//        }

        if (ext.equalsIgnoreCase(THEME_EXT)) {
            return bitmap;
        }
        if (ext.equalsIgnoreCase(PDF_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.pdf);
        }
        if (matchExts(ext, DOC_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.doc);
        }
        if (matchExts(ext, PPT_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ppt);
        }
        if (matchExts(ext, XSL_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.xls);
        }
        if (matchExts(ext, TXT_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.drawable.file_doc);
        }

        if (matchExts(ext, ZIP_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.drawable.file_archive);
        }

        if (matchExts(ext, VIDEO_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.drawable.file_video);
        }
        if (matchExts(ext, MUSIC_EXTS)) {
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                    R.drawable.file_audio);
        }

        if (matchExts(ext, PICTURE_EXTS)) {
//            bitmap = getBitmap(path);
//            return bitmap;
            return bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.file_image);
           
        }
        return bitmap;
    }

    private static boolean matchExts(String ext, String[] exts) {
        for (String ex : exts) {
            if (ex.equalsIgnoreCase(ext)) return true;
        }
        return false;
    }

    private void getBitmapSize(String filePaths) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePaths, options);
        width = options.outWidth;
        height = options.outHeight;
    }

    private Bitmap getBitmap(String filePaths) {
        getBitmapSize(filePaths);
        while ((width / sampleSize > DEFAULT_WIDTH * 2)
                || (height / sampleSize > DEFAULT_HEIGHT * 2)) {
            sampleSize *= 2;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePaths, options);
    }

    /**
     * 获取APK图标
     * 
     * @param context
     * @param path
     * @return
     */
    public static Drawable getApkIcon(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            try {
                return pm.getApplicationIcon(appInfo);
            } catch (OutOfMemoryError e) {
                Log.e("LOG_TAG", e.toString());
            }
        }
        return null;
    }
}
