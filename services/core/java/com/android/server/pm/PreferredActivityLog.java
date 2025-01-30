package com.android.server.pm;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.server.p025pm.PmLog;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PreferredActivityLog {
  public static void logDroppingPreferredActivity(
      PreferredActivity preferredActivity, List list, String str) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        "Dropping Preferred: "
            + preferredActivity.mPref.mShortComponent
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    StringBuilder sb2 = new StringBuilder();
    sb2.append(str);
    sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(sb2.toString());
    String[] strArr = preferredActivity.mPref.mSetComponents;
    int i = 0;
    if (strArr != null) {
      sb.append("Sets:\n");
      int i2 = 0;
      while (i2 < strArr.length) {
        sb.append("  " + strArr[i2]);
        i2++;
        if (i2 < strArr.length) {
          sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
    }
    if (list != null) {
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append("Query sets: ");
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      int size = list.size();
      while (i < size) {
        ActivityInfo activityInfo = ((ResolveInfo) list.get(i)).activityInfo;
        sb.append("  " + activityInfo.packageName + "/" + activityInfo.name);
        i++;
        if (i < size) {
          sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
    }
    PmLog.logDebugInfoAndLogcat(
        sb.toString() + ", uid: " + Binder.getCallingUid() + ", pid: " + Binder.getCallingPid());
  }

  public static void logPreferenceChange(PreferredActivity preferredActivity, String str) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        str
            + ": "
            + preferredActivity.mPref.mShortComponent
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    String[] strArr = preferredActivity.mPref.mSetComponents;
    if (strArr != null) {
      sb.append("Sets:\n");
      int i = 0;
      while (i < strArr.length) {
        sb.append("  " + strArr[i]);
        i++;
        if (i < strArr.length) {
          sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
    }
    PmLog.logDebugInfoAndLogcat(
        sb.toString() + ", uid: " + Binder.getCallingUid() + ", pid: " + Binder.getCallingPid());
  }
}
