package com.samsung.android.allshare;

import android.media.MediaMetrics;
import android.util.Log;

/* loaded from: classes5.dex */
public final class DLog {
  private static final String MAIN_DEV_TAG = "AllShare(ASF-API)";
  private static final String PREFIX = "* catched :: * ";
  private static String TAG_API = "L-";
  private static String DEV_DATE = "230412";
  private static boolean PRODUCT_BUILD = false;
  private static boolean SECURE_ON = false;

  public static void setAPIVersionTag() {
    String str = DEV_DATE;
    if (str == null || str.length() == 0) {
      TAG_API = "L-";
    } else {
      TAG_API = "L-d" + DEV_DATE + " / ";
    }
  }

  public static final void v_api(String className, String msg) {
    if (!PRODUCT_BUILD) {
      Log.m100v(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
    }
  }

  public static final void d_api(String className, String msg) {
    Log.m94d(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
  }

  public static final void i_api(String className, String msg) {
    Log.m98i(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
  }

  public static final void w_api(String className, String msg) {
    Log.m102w(MAIN_DEV_TAG, TAG_API + className + MediaMetrics.SEPARATOR + msg);
  }

  public static final void w_api(String className, String msg, Exception ex) {
    Log.m103w(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg, ex);
  }

  public static final void w_api(String className, String msg, Error err) {
    Log.m103w(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg, err);
  }

  public static final void e_api(String className, String msg) {
    Log.m96e(MAIN_DEV_TAG, TAG_API + PREFIX + className + MediaMetrics.SEPARATOR + msg);
  }
}
