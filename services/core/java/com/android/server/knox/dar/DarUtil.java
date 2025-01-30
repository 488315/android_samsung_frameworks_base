package com.android.server.knox.dar;

import android.content.pm.UserInfo;
import android.net.INetd;
import android.os.Binder;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes2.dex */
public final class DarUtil {
  public static boolean mIsDeviceOwnerEnabled = false;

  public static void updateDeviceOwnerStatus(boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        boolean isDoEnabled = SemPersonaManager.isDoEnabled(0);
        Log.d("DARUtil", "Knox device_owner property : " + isDoEnabled);
        boolean z2 = SystemProperties.getBoolean("ro.device_owner", false);
        Log.d("DARUtil", "DPM device_owner property  : " + z2);
        Log.d("DARUtil", "Extra factor               : " + z);
        setDoEnabled(isDoEnabled || z2 || z);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static void setDoEnabled(boolean z) {
    mIsDeviceOwnerEnabled = z;
  }

  public static boolean isDoEnabled() {
    boolean z = mIsDeviceOwnerEnabled;
    if (z) {
      return z;
    }
    Log.d("DARUtil", "Device owner status not updated yet...");
    return SemPersonaManager.isDoEnabled(0);
  }

  public static boolean isDeviceOwnerUser(int i) {
    return i == 0 && isDoEnabled();
  }

  public static boolean isEnterpriseUser(UserInfo userInfo) {
    boolean z =
        !(!userInfo.isManagedProfile()
                || userInfo.isSdpNotSupportedSecureFolder()
                || userInfo.isGuest()
                || userInfo.isDualAppProfile()
                || userInfo.isBMode())
            || isDeviceOwnerUser(userInfo.id);
    if (!z) {
      Log.d("DARUtil", "Not an enterprise user : " + userInfo.id);
    } else {
      Log.d("DARUtil", "is enterprise user : " + userInfo.id);
    }
    return z;
  }

  public static boolean isLegacyContainerUser(UserInfo userInfo) {
    boolean isSecureFolder = userInfo.isSecureFolder();
    if (isSecureFolder) {
      Log.d("DARUtil", "Identified as legacy type container user : " + userInfo.id);
    }
    return isSecureFolder;
  }

  public static LockscreenCredential getSafe(LockscreenCredential lockscreenCredential) {
    return lockscreenCredential == null ? LockscreenCredential.createNone() : lockscreenCredential;
  }

  public static String credentialTypeToString(int i) {
    if (i == -1) {
      return "None";
    }
    if (i == 1) {
      return "Pattern";
    }
    if (i == 3) {
      return "Pin";
    }
    if (i == 4) {
      return "Password";
    }
    return "Unknown " + i;
  }

  public static boolean isDaemonRunning(String str) {
    String str2;
    boolean z = false;
    if (str == null || str.isEmpty()) {
      Log.e("DARUtil", "isDaemonRunning() - Invalid service name");
      return false;
    }
    String str3 = "init.svc." + str;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        str2 = SystemProperties.get(str3);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (str2 != null && !str2.isEmpty()) {
        z = INetd.IF_FLAG_RUNNING.equals(str2);
        return z;
      }
      Log.e("DARUtil", "isDaemonRunning() - Service not found");
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static void setSystemPropertyBoolean(String str, boolean z) {
    if (str == null || str.isEmpty()) {
      Log.e("DARUtil", "Invalid property");
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        SystemProperties.set(str, Boolean.toString(z));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
