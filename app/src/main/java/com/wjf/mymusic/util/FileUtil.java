package com.wjf.mymusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

import java.io.*;

/**
 * Created by ws on 2017/10/13 0013.
 */

public class FileUtil {
    public static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ?
            Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";// 保存到SD卡
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/mymusic/";// 保存的确切位置

    /**
     * Sd卡是否可用
     */
    public static boolean isUsable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 保存路径
     */
    public static String getImageDir() {
        File file = new File(SAVE_REAL_PATH);
        if (!file.exists())
            file.mkdirs();

        return SAVE_REAL_PATH;
    }

    public static String getImageDir(Context context) {
        String packageName = context.getPackageName();
        String path = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + packageName + "/images/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }


    /**
     * 文件名
     */
    public static String getFileName(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        return fileName;
    }

    public static Bitmap readImage(String url) {
        if (!isUsable() || url == null)
            return null;

        File imageFile = new File(getImageDir(), getFileName(url));

        if (!imageFile.exists())
            return null;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeResponseBody2Disk(ResponseBody body, String saveName) {
        if (body == null)
            return false;

        try {
            File file = new File(getImageDir(), saveName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    LogUtil.d("downloading", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void copyAssets2SD(Context context, String strSrcFileName, String strOutFileName) throws IOException {
        String dbPath = getDBPath(context);
        String dbFile = dbPath + strOutFileName;
        LogUtil.e("dbFile", dbFile);
        //File file = new File(getImageDir(), strOutFileName);
        File file = new File(dbFile);
        if (file.exists()) {
            LogUtil.e("==================", "db exists");
            return;
        }
        File filepath = new File(dbPath);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        InputStream is = context.getAssets().open(strSrcFileName);
        FileOutputStream os = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
            //LogUtil.e("==================", "copying" + length);
        }
        os.flush();
        is.close();
        os.close();
        LogUtil.e("==================", "copy complete");
    }

    @NonNull
    public static String getDBPath(Context context) {
        String packageName = context.getPackageName();
        return "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + packageName + "/databases/";
    }

    public static String copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024 * 8];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
            return target.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
