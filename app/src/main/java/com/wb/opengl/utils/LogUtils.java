package com.wb.opengl.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

/**
 * @author wangbo
 * @since 2020/5/26
 */
public class LogUtils {
    private LogUtils() {

    }

    private static final String TAG = "AutoGlobalDebug";

    public static void v(String tag, String msgFotmat, Object... args) {
        log(Log.VERBOSE,tag,msgFotmat,null,args);
    }

    public static void d(String tag, String msgFotmat, Object... args) {
        log(Log.DEBUG,tag,msgFotmat,null,args);
    }

    public static void i(String tag, String msgFotmat, Object... args) {
        log(Log.INFO,tag,msgFotmat,null,args);
    }
    public static void w(String tag, String msgFotmat, Object... args) {
        log(Log.WARN,tag,msgFotmat,null,args);
    }

    public static void e(String tag, String msgFotmat, Object... args) {
        log(Log.ERROR,tag,msgFotmat,null,args);
    }

    public static void a(String tag, String msgFotmat, Object... args) {
        log(Log.ASSERT,tag,msgFotmat,null,args);
    }

    private static void log(int level, String tag, String msg, Throwable tr, Object... args) {
        msg = String.format(Locale.US, msg, args);
        if (null == tr) {
            Log.println(level,tag,msg);
        }else {
            Log.println(level,tag,msg+"\n"+getStackTraceString(tr));
        }
    }

    private static String getStackTraceString(Throwable tr){
        StringWriter sw = new StringWriter(4096);
        PrintWriter pw = new PrintWriter(sw,false);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }


}
