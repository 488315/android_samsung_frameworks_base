package com.android.server.notification.toast;

import android.os.Binder;
import android.os.IBinder;

import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ToastRecord {
    public final int displayId;
    public final boolean isSystemToast;
    public int mDuration;
    public final NotificationManagerService mNotificationManager;
    public final int pid;
    public final String pkg;
    public final IBinder token;
    public final int uid;
    public final Binder windowToken;

    public ToastRecord(
            NotificationManagerService notificationManagerService,
            int i,
            int i2,
            String str,
            boolean z,
            IBinder iBinder,
            int i3,
            Binder binder,
            int i4) {
        this.mNotificationManager = notificationManagerService;
        this.uid = i;
        this.pid = i2;
        this.pkg = str;
        this.isSystemToast = z;
        this.token = iBinder;
        this.windowToken = binder;
        this.displayId = i4;
        this.mDuration = i3;
    }

    public abstract void hide();

    public abstract boolean isAppRendered();

    public abstract boolean show();
}
