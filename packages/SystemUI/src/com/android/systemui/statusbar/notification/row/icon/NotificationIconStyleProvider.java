package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import java.util.Collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationIconStyleProvider {
    void purgeCache(Collection collection);

    boolean shouldShowAppIcon(Context context, StatusBarNotification statusBarNotification);

    boolean shouldShowWorkProfileBadge(Context context, StatusBarNotification statusBarNotification);
}
