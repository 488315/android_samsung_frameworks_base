package com.samsung.android.server.pm.desktopmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import com.android.server.pm.WatchedIntentFilter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public abstract class DesktopModeUtils {
    public static boolean filterHomeActivitiesByDesktopMode(boolean z, boolean z2, String str, List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (z) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo != null && KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(activityInfo.packageName)) {
                    list.clear();
                    list.add(resolveInfo);
                    return true;
                }
            } else {
                ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                if (activityInfo2 != null && str.equals(activityInfo2.packageName)) {
                    if (z2) {
                        list.clear();
                        list.add(resolveInfo);
                        return true;
                    }
                    list.remove(resolveInfo);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isReplacingPreferredBlockedByDesktopMode(WatchedIntentFilter watchedIntentFilter, ComponentName componentName, boolean z, String str) {
        return (componentName != null && str.equals(componentName.getPackageName())) || z;
    }

    public static void sendSessionCommitBroadcastToDesktopLauncherIfNeeded(Context context, ComponentName componentName, String str, PackageInstaller.SessionInfo sessionInfo, int i, Supplier supplier) {
        if (i != 0 || componentName == null || str.equals(componentName.getPackageName()) || !((Boolean) supplier.get()).booleanValue()) {
            return;
        }
        context.sendBroadcastAsUser(new Intent("android.content.pm.action.SESSION_COMMITTED").putExtra("android.content.pm.extra.SESSION", sessionInfo).putExtra("android.intent.extra.USER", UserHandle.of(i)).setPackage(str), UserHandle.of(i));
    }

    public static boolean isHomeFilter(WatchedIntentFilter watchedIntentFilter) {
        return watchedIntentFilter.hasAction("android.intent.action.MAIN") && watchedIntentFilter.hasCategory("android.intent.category.HOME");
    }

    public static boolean isNewDexMode(Context context) {
        return context.getResources().getConfiguration().dexMode == 3;
    }
}
