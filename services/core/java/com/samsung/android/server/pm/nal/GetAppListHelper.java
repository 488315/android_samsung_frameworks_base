package com.samsung.android.server.pm.nal;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.SettingBase;
import com.android.server.pm.SharedUserSetting;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GetAppListHelper {
    public final String[] EXEMPTED_PACKAGES = {"com.google.android.wearable.app.cn"};

    public static boolean checkCallingGetAppList(
            Context context, int i, boolean z, Function function) {
        if (z) {
            return false;
        }
        int userId = UserHandle.getUserId(i);
        if (ActivityManager.checkUidPermission("com.samsung.android.permission.GET_APP_LIST", i)
                != 0) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = (String[]) function.apply(Integer.valueOf(i));
            if (strArr != null && strArr.length != 0) {
                return (((PermissionManager) context.getSystemService(PermissionManager.class))
                                        .getPermissionFlags(
                                                strArr[0],
                                                "com.samsung.android.permission.GET_APP_LIST",
                                                UserHandle.of(userId))
                                & 8)
                        != 0;
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static int getTaskIdOfVisibleActivity(List list) {
        if (list == null) {
            return -1;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RecentTaskInfo taskInfo =
                    ((ActivityManager.AppTask) it.next()).getTaskInfo();
            if (taskInfo != null && taskInfo.isVisible && taskInfo.isRunning) {
                Intent intent = taskInfo.baseIntent;
                if ("android.intent.action.MAIN".equals(intent.getAction())
                        && intent.getCategories() != null
                        && (intent.getCategories().contains("android.intent.category.LAUNCHER")
                                || intent.getCategories()
                                        .contains("android.intent.category.LEANBACK_LAUNCHER")
                                || intent.getCategories()
                                        .contains("android.intent.category.CAR_LAUNCHER"))) {
                    return taskInfo.id;
                }
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ActivityManager.RecentTaskInfo taskInfo2 =
                    ((ActivityManager.AppTask) it2.next()).getTaskInfo();
            if (taskInfo2 != null && taskInfo2.isVisible && taskInfo2.isRunning) {
                return taskInfo2.id;
            }
        }
        return -1;
    }

    public static void requestGetAppListPermIfNeeded(Context context, int i, int i2) {
        List list;
        try {
            String packageNameByPid =
                    ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .getPackageNameByPid(i2);
            if (packageNameByPid != null) {
                try {
                    list =
                            ((ActivityTaskManagerInternal)
                                            LocalServices.getService(
                                                    ActivityTaskManagerInternal.class))
                                    .getAppTasks(i, packageNameByPid);
                } catch (Exception unused) {
                    Slog.d(
                            "GetAppListHelper",
                            "Failed to get app tasks for ".concat(packageNameByPid));
                    list = null;
                }
                if (list == null) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ActivityManager.RecentTaskInfo taskInfo =
                            ((ActivityManager.AppTask) it.next()).getTaskInfo();
                    if (taskInfo != null && taskInfo.isVisible && taskInfo.isRunning) {
                        showGetAppListConfirmDialog(
                                context,
                                packageNameByPid,
                                getTaskIdOfVisibleActivity(list),
                                UserHandle.getUserId(i));
                        return;
                    }
                }
            }
        } catch (Exception unused2) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Failed to request GET_APP_LIST for ", "GetAppListHelper");
        }
    }

    public static void showGetAppListConfirmDialog(Context context, String str, int i, int i2) {
        if (i < 0) {
            return;
        }
        try {
            Log.d("GetAppListHelper", "Request GET_APP_LIST permission for ".concat(str));
            Intent intent = new Intent("android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER");
            intent.putExtra(
                    "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES",
                    new String[] {"com.samsung.android.permission.GET_APP_LIST"});
            intent.setPackage("com.samsung.android.permissioncontroller");
            intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            intent.addFlags(268697600);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityOptions activityOptions = new ActivityOptions(new Bundle());
                activityOptions.setTaskOverlay(true, false);
                activityOptions.setLaunchTaskId(i);
                context.startActivityAsUser(intent, activityOptions.toBundle(), UserHandle.of(i2));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isExemptedPackage(int i, SettingBase settingBase) {
        String packageName;
        if (i == 1000 || i == 0) {
            return true;
        }
        if (settingBase == 0) {
            return false;
        }
        if ((settingBase.mPkgFlags & 1) != 0) {
            return true;
        }
        boolean z = settingBase instanceof SharedUserSetting;
        String[] strArr = this.EXEMPTED_PACKAGES;
        PackageStateInternal packageStateInternal = null;
        if (z) {
            ArraySet arraySet = ((SharedUserSetting) settingBase).mPackages.mStorage;
            if (arraySet != null && arraySet.size() > 0) {
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    String packageName2 = ((PackageStateInternal) it.next()).getPackageName();
                    if (packageName2 != null) {
                        for (String str : strArr) {
                            if (packageName2.equals(str)) {
                                return true;
                            }
                        }
                    }
                }
                packageStateInternal = (PackageStateInternal) arraySet.valueAt(0);
            }
        } else if ((settingBase instanceof PackageStateInternal)
                && (packageName =
                                (packageStateInternal = (PackageStateInternal) settingBase)
                                        .getPackageName())
                        != null) {
            for (String str2 : strArr) {
                if (packageName.equals(str2)) {
                    return true;
                }
            }
        }
        return (packageStateInternal == null
                        || packageStateInternal
                                .getPkg()
                                .getRequestedPermissions()
                                .contains("com.samsung.android.permission.GET_APP_LIST"))
                ? false
                : true;
    }
}
