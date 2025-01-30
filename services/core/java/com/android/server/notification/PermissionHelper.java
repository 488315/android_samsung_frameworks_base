package com.android.server.notification;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.RemoteException;
import android.permission.IPermissionManager;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class PermissionHelper {
  public final Context mContext;
  public final IPackageManager mPackageManager;
  public final IPermissionManager mPermManager;
  public final PermissionManagerServiceInternal mPmi =
      (PermissionManagerServiceInternal)
          LocalServices.getService(PermissionManagerServiceInternal.class);

  public PermissionHelper(
      Context context, IPackageManager iPackageManager, IPermissionManager iPermissionManager) {
    this.mContext = context;
    this.mPackageManager = iPackageManager;
    this.mPermManager = iPermissionManager;
  }

  public boolean hasPermission(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mPmi.checkPostNotificationsPermissionGrantedOrLegacyAccess(i) == 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean hasRequestedPermission(String str, String str2, int i) {
    PackageInfo packageInfo;
    String[] strArr;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        packageInfo = this.mPackageManager.getPackageInfo(str2, 4096L, i);
      } catch (RemoteException e) {
        Slog.d("PermissionHelper", "Could not reach system server", e);
      }
      if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null) {
        for (String str3 : strArr) {
          if (str.equals(str3)) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
          }
        }
        return false;
      }
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public Set getAppsRequestingPermission(int i) {
    HashSet hashSet = new HashSet();
    for (PackageInfo packageInfo : getInstalledPackages(i)) {
      String[] strArr = packageInfo.requestedPermissions;
      if (strArr != null) {
        int length = strArr.length;
        int i2 = 0;
        while (true) {
          if (i2 >= length) {
            break;
          }
          if ("android.permission.POST_NOTIFICATIONS".equals(strArr[i2])) {
            hashSet.add(
                new Pair(
                    Integer.valueOf(packageInfo.applicationInfo.uid), packageInfo.packageName));
            break;
          }
          i2++;
        }
      }
    }
    return hashSet;
  }

  public final List getInstalledPackages(int i) {
    ParceledListSlice parceledListSlice;
    try {
      parceledListSlice = this.mPackageManager.getInstalledPackages(4096L, i);
    } catch (RemoteException e) {
      Slog.d("PermissionHelper", "Could not reach system server", e);
      parceledListSlice = null;
    }
    if (parceledListSlice == null) {
      return Collections.emptyList();
    }
    return parceledListSlice.getList();
  }

  public Set getAppsGrantedPermission(int i) {
    ParceledListSlice parceledListSlice;
    HashSet hashSet = new HashSet();
    try {
      parceledListSlice =
          this.mPackageManager.getPackagesHoldingPermissions(
              new String[] {"android.permission.POST_NOTIFICATIONS"}, 0L, i);
    } catch (RemoteException e) {
      Slog.e("PermissionHelper", "Could not reach system server", e);
      parceledListSlice = null;
    }
    if (parceledListSlice == null) {
      return hashSet;
    }
    for (PackageInfo packageInfo : parceledListSlice.getList()) {
      hashSet.add(
          new Pair(Integer.valueOf(packageInfo.applicationInfo.uid), packageInfo.packageName));
    }
    return hashSet;
  }

  public ArrayMap getNotificationPermissionValues(int i) {
    ArrayMap arrayMap = new ArrayMap();
    Set<Pair> appsRequestingPermission = getAppsRequestingPermission(i);
    Set appsGrantedPermission = getAppsGrantedPermission(i);
    for (Pair pair : appsRequestingPermission) {
      arrayMap.put(
          pair,
          new Pair(
              Boolean.valueOf(appsGrantedPermission.contains(pair)),
              Boolean.valueOf(isPermissionUserSet((String) pair.second, i))));
    }
    return arrayMap;
  }

  public void setNotificationPermission(String str, int i, boolean z, boolean z2) {
    setNotificationPermission(str, i, z, z2, false);
  }

  public void setNotificationPermission(String str, int i, boolean z, boolean z2, boolean z3) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
      } catch (RemoteException e) {
        Slog.e("PermissionHelper", "Could not reach system server", e);
      }
      if (packageRequestsNotificationPermission(str, i)
          && !isPermissionFixed(str, i)
          && (!isPermissionGrantedByDefaultOrRole(str, i) || z2)) {
        boolean hasPermission = hasPermission(this.mPackageManager.getPackageUid(str, 0L, i));
        Slog.d("PermissionHelper", "pkg = " + str + ", currentlyGranted = " + hasPermission);
        Slog.d("PermissionHelper", "reviewRequired = " + z3 + ", grant = " + z);
        if (!z3) {
          if (z && !hasPermission) {
            this.mPermManager.grantRuntimePermission(
                str, "android.permission.POST_NOTIFICATIONS", i);
          } else if (!z && hasPermission) {
            this.mPermManager.revokeRuntimePermission(
                str, "android.permission.POST_NOTIFICATIONS", i, "PermissionHelper");
          }
          if (z2) {
            this.mPermManager.updatePermissionFlags(
                str, "android.permission.POST_NOTIFICATIONS", 1, 1, true, i);
          } else {
            this.mPermManager.updatePermissionFlags(
                str, "android.permission.POST_NOTIFICATIONS", 1, 0, true, i);
          }
          this.mPermManager.updatePermissionFlags(
              str, "android.permission.POST_NOTIFICATIONS", 64, 0, true, i);
        } else {
          if ("com.sec.android.easyMover".equals(str)) {
            return;
          }
          if (!z && hasPermission) {
            this.mPermManager.revokeRuntimePermission(
                str, "android.permission.POST_NOTIFICATIONS", i, "PermissionHelper");
          }
          if (z && !hasPermission) {
            this.mPermManager.updatePermissionFlags(
                str, "android.permission.POST_NOTIFICATIONS", 1, 0, true, i);
            this.mPermManager.updatePermissionFlags(
                str, "android.permission.POST_NOTIFICATIONS", 64, 64, true, i);
          }
        }
        return;
      }
      Slog.d("PermissionHelper", "pkg = " + str + ", setNotificationPermission return!!");
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setNotificationPermission(PackagePermission packagePermission) {
    String str;
    if (packagePermission == null
        || (str = packagePermission.packageName) == null
        || isPermissionFixed(str, packagePermission.userId)) {
      return;
    }
    setNotificationPermission(
        packagePermission.packageName,
        packagePermission.userId,
        packagePermission.granted,
        true,
        true);
  }

  public boolean isPermissionFixed(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int permissionFlags =
          this.mPermManager.getPermissionFlags(str, "android.permission.POST_NOTIFICATIONS", i);
      return ((permissionFlags & 16) == 0 && (permissionFlags & 4) == 0) ? false : true;
    } catch (RemoteException e) {
      Slog.e("PermissionHelper", "Could not reach system server", e);
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isPermissionUserSet(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return (this.mPermManager.getPermissionFlags(str, "android.permission.POST_NOTIFICATIONS", i)
              & 3)
          != 0;
    } catch (RemoteException e) {
      Slog.e("PermissionHelper", "Could not reach system server", e);
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isPermissionGrantedByDefaultOrRole(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return (this.mPermManager.getPermissionFlags(str, "android.permission.POST_NOTIFICATIONS", i)
              & 32800)
          != 0;
    } catch (RemoteException e) {
      Slog.e("PermissionHelper", "Could not reach system server", e);
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean packageRequestsNotificationPermission(String str, int i) {
    try {
      PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 4096L, i);
      if (packageInfo != null) {
        return ArrayUtils.contains(
            packageInfo.requestedPermissions, "android.permission.POST_NOTIFICATIONS");
      }
      return false;
    } catch (RemoteException e) {
      Slog.e("PermissionHelper", "Could not reach system server", e);
      return false;
    }
  }

  public class PackagePermission {
    public final boolean granted;
    public final String packageName;
    public final int userId;
    public final boolean userModifiedSettings;

    public PackagePermission(String str, int i, boolean z, boolean z2) {
      this.packageName = str;
      this.userId = i;
      this.granted = z;
      this.userModifiedSettings = z2;
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      PackagePermission packagePermission = (PackagePermission) obj;
      return this.userId == packagePermission.userId
          && this.granted == packagePermission.granted
          && this.userModifiedSettings == packagePermission.userModifiedSettings
          && Objects.equals(this.packageName, packagePermission.packageName);
    }

    public int hashCode() {
      return Objects.hash(
          this.packageName,
          Integer.valueOf(this.userId),
          Boolean.valueOf(this.granted),
          Boolean.valueOf(this.userModifiedSettings));
    }

    public String toString() {
      return "PackagePermission{packageName='"
          + this.packageName
          + "', userId="
          + this.userId
          + ", granted="
          + this.granted
          + ", userSet="
          + this.userModifiedSettings
          + '}';
    }
  }
}
