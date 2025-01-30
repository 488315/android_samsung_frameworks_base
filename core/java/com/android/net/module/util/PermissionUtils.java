package com.android.net.module.util;

import android.Manifest;
import android.content.Context;
import android.content.p002pm.PackageInfo;
import android.media.MediaMetrics;
import android.net.NetworkStack;
import android.p009os.Binder;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public final class PermissionUtils {
  public static boolean checkAnyPermissionOf(Context context, String... permissions) {
    for (String permission : permissions) {
      if (context.checkCallingOrSelfPermission(permission) == 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkAnyPermissionOf(
      Context context, int pid, int uid, String... permissions) {
    for (String permission : permissions) {
      if (context.checkPermission(permission, pid, uid) == 0) {
        return true;
      }
    }
    return false;
  }

  public static void enforceAnyPermissionOf(Context context, String... permissions) {
    if (!checkAnyPermissionOf(context, permissions)) {
      throw new SecurityException(
          "Requires one of the following permissions: "
              + String.join(", ", permissions)
              + MediaMetrics.SEPARATOR);
    }
  }

  public static void enforceNetworkStackPermission(Context context) {
    enforceNetworkStackPermissionOr(context, new String[0]);
  }

  public static void enforceNetworkStackPermissionOr(Context context, String... otherPermissions) {
    ArrayList<String> permissions = new ArrayList<>(Arrays.asList(otherPermissions));
    permissions.add(Manifest.permission.NETWORK_STACK);
    permissions.add(NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK);
    enforceAnyPermissionOf(context, (String[]) permissions.toArray(new String[0]));
  }

  public static void enforceRestrictedNetworkPermission(Context context, String message) {
    context.enforceCallingOrSelfPermission(
        Manifest.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS, message);
  }

  public static void enforceAccessNetworkStatePermission(Context context, String message) {
    context.enforceCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE, message);
  }

  public static boolean checkDumpPermission(Context context, String tag, PrintWriter pw) {
    if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
      pw.println(
          "Permission Denial: can't dump "
              + tag
              + " from from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " due to missing android.permission.DUMP permission");
      return false;
    }
    return true;
  }

  public static void enforceSystemFeature(Context context, String feature, String errorMessage) {
    boolean hasSystemFeature = context.getPackageManager().hasSystemFeature(feature);
    if (!hasSystemFeature) {
      if (errorMessage == null) {
        throw new UnsupportedOperationException();
      }
      throw new UnsupportedOperationException(errorMessage);
    }
  }

  public static List<String> getGrantedPermissions(PackageInfo packageInfo) {
    if (packageInfo.requestedPermissions == null) {
      return Collections.emptyList();
    }
    ArrayList<String> result = new ArrayList<>(packageInfo.requestedPermissions.length);
    for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
      if ((packageInfo.requestedPermissionsFlags[i] & 2) != 0) {
        result.add(packageInfo.requestedPermissions[i]);
      }
    }
    return result;
  }
}
