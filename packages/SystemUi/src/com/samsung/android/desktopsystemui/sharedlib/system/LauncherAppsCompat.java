package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.pm.LauncherApps;
import android.os.UserHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LauncherAppsCompat {
    private final LauncherApps mWrapper;

    public LauncherAppsCompat(LauncherApps launcherApps) {
        this.mWrapper = launcherApps;
    }

    public AppUsageLimitCompat getAppUsageLimit(String str, UserHandle userHandle) {
        LauncherApps.AppUsageLimit appUsageLimit = this.mWrapper.getAppUsageLimit(str, userHandle);
        if (appUsageLimit != null) {
            return new AppUsageLimitCompat(appUsageLimit);
        }
        return null;
    }
}
