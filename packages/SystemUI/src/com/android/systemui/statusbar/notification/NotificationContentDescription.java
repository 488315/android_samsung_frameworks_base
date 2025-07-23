package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotificationContentDescription {
    public static final CharSequence contentDescForNotification(Context context, Notification notification2) {
        String loadHeaderAppName = notification2.loadHeaderAppName(context);
        if (loadHeaderAppName == null) {
            loadHeaderAppName = "";
        }
        return context.getString(R.string.accessibility_desc_notification_icon, loadHeaderAppName, "");
    }
}
