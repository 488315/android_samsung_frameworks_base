package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LowBatteryNotification extends PowerUiNotification {
    public int mBatteryLevel;
    public int mCurrentBatteryMode;
    public final boolean mIsPowerSavingSupported;
    public final Intent mOpenBatterySettings;

    public LowBatteryNotification(Context context) {
        super(context);
        this.mIsPowerSavingSupported = !"U06".equals(SystemProperties.get("ro.csc.sales_code"));
        this.mOpenBatterySettings = new Intent("android.intent.action.POWER_USAGE_SUMMARY").setFlags(478150656);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("low_battery", 3, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Object[] objArr = {Integer.valueOf(this.mBatteryLevel)};
        Context context = this.mContext;
        String string = context.getString(R.string.battery_low_sec_title, objArr);
        int i = this.mCurrentBatteryMode;
        boolean z = this.mIsPowerSavingSupported;
        String string2 = (i == 0 && z) ? DeviceType.isTablet() ? context.getString(R.string.battery_low_sec_body_tablet) : context.getString(R.string.battery_low_sec_body_phone) : DeviceType.isTablet() ? context.getString(R.string.battery_low_sec_body_tablet_psm) : context.getString(R.string.battery_low_sec_body_phone_psm);
        Notification.Builder style = getCommonBuilder(string, string2, "LOWBAT").setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(context, "PNW.dismissedWarning")).setStyle(new Notification.BigTextStyle().bigText(string2));
        if ((this.mOpenBatterySettings.resolveActivity(context.getPackageManager()) != null) && this.mCurrentBatteryMode == 0 && z) {
            style.setContentIntent(PowerUtils.pendingBroadcast(context, "PNW.powerMode"));
        }
        if (PowerUiRune.CHN_SMART_MANAGER) {
            SystemUIApplication.overrideNotificationAppName(context, style, false);
            style.setSmallIcon(R.drawable.ic_power_low);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("android.substName", context.getString(R.string.battery_low_sec_app_name));
            style.addExtras(bundle);
            style.setSmallIcon(R.drawable.stat_notify_battery_caution);
        }
        return style;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mBatteryLevel = secBatteryStatsSnapshot.batteryLevel;
        this.mCurrentBatteryMode = secBatteryStatsSnapshot.currentBatteryMode;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        this.mNotificationManager.notifyAsUser("low_battery", 3, getBuilder().build(), UserHandle.ALL);
    }
}
