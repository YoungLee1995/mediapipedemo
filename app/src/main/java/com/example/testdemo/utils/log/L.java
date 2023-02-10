package com.example.testdemo.utils.log;

import android.util.Log;

public final class L {
    private static final String TAG = "test_demo";
    private static boolean DEBUG = false;// 是否打印日志

    public static void setDebug(boolean debug) {
        DEBUG = debug;
        if (DEBUG) {
            Logger.init(TAG).setMethodCount(0).hideThreadInfo()
                    .setLogLevel(L.DEBUG ? Logger.LogLevel.FULL : Logger.LogLevel.NONE);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Logger.v(getTagName(), msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Logger.d(getTagName(), msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Logger.i(getTagName(), msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Logger.w(getTagName(), msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Logger.e(getTagName(), msg);
        }
    }

    public static void json(String msg) {
        if (DEBUG) {
            Logger.json(msg);
        }
    }

    public static void e(String msg, Exception tr) {
        if (DEBUG) {
            Logger.e(getTagName(), msg, tr);
        }
    }

    private static String getTagName() {
        return TAG;
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Logger.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Logger.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Logger.e(tag, message);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, msg, tr);
        }
    }
}