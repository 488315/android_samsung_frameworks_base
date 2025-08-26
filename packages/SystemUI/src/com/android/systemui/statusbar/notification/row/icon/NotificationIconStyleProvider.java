package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import java.util.Collection;

/* loaded from: classes3.dex */
public interface NotificationIconStyleProvider {
    void purgeCache(Collection collection);

    boolean shouldShowAppIcon(Context context, StatusBarNotification statusBarNotification);

    boolean shouldShowWorkProfileBadge(Context context, StatusBarNotification statusBarNotification);
}
