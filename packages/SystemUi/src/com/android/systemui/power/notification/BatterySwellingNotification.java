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
public final class BatterySwellingNotification extends PowerUiNotification {
    public BatterySwellingNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("battery_swelling", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Context context = this.mContext;
        Notification.Builder category = getCommonBuilder(context.getString(R.string.battery_swelling_mode_noti_title), DeviceType.isTablet() ? context.getString(R.string.battery_swelling_mode_low_temp_noti_text_tablet) : context.getString(R.string.battery_swelling_mode_low_temp_noti_text_phone), "CHR").setSmallIcon(R.drawable.stat_notify_afc).setOnlyAlertOnce(true).setOngoing(true).setPriority(-2).setCategory("sys");
        SystemUIApplication.overrideNotificationAppName(context, category, false);
        return category;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle(getBuilder());
        boolean isTablet = DeviceType.isTablet();
        Context context = this.mContext;
        this.mNotificationManager.notifyAsUser("battery_swelling", R.id.notification_power, bigTextStyle.bigText(isTablet ? context.getString(R.string.battery_swelling_mode_low_temp_noti_text_tablet) : context.getString(R.string.battery_swelling_mode_low_temp_noti_text_phone)).build(), UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
