package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.app.NotificationManager;
import android.net.Uri;
import com.android.systemui.plugins.subscreen.SubRoom;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationManagerWrapper {
    private static final NotificationManagerWrapper sInstance = new NotificationManagerWrapper();
    private static final NotificationManager mNotificationManager = (NotificationManager) AppGlobals.getInitialApplication().getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);

    private NotificationManagerWrapper() {
    }

    public static NotificationManagerWrapper getInstance() {
        return sInstance;
    }

    public int getZenMode() {
        return mNotificationManager.getZenMode();
    }

    public void setZenMode(int i, Uri uri, String str) {
        mNotificationManager.setZenMode(i, uri, str);
    }
}
