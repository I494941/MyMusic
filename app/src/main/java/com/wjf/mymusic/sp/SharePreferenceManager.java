package com.wjf.mymusic.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wjf.mymusic.util.CommonConfig;

import java.util.Map;

/**
 * Created by wangshuai on 2018/4/12.
 */

public class SharePreferenceManager {
    private Context mContext;
    private static final String TAG = "SharePreferenceManager";

    public SharePreferenceManager(Context context) {
        mContext = context;
    }

    private SharedPreferences.Editor getSharePreferenceEditor(String fileName) {
        return getSharedPreferences(fileName).edit();
    }

    private SharedPreferences getSharedPreferences(String fileName) {
        return mContext.getSharedPreferences(TextUtils.isEmpty(fileName) ? CommonConfig.SHARE_PREFERENCE_FILE_NAME : fileName, Context.MODE_PRIVATE);
    }

    /*
     * 存数据
     */
    public void putString(Map<String, String> maps) {
        this.putString(maps, null);
    }


    public void putString(Map<String, String> maps, String fileName) {
        if (null == maps || maps.size() == 0)
            return;
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        for (Map.Entry<String, String> map : maps.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();
            editor.putString(key, value);
        }
        editor.commit();
    }

    public void putString(String key, String value) {
        this.putString(key, value, null);
    }

    public void putString(String key, String value, String fileName) {
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        if (null == key)
            return null;
        return this.getString(key, null);
    }

    public String getString(String key, String fileName) {
        if (null == key)
            return null;
        return getSharedPreferences(fileName).getString(key, null);
    }

    public void putBoolean(String key, boolean value) {
        this.putBoolean(key, value, null);
    }

    public void putBoolean(String key, boolean value, String fileName) {
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        if (null == key)
            return false;
        return this.getBoolean(key, null);
    }

    public boolean getBoolean(String key, String fileName) {
        if (null == key)
            return false;
        return getSharedPreferences(fileName).getBoolean(key, false);
    }

    public void putInt(String key, int i) {
        this.putInt(key, i, null);
    }

    public void putInt(String key, int i, String fileName) {
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        editor.putInt(key, i);
        editor.commit();
    }

    public int getInt(String key) {
        if (null == key)
            return 0;
        return this.getInt(key, null);
    }

    public int getInt(String key, String fileName) {
        if (null == key)
            return 0;
        return getSharedPreferences(fileName).getInt(key, 0);
    }

    /*
     * 读数据，返回一个Map<String, String>
     */
    public Map<String, String> getAll() {
        return this.getAll(null);
    }

    public Map<String, String> getAll(String fileName) {
        return (Map<String, String>) getSharedPreferences(fileName).getAll();
    }

    /*
     * 根据文件名删除文件里的数据
     */
    public void deletePreference() {
        this.deletePreference(null);
    }

    public void deletePreference(String fileName) {
        getSharedPreferences(fileName).getAll().clear();
        getSharePreferenceEditor(fileName).clear().commit();
    }
}
