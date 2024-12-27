package com.android.systemui.edgelighting.data;

import android.graphics.drawable.Drawable;

public final class AppInfo {
    public final String appName;
    public final String packageName;
    public final int priority;

    public AppInfo(String str, String str2, Drawable drawable, int i, boolean z) {
        this.appName = str;
        this.packageName = str2;
        this.priority = i;
    }
}
