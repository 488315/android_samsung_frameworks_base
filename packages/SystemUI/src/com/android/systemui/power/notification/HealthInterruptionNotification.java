package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HealthInterruptionNotification extends PowerUiNotification {
    public int mBatteryHealth;

    public HealthInterruptionNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("health_interruption", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        String string = this.mContext.getString(R.string.battery_health_interruption_title);
        int i = this.mBatteryHealth;
        String string2 = i == 3 ? DeviceType.isTablet() ? this.mContext.getString(R.string.battery_health_interruption_by_too_high_temperature_text_tablet) : this.mContext.getString(R.string.battery_health_interruption_by_too_high_temperature_text_phone) : i == 7 ? DeviceType.isTablet() ? this.mContext.getString(R.string.battery_health_interruption_by_too_low_temperature_text_tablet) : this.mContext.getString(R.string.battery_health_interruption_by_too_low_temperature_text_phone) : null;
        Notification.Builder style = getCommonBuilder(NotificationChannels.CHARGING, string, string2).setSmallIcon(R.drawable.ic_device_thermostat_24).setOngoing(true).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2));
        SystemUIApplication.overrideNotificationAppName(this.mContext, style, false);
        return style;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mBatteryHealth = secBatterySnapshot.batteryHealth;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        this.mNotificationManager.notifyAsUser("health_interruption", R.id.notification_power, getBuilder().build(), UserHandle.ALL);
    }
}
