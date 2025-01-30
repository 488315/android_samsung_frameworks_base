package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Context context = this.mContext;
        String string = context.getString(R.string.battery_health_interruption_title);
        int i = this.mBatteryHealth;
        String string2 = i == 3 ? DeviceType.isTablet() ? context.getString(R.string.battery_health_interruption_by_too_high_temperature_text_tablet) : context.getString(R.string.battery_health_interruption_by_too_high_temperature_text_phone) : i == 7 ? DeviceType.isTablet() ? context.getString(R.string.battery_health_interruption_by_too_low_temperature_text_tablet) : context.getString(R.string.battery_health_interruption_by_too_low_temperature_text_phone) : null;
        Notification.Builder style = getCommonBuilder(string, string2, "CHR").setSmallIcon(R.drawable.ic_device_thermostat_24).setOngoing(true).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2));
        SystemUIApplication.overrideNotificationAppName(context, style, false);
        return style;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mBatteryHealth = secBatteryStatsSnapshot.batteryHealth;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        this.mNotificationManager.notifyAsUser("health_interruption", R.id.notification_power, getBuilder().build(), UserHandle.ALL);
    }
}
