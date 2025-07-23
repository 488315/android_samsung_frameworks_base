package com.samsung.android.allshare;

import android.media.MediaMetrics;
import android.util.Log;

/* loaded from: classes6.dex */
public final class DLog {
    private static String DEV_DATE = "250425";
    private static final String MAIN_DEV_TAG = "AllShare(MSF-API)";
    private static final String PREFIX = "* catched :: * ";
    private static boolean PRODUCT_BUILD = false;
    private static boolean SECURE_ON = false;
    private static String TAG_API = "L-";

    public static void setAPIVersionTag() {
        String str = DEV_DATE;
        if (str == null || str.length() == 0) {
            TAG_API = "L-";
            return;
        }
        TAG_API = "L-d" + DEV_DATE + " / ";
    }

    public static final void v_api(String str, String str2) {
        if (PRODUCT_BUILD) {
            return;
        }
        Log.v(MAIN_DEV_TAG, TAG_API + str + MediaMetrics.SEPARATOR + str2);
    }

    public static final void d_api(String str, String str2) {
        Log.d(MAIN_DEV_TAG, TAG_API + str + MediaMetrics.SEPARATOR + str2);
    }

    public static final void i_api(String str, String str2) {
        Log.i(MAIN_DEV_TAG, TAG_API + str + MediaMetrics.SEPARATOR + str2);
    }

    public static final void w_api(String str, String str2) {
        Log.w(MAIN_DEV_TAG, TAG_API + str + MediaMetrics.SEPARATOR + str2);
    }

    public static final void w_api(String str, String str2, Exception exc) {
        Log.w(MAIN_DEV_TAG, TAG_API + PREFIX + str + MediaMetrics.SEPARATOR + str2, exc);
    }

    public static final void w_api(String str, String str2, Error error) {
        Log.w(MAIN_DEV_TAG, TAG_API + PREFIX + str + MediaMetrics.SEPARATOR + str2, error);
    }

    public static final void e_api(String str, String str2) {
        Log.e(MAIN_DEV_TAG, TAG_API + PREFIX + str + MediaMetrics.SEPARATOR + str2);
    }
}
