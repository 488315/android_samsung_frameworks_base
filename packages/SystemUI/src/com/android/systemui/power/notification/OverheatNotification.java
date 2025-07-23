package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.power.utils.PowerUtils;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class OverheatNotification extends PowerUiNotification {
    public OverheatNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("over_heat", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW;
        Notification.Builder category = getCommonBuilder(NotificationChannels.ALERTS, z ? this.mContext.getString(R.string.cooling_title_vzw) : this.mContext.getString(R.string.cooling_title), z ? this.mContext.getString(R.string.cooling_noti_text_vzw) : this.mContext.getString(R.string.cooling_noti_text)).setSmallIcon(R.drawable.stat_notify_battery_cooling_down).setOnlyAlertOnce(true).setOngoing(true).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "com.samsung.systemui.power.action.ACTION_BATTERY_OVER_HEAT")).setPriority(0).setCategory("sys");
        SystemUIApplication.overrideNotificationAppName(this.mContext, category, false);
        return category;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification build = getBuilder().build();
        RemoteViews remoteViews = build.headsUpContentView;
        if (remoteViews != null) {
            remoteViews.setViewVisibility(android.R.id.tag_top_animator, 8);
        }
        this.mNotificationManager.notifyAsUser("over_heat", R.id.notification_power, build, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
