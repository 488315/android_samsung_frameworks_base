package com.android.server.wm;

import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;

public final class AlertWindowNotification {
    public static NotificationChannelGroup sChannelGroup;
    public static int sNextRequestCode;
    public final NotificationManager mNotificationManager;
    public final String mNotificationTag;
    public final String mPackageName;
    public boolean mPosted;
    public final int mRequestCode;
    public final WindowManagerService mService;

    public AlertWindowNotification(WindowManagerService windowManagerService, String str) {
        this.mService = windowManagerService;
        this.mPackageName = str;
        this.mNotificationManager =
                (NotificationManager)
                        windowManagerService.mContext.getSystemService("notification");
        this.mNotificationTag =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "com.android.server.wm.AlertWindowNotification - ", str);
        int i = sNextRequestCode;
        sNextRequestCode = i + 1;
        this.mRequestCode = i;
    }
}
