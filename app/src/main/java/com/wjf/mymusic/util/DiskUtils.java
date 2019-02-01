package com.wjf.mymusic.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wjf on 2019/2/1.
 */
public class DiskUtils {
    //文件路径
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取应用的版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    /**
     * 用md5算法对字符串加密
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key){
        String cacheKey;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            cacheKey=bytesToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey=String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 将bytes数组转换为string类型
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<bytes.length;i++){
            String hex=Integer.toHexString(0xFF & bytes[i]);
            if (hex.length()==1){//
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
