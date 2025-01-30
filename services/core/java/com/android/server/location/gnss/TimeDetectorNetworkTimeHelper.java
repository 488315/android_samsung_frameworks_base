package com.android.server.location.gnss;

import android.util.Log;

/* loaded from: classes2.dex */
public abstract class TimeDetectorNetworkTimeHelper extends NetworkTimeHelper {
  public static final boolean DEBUG = Log.isLoggable("TDNetworkTimeHelper", 3);
  static final int MAX_NETWORK_TIME_AGE_MILLIS = 86400000;

  public static boolean isInUse() {
    return false;
  }
}
