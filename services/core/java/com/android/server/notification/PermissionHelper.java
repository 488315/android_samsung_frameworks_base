package com.android.server.notification;

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
import com.android.server.pm.permission.PermissionManagerService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PermissionHelper {
    public final IPackageManager mPackageManager;
    public final IPermissionManager mPermManager;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPmi =
            (PermissionManagerService.PermissionManagerServiceInternalImpl)
                    LocalServices.getService(
                            PermissionManagerService.PermissionManagerServiceInternalImpl.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackagePermission {
        public final boolean granted;
        public final String packageName;
        public final int userId;
        public final boolean userModifiedSettings;

        public PackagePermission(String str, boolean z, boolean z2, int i) {
            this.packageName = str;
            this.userId = i;
            this.granted = z;
            this.userModifiedSettings = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || PackagePermission.class != obj.getClass()) {
                return false;
            }
            PackagePermission packagePermission = (PackagePermission) obj;
            return this.userId == packagePermission.userId
                    && this.granted == packagePermission.granted
                    && this.userModifiedSettings == packagePermission.userModifiedSettings
                    && Objects.equals(this.packageName, packagePermission.packageName);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.packageName,
                    Integer.valueOf(this.userId),
                    Boolean.valueOf(this.granted),
                    Boolean.valueOf(this.userModifiedSettings));
        }

        public final String toString() {
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

    public PermissionHelper(
            IPackageManager iPackageManager, IPermissionManager iPermissionManager) {
        this.mPackageManager = iPackageManager;
        this.mPermManager = iPermissionManager;
    }

    public final ArrayMap getNotificationPermissionValues(int i) {
        ParceledListSlice parceledListSlice;
        ArrayMap arrayMap = new ArrayMap();
        HashSet hashSet = new HashSet();
        ParceledListSlice parceledListSlice2 = null;
        try {
            parceledListSlice = this.mPackageManager.getInstalledPackages(4096L, i);
        } catch (RemoteException e) {
            Slog.d("PermissionHelper", "Could not reach system server", e);
            parceledListSlice = null;
        }
        for (PackageInfo packageInfo :
                parceledListSlice == null ? Collections.emptyList() : parceledListSlice.getList()) {
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
                                        Integer.valueOf(packageInfo.applicationInfo.uid),
                                        packageInfo.packageName));
                        break;
                    }
                    i2++;
                }
            }
        }
        HashSet hashSet2 = new HashSet();
        try {
            parceledListSlice2 =
                    this.mPackageManager.getPackagesHoldingPermissions(
                            new String[] {"android.permission.POST_NOTIFICATIONS"}, 0L, i);
        } catch (RemoteException e2) {
            Slog.e("PermissionHelper", "Could not reach system server", e2);
        }
        if (parceledListSlice2 != null) {
            for (PackageInfo packageInfo2 : parceledListSlice2.getList()) {
                hashSet2.add(
                        new Pair(
                                Integer.valueOf(packageInfo2.applicationInfo.uid),
                                packageInfo2.packageName));
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayMap.put(
                    pair,
                    new Pair(
                            Boolean.valueOf(hashSet2.contains(pair)),
                            Boolean.valueOf(isPermissionUserSet(i, (String) pair.second))));
        }
        return arrayMap;
    }

    public final boolean hasPermission(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPmi.checkPostNotificationsPermissionGrantedOrLegacyAccess(i) == 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasRequestedPermission(int i, String str) {
        PackageInfo packageInfo;
        String[] strArr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageInfo = this.mPackageManager.getPackageInfo(str, 4096L, i);
            } catch (RemoteException e) {
                Slog.d("PermissionHelper", "Could not reach system server", e);
            }
            if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null) {
                for (String str2 : strArr) {
                    if ("android.permission.USE_FULL_SCREEN_INTENT".equals(str2)) {
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

    public final boolean isPermissionFixed(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int permissionFlags =
                    this.mPermManager.getPermissionFlags(
                            str, "android.permission.POST_NOTIFICATIONS", "default:0", i);
            return ((permissionFlags & 16) == 0 && (permissionFlags & 4) == 0) ? false : true;
        } catch (RemoteException e) {
            Slog.e("PermissionHelper", "Could not reach system server", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPermissionGrantedByDefaultOrRole(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (this.mPermManager.getPermissionFlags(
                                    str, "android.permission.POST_NOTIFICATIONS", "default:0", i)
                            & 32800)
                    != 0;
        } catch (RemoteException e) {
            Slog.e("PermissionHelper", "Could not reach system server", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPermissionUserSet(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (this.mPermManager.getPermissionFlags(
                                    str, "android.permission.POST_NOTIFICATIONS", "default:0", i)
                            & 3)
                    != 0;
        } catch (RemoteException e) {
            Slog.e("PermissionHelper", "Could not reach system server", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean packageRequestsNotificationPermission(int i, String str) {
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

    public final void setNotificationPermission(String str, boolean z, boolean z2, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } catch (RemoteException e) {
                Slog.e("PermissionHelper", "Could not reach system server", e);
            }
            if (!packageRequestsNotificationPermission(i, str) || isPermissionFixed(str, i)) {
                Slog.d("PermissionHelper", "pkg = " + str + ", setNotificationPermission return!!");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            isPermissionGrantedByDefaultOrRole(i, str);
            boolean hasPermission = hasPermission(this.mPackageManager.getPackageUid(str, 0L, i));
            Slog.d("PermissionHelper", "pkg = " + str + ", currentlyGranted = " + hasPermission);
            Slog.d("PermissionHelper", "reviewRequired = " + z2 + ", grant = " + z);
            if (!z2) {
                if (z && !hasPermission) {
                    this.mPermManager.grantRuntimePermission(
                            str, "android.permission.POST_NOTIFICATIONS", "default:0", i);
                } else if (!z && hasPermission) {
                    this.mPermManager.revokeRuntimePermission(
                            str,
                            "android.permission.POST_NOTIFICATIONS",
                            "default:0",
                            i,
                            "PermissionHelper");
                }
                this.mPermManager.updatePermissionFlags(
                        str, "android.permission.POST_NOTIFICATIONS", 1, 1, true, "default:0", i);
                this.mPermManager.updatePermissionFlags(
                        str, "android.permission.POST_NOTIFICATIONS", 64, 0, true, "default:0", i);
            } else {
                if ("com.sec.android.easyMover".equals(str)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                }
                if (!z && hasPermission) {
                    this.mPermManager.revokeRuntimePermission(
                            str,
                            "android.permission.POST_NOTIFICATIONS",
                            "default:0",
                            i,
                            "PermissionHelper");
                }
                if (z && !hasPermission) {
                    this.mPermManager.updatePermissionFlags(
                            str,
                            "android.permission.POST_NOTIFICATIONS",
                            1,
                            0,
                            true,
                            "default:0",
                            i);
                    this.mPermManager.updatePermissionFlags(
                            str,
                            "android.permission.POST_NOTIFICATIONS",
                            64,
                            64,
                            true,
                            "default:0",
                            i);
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
