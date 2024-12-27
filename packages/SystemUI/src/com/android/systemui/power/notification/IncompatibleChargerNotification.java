package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IncompatibleChargerNotification extends PowerUiNotification {
    public IncompatibleChargerNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("incompatible_charger_state", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Notification.Builder category = getCommonBuilder(NotificationChannels.ALERTS, this.mContext.getString(R.string.incompatible_charger_title), this.mContext.getString(R.string.incompatible_charger_notification_text)).setSmallIcon(R.drawable.tw_stat_sys_battery_incompatible_vzw).setOnlyAlertOnce(true).setOngoing(true).setPriority(-2).setCategory("sys");
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
        this.mNotificationManager.notifyAsUser("incompatible_charger_state", R.id.notification_power, build, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
