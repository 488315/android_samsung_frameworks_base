package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import java.util.Collection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NoOpIconStyleProvider implements NotificationIconStyleProvider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final void purgeCache(Collection collection) {
        Log.wtf("NoOpIconStyleProvider", "NoOpIconStyleProvider should not be used anywhere.");
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final boolean shouldShowAppIcon(Context context, StatusBarNotification statusBarNotification) {
        Log.wtf("NoOpIconStyleProvider", "NoOpIconStyleProvider should not be used anywhere.");
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final boolean shouldShowWorkProfileBadge(Context context, StatusBarNotification statusBarNotification) {
        Log.wtf("NoOpIconStyleProvider", "NoOpIconStyleProvider should not be used anywhere.");
        return false;
    }
}
