package com.android.server.pm.pkg;

/* loaded from: classes3.dex */
public abstract class SELinuxUtil {
  public static String getSeinfoUser(PackageUserState packageUserState) {
    return packageUserState.isInstantApp() ? ":ephemeralapp:complete" : ":complete";
  }
}
