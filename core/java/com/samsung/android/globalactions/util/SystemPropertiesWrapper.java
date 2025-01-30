package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import com.android.internal.C4337R;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes5.dex */
public class SystemPropertiesWrapper {
  public static final String KEY_SYS_SHUTDOWN = "persist.sys.shutdown";
  private static final String TAG = "SystemPropertiesWrapper";
  private final Context mContext;
  private final LogWrapper mLogWrapper;

  public SystemPropertiesWrapper(Context context, LogWrapper logWrapper) {
    this.mContext = context;
    this.mLogWrapper = logWrapper;
  }

  public void set(String key, String value) {
    SystemProperties.set(key, value);
  }

  public String get(String value) {
    return SystemProperties.get(value);
  }

  public boolean isBuildTypeENG() {
    return "eng".equals(Build.TYPE);
  }

  public String getBugReportStatus() {
    return this.mContext.getString(
        C4337R.string.bugreport_status, Build.VERSION.RELEASE, Build.f317ID);
  }

  public boolean isDomesticOtaMode() {
    return "true".equals(SystemProperties.get("ril.domesticOtaStart"));
  }

  public boolean isTabletDevice() {
    String deviceType = SystemProperties.get("ro.build.characteristics");
    return deviceType != null && deviceType.contains(BnRConstants.DEVICETYPE_TABLET);
  }
}
