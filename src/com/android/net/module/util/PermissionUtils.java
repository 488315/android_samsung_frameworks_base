package com.android.net.module.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaMetrics;
import android.net.NetworkStack;
import android.os.Binder;
import android.os.UserHandle;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public final class PermissionUtils {
    public static boolean hasAnyPermissionOf(Context context, String... strArr) {
        for (String str : strArr) {
            if (context.checkCallingOrSelfPermission(str) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnyPermissionOf(Context context, int i, int i2, String... strArr) {
        for (String str : strArr) {
            if (context.checkPermission(str, i, i2) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void enforceAnyPermissionOf(Context context, String... strArr) {
        if (hasAnyPermissionOf(context, strArr)) {
            return;
        }
        throw new SecurityException("Requires one of the following permissions: " + String.join(", ", strArr) + MediaMetrics.SEPARATOR);
    }

    public static void enforceNetworkStackPermission(Context context) {
        enforceNetworkStackPermissionOr(context, new String[0]);
    }

    public static void enforceNetworkStackPermissionOr(Context context, String... strArr) {
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        arrayList.add(Manifest.permission.NETWORK_STACK);
        arrayList.add(NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK);
        enforceAnyPermissionOf(context, (String[]) arrayList.toArray(new String[0]));
    }

    public static void enforceRestrictedNetworkPermission(Context context, String str) {
        context.enforceCallingOrSelfPermission(Manifest.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS, str);
    }

    public static void enforceAccessNetworkStatePermission(Context context, String str) {
        context.enforceCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE, str);
    }

    public static boolean hasDumpPermission(Context context, String str, PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) == 0) {
            return true;
        }
        printWriter.println("Permission Denial: can't dump " + str + " from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
        return false;
    }

    public static void enforceSystemFeature(Context context, String str, String str2) {
        if (context.getPackageManager().hasSystemFeature(str)) {
            return;
        }
        if (str2 == null) {
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException(str2);
    }

    public static List<String> getGrantedPermissions(PackageInfo packageInfo) {
        if (packageInfo.requestedPermissions == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(packageInfo.requestedPermissions.length);
        for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
            if ((packageInfo.requestedPermissionsFlags[i] & 2) != 0) {
                arrayList.add(packageInfo.requestedPermissions[i]);
            }
        }
        return arrayList;
    }

    public static void enforcePackageNameMatchesUid(Context context, int i, String str) {
        if (getAppUid(context, str, UserHandle.getUserHandleForUid(i)) == i) {
            return;
        }
        throw new SecurityException(str + " does not belong to uid " + i);
    }

    private static int getAppUid(Context context, String str, UserHandle userHandle) {
        PackageManager packageManager = context.createContextAsUser(userHandle, 0).getPackageManager();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int packageUid = packageManager.getPackageUid(str, 0);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return packageUid;
        } catch (PackageManager.NameNotFoundException unused) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return -1;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }
}
