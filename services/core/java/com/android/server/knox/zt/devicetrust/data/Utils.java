package com.android.server.knox.zt.devicetrust.data;

import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.ServiceManager;
import com.samsung.android.server.pm.PmServerUtils;

/* loaded from: classes2.dex */
public class Utils {
  public static final String EMPTY_STRING = "";
  public IPackageManager mPm;

  public class InstanceHolder {
    public static final Utils INSTANCE = new Utils();
  }

  public static String nullSafe(String str) {
    return str != null ? str : "";
  }

  public Utils() {
    this.mPm = getPackageManager();
  }

  public final IPackageManager getPackageManager() {
    return IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
  }

  public String getSecurityContextForPid(int i) {
    String str;
    try {
      str = SELinux.getPidContext(i);
    } catch (Throwable unused) {
      str = null;
    }
    return nullSafe(str);
  }

  public String getProcessNameForPid(int i) {
    String str;
    try {
      str = PmServerUtils.getProcessNameForPid(i);
    } catch (Throwable unused) {
      str = null;
    }
    return nullSafe(str);
  }

  public String getPackageNameForUid(int i) {
    String nameForUid;
    IPackageManager iPackageManager = this.mPm;
    if (iPackageManager != null) {
      try {
        nameForUid = iPackageManager.getNameForUid(i);
      } catch (RemoteException unused) {
      }
      return nullSafe(nameForUid);
    }
    nameForUid = null;
    return nullSafe(nameForUid);
  }

  public static Utils getInstance() {
    return InstanceHolder.INSTANCE;
  }
}
