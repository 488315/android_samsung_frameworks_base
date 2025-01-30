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
import com.android.server.LocalServices;
import com.android.server.pm.SettingBase;
import com.android.server.pm.SharedUserSetting;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class GetAppListHelper {
    public final String[] EXEMPTED_PACKAGES = {"com.google.android.wearable.app.cn"};

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isExemptedPackage(int i, SettingBase settingBase) {
        if (i == 1000 || i == 0) {
            return true;
        }
        if (settingBase == 0) {
            return false;
        }
        if ((settingBase.getFlags() & 1) != 0) {
            return true;
        }
        PackageStateInternal packageStateInternal = null;
        if (settingBase instanceof SharedUserSetting) {
            ArraySet packageStates = ((SharedUserSetting) settingBase).getPackageStates();
            if (packageStates != null && packageStates.size() > 0) {
                Iterator it = packageStates.iterator();
                while (it.hasNext()) {
                    if (containsInAllowlist(((PackageStateInternal) it.next()).getPackageName())) {
                        return true;
                    }
                }
                packageStateInternal = (PackageStateInternal) packageStates.valueAt(0);
            }
        } else if (settingBase instanceof PackageStateInternal) {
            packageStateInternal = (PackageStateInternal) settingBase;
            if (containsInAllowlist(packageStateInternal.getPackageName())) {
                return true;
            }
        }
        return (packageStateInternal == null || packageStateInternal.getPkg().getRequestedPermissions().contains("com.samsung.android.permission.GET_APP_LIST")) ? false : true;
    }

    public final boolean containsInAllowlist(String str) {
        if (str == null) {
            return false;
        }
        for (String str2 : this.EXEMPTED_PACKAGES) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public void requestGetAppListPermIfNeeded(Context context, int i, int i2) {
        try {
            String packageNameOfPid = getPackageNameOfPid(i2);
            if (packageNameOfPid != null) {
                List appTasks = getAppTasks(packageNameOfPid, i);
                if (hasPackageVisibleActivity(appTasks)) {
                    showGetAppListConfirmDialog(context, packageNameOfPid, getTaskIdOfVisibleActivity(appTasks), UserHandle.getUserId(i));
                }
            }
        } catch (Exception unused) {
            Slog.d("GetAppListHelper", "Failed to request GET_APP_LIST for " + i);
        }
    }

    public boolean checkCallingGetAppList(Context context, int i, boolean z, Function function) {
        if (z) {
            return false;
        }
        int userId = UserHandle.getUserId(i);
        if (ActivityManager.checkUidPermission("com.samsung.android.permission.GET_APP_LIST", i) != 0) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = (String[]) function.apply(Integer.valueOf(i));
            if (strArr != null && strArr.length != 0) {
                return (((PermissionManager) context.getSystemService(PermissionManager.class)).getPermissionFlags(strArr[0], "com.samsung.android.permission.GET_APP_LIST", UserHandle.of(userId)) & 8) != 0;
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getPackageNameOfPid(int i) {
        return ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getPackageNameByPid(i);
    }

    public final boolean hasPackageVisibleActivity(List list) {
        if (list == null) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RecentTaskInfo taskInfo = ((ActivityManager.AppTask) it.next()).getTaskInfo();
            if (taskInfo != null && taskInfo.isVisible && taskInfo.isRunning) {
                return true;
            }
        }
        return false;
    }

    public final int getTaskIdOfVisibleActivity(List list) {
        if (list == null) {
            return -1;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RecentTaskInfo taskInfo = ((ActivityManager.AppTask) it.next()).getTaskInfo();
            if (taskInfo != null && taskInfo.isVisible && taskInfo.isRunning && isLauncherIntent(taskInfo.baseIntent)) {
                return taskInfo.id;
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ActivityManager.RecentTaskInfo taskInfo2 = ((ActivityManager.AppTask) it2.next()).getTaskInfo();
            if (taskInfo2 != null && taskInfo2.isVisible && taskInfo2.isRunning) {
                return taskInfo2.id;
            }
        }
        return -1;
    }

    public final boolean isLauncherIntent(Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.getCategories() != null && (intent.getCategories().contains("android.intent.category.LAUNCHER") || intent.getCategories().contains("android.intent.category.LEANBACK_LAUNCHER") || intent.getCategories().contains("android.intent.category.CAR_LAUNCHER"));
    }

    public final List getAppTasks(String str, int i) {
        try {
            return ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getAppTasks(str, i);
        } catch (Exception unused) {
            Slog.d("GetAppListHelper", "Failed to get app tasks for " + str);
            return null;
        }
    }

    public final void showGetAppListConfirmDialog(Context context, String str, int i, int i2) {
        if (i < 0) {
            return;
        }
        try {
            Log.d("GetAppListHelper", "Request GET_APP_LIST permission for " + str);
            Intent intent = new Intent("android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER");
            intent.putExtra("android.content.pm.extra.REQUEST_PERMISSIONS_NAMES", new String[]{"com.samsung.android.permission.GET_APP_LIST"});
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
}
