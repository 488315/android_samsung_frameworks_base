package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SafeModeNotification extends PowerUiNotification {
    public SafeModeNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("safe_mode", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Context context = this.mContext;
        Notification.Builder category = getCommonBuilder(context.getString(R.string.safe_mode_noti_title), context.getString(R.string.safe_mode_noti_body), "ALR").setSmallIcon(R.drawable.stat_notify_safe_mode).setOnlyAlertOnce(true).setOngoing(true).setContentIntent(PowerUtils.pendingBroadcast(context, "com.samsung.systemui.power.action.ACTION_BATTERY_SAFE_MODE")).setPriority(0).setCategory("sys");
        SystemUIApplication.overrideNotificationAppName(context, category, false);
        return category;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification build = getBuilder().build();
        RemoteViews remoteViews = build.headsUpContentView;
        if (remoteViews != null) {
            remoteViews.setViewVisibility(android.R.id.snooze_button, 8);
        }
        this.mNotificationManager.notifyAsUser("safe_mode", R.id.notification_power, build, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
