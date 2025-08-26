package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public abstract class NotificationContentDescription {
    public static final CharSequence contentDescForNotification(Context context, Notification notification2) {
        String strLoadHeaderAppName = notification2.loadHeaderAppName(context);
        if (strLoadHeaderAppName == null) {
            strLoadHeaderAppName = "";
        }
        return context.getString(R.string.accessibility_desc_notification_icon, strLoadHeaderAppName, "");
    }
}
