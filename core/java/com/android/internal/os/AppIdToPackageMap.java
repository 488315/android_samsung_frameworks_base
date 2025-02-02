package com.android.internal.os;

import android.app.AppGlobals;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.SparseArray;
import java.util.List;

/* loaded from: classes5.dex */
public final class AppIdToPackageMap {
  private final SparseArray<String> mAppIdToPackageMap;

  public AppIdToPackageMap(SparseArray<String> appIdToPackageMap) {
    this.mAppIdToPackageMap = appIdToPackageMap;
  }

  public static AppIdToPackageMap getSnapshot() {
    try {
      List<PackageInfo> packages =
          AppGlobals.getPackageManager().getInstalledPackages(794624L, 0).getList();
      SparseArray<String> map = new SparseArray<>();
      for (PackageInfo pkg : packages) {
        int uid = pkg.applicationInfo.uid;
        if (pkg.sharedUserId != null && map.indexOfKey(uid) >= 0) {
          map.put(uid, "shared:" + pkg.sharedUserId);
        } else {
          map.put(uid, pkg.packageName);
        }
      }
      return new AppIdToPackageMap(map);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public String mapAppId(int appId) {
    String pkgName = this.mAppIdToPackageMap.get(appId);
    return pkgName == null ? String.valueOf(appId) : pkgName;
  }

  public String mapUid(int uid) {
    int appId = UserHandle.getAppId(uid);
    String pkgName = this.mAppIdToPackageMap.get(appId);
    String uidStr = UserHandle.formatUid(uid);
    return pkgName == null ? uidStr : pkgName + '/' + uidStr;
  }
}
