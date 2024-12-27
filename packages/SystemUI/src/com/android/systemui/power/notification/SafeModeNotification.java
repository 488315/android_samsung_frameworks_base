package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Notification.Builder category = getCommonBuilder(NotificationChannels.ALERTS, this.mContext.getString(R.string.safe_mode_noti_title), this.mContext.getString(R.string.safe_mode_noti_body)).setSmallIcon(R.drawable.stat_notify_safe_mode).setOnlyAlertOnce(true).setOngoing(true).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "com.samsung.systemui.power.action.ACTION_BATTERY_SAFE_MODE")).setPriority(0).setCategory("sys");
        SystemUIApplication.overrideNotificationAppName(this.mContext, category, false);
        return category;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification build = getBuilder().build();
        RemoteViews remoteViews = build.headsUpContentView;
        if (remoteViews != null) {
            remoteViews.setViewVisibility(android.R.id.splashscreen_branding_view, 8);
        }
        this.mNotificationManager.notifyAsUser("safe_mode", R.id.notification_power, build, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
