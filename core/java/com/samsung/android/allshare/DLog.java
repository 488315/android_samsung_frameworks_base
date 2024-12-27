package com.samsung.android.allshare;

import android.media.MediaMetrics;
import android.util.Log;

public final class DLog {
    private static final String MAIN_DEV_TAG = "AllShare(ASF-API)";
    private static final String PREFIX = "* catched :: * ";
    private static String TAG_API = "L-";
    private static String DEV_DATE = "230412";
    private static boolean PRODUCT_BUILD = false;
    private static boolean SECURE_ON = false;

    public static void setAPIVersionTag() {
        if (DEV_DATE == null || DEV_DATE.length() == 0) {
            TAG_API = "L-";
        } else {
            TAG_API = "L-d" + DEV_DATE + " / ";
        }
    }

    public static final void v_api(String className, String msg) {
        if (!PRODUCT_BUILD) {
            Log.v(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
        }
    }

    public static final void d_api(String className, String msg) {
        Log.d(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
    }

    public static final void i_api(String className, String msg) {
        Log.i(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
    }

    public static final void w_api(String className, String msg) {
        Log.w(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
    }

    public static final void w_api(String className, String msg, Exception ex) {
        Log.w(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg, ex);
    }

    public static final void w_api(String className, String msg, Error err) {
        Log.w(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg, err);
    }

    public static final void e_api(String className, String msg) {
        Log.e(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg);
    }
}
