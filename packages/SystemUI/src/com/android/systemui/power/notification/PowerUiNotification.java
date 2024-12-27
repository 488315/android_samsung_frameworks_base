package com.android.systemui.power.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.power.SecBatterySnapshot;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PowerUiNotification {
    public final Context mContext;
    public NotificationManager mNotificationManager;

    public PowerUiNotification(Context context) {
        this.mContext = context;
    }

    public abstract void dismissNotification();

    public abstract Notification.Builder getBuilder();

    public final Notification.Builder getCommonBuilder(String str, CharSequence charSequence, CharSequence charSequence2) {
        return new Notification.Builder(this.mContext, str).setShowWhen(false).setContentTitle(charSequence).setContentText(charSequence2).setVisibility(1);
    }

    public abstract void setInformation(SecBatterySnapshot secBatterySnapshot);

    public abstract void showNotification();
}
