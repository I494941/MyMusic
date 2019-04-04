package com.wjf.mymusic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wjf on 2018/5/4.
 */

public class DateUtil {

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }
}
