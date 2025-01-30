package com.samsung.android.globalactions.util;

import android.app.usage.IUsageStatsManager;
import android.content.Context;
import android.p009os.Binder;
import android.p009os.Debug;
import android.p009os.ServiceManager;

/* loaded from: classes5.dex */
public class UsageStatsWrapper {
  private static final String TAG = "UsageStatsWrapper";
  private IUsageStatsManager mAppUsageStats =
      IUsageStatsManager.Stub.asInterface(ServiceManager.getService(Context.USAGE_STATS_SERVICE));
  private final Context mContext;
  private final LogWrapper mLogWrapper;

  public UsageStatsWrapper(Context context, LogWrapper logWrapper) {
    this.mContext = context;
    this.mLogWrapper = logWrapper;
  }

  public void shutdownDump(String extraInfo) {
    dump("[shutdown]", extraInfo);
  }

  public void restartDump(String extraInfo) {
    dump("[restart]", extraInfo);
  }

  private void dump(String prefix, String extraInfo) {
    Binder.getCallingUserHandle().semGetIdentifier();
    String callback = Debug.getCallers(9);
    String str = callback + extraInfo;
  }
}
