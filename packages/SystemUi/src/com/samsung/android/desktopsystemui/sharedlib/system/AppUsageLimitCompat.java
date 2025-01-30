package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.pm.LauncherApps;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppUsageLimitCompat {
    private final LauncherApps.AppUsageLimit mWrapper;

    public AppUsageLimitCompat(LauncherApps.AppUsageLimit appUsageLimit) {
        this.mWrapper = appUsageLimit;
    }

    public long getTotalUsageLimit() {
        return this.mWrapper.getTotalUsageLimit();
    }

    public long getUsageRemaining() {
        return this.mWrapper.getUsageRemaining();
    }
}
