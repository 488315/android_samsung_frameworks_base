package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ActivityInfoWrapper {
    private static final ActivityInfoWrapper sInstance = new ActivityInfoWrapper();

    public static ActivityInfoWrapper getInstance() {
        return sInstance;
    }

    public ComponentName getComponentName(ActivityInfo activityInfo) {
        return activityInfo.getComponentName();
    }
}
