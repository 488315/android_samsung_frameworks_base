package com.android.systemui.power.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PowerUiNotification {
    public final Context mContext;
    public NotificationManager mNotificationManager;

    public PowerUiNotification(Context context) {
        this.mContext = context;
    }

    public abstract void dismissNotification();

    public abstract Notification.Builder getBuilder();

    public final Notification.Builder getCommonBuilder(CharSequence charSequence, CharSequence charSequence2, String str) {
        return new Notification.Builder(this.mContext, str).setShowWhen(false).setContentTitle(charSequence).setContentText(charSequence2).setVisibility(1);
    }

    public abstract void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot);

    public abstract void showNotification();
}
