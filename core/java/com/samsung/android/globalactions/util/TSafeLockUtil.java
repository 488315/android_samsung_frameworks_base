package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.provider.Settings;

/* loaded from: classes5.dex */
public class TSafeLockUtil {
  private static final String OFF_MENU_SETTING = "off_menu_setting";
  private static final String TLOCK_PKG_NAME = "com.skt.t_smart_charge";
  private final Context mContext;

  public TSafeLockUtil(Context context) {
    this.mContext = context;
  }

  public boolean isTSafeLock() {
    boolean z;
    boolean z2 = false;
    Boolean ret = false;
    PackageManager packageManager = this.mContext.getPackageManager();
    try {
      ApplicationInfo tLock = packageManager.getApplicationInfo(TLOCK_PKG_NAME, 0);
      if (tLock == null) {
        z = false;
      } else {
        z = true;
      }
      ret = Boolean.valueOf(z);
    } catch (PackageManager.NameNotFoundException e) {
    }
    if (ret.booleanValue()) {
      if (Settings.System.getInt(this.mContext.getContentResolver(), OFF_MENU_SETTING, 0) == 1) {
        z2 = true;
      }
      ret = Boolean.valueOf(z2);
    }
    return ret.booleanValue();
  }
}
