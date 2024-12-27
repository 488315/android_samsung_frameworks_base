package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.NotificationChannels;

public final class BatterySwellingNotification extends PowerUiNotification {
    public int mBatteryLevel;

    public BatterySwellingNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("battery_swelling", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R.string.charging_notice_app_name));
        return getCommonBuilder(NotificationChannels.CHARGING, this.mContext.getString(R.string.battery_swelling_mode_noti_title), getContentText()).setSmallIcon(R.drawable.stat_notify_afc).setGroup("CHARGING").setOnlyAlertOnce(true).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        String string = this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel)));
        if (DeviceType.isTablet()) {
            return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.battery_swelling_mode_low_temp_noti_text_tablet, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n"));
        }
        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.battery_swelling_mode_low_temp_noti_text_phone, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n"));
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mBatteryLevel = secBatterySnapshot.batteryLevel;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        this.mNotificationManager.notifyAsUser("battery_swelling", R.id.notification_power, new Notification.BigTextStyle(getBuilder()).bigText(getContentText()).build(), UserHandle.ALL);
    }
}
