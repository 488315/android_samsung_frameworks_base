package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.pm.ApplicationInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ApplicationInfoCompat {
    private final ApplicationInfo mWrapper;

    public ApplicationInfoCompat(ApplicationInfo applicationInfo) {
        this.mWrapper = applicationInfo;
    }

    public boolean isInstantApp() {
        return this.mWrapper.isInstantApp();
    }
}
