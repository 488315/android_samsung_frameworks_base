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
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        String string = this.mContext.getString(R.string.battery_low_sec_title, Integer.valueOf(this.mBatteryLevel));
        int i = this.mCurrentBatteryMode;
        boolean z = this.mIsPowerSavingSupported;
        String string2 = (i == 0 && z) ? DeviceType.isTablet() ? this.mContext.getString(R.string.battery_low_sec_body_tablet) : this.mContext.getString(R.string.battery_low_sec_body_phone) : DeviceType.isTablet() ? this.mContext.getString(R.string.battery_low_sec_body_tablet_psm) : this.mContext.getString(R.string.battery_low_sec_body_phone_psm);
        Notification.Builder style = getCommonBuilder(NotificationChannels.SEC_LOW_BATTERY, string, string2).setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.dismissedWarning")).setStyle(new Notification.BigTextStyle().bigText(string2));
        if (this.mOpenBatterySettings.resolveActivity(this.mContext.getPackageManager()) != null && this.mCurrentBatteryMode == 0 && z) {
            style.setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.powerMode"));
        }
        if (PowerUiRune.CHN_SMART_MANAGER) {
            SystemUIApplication.overrideNotificationAppName(this.mContext, style, false);
            style.setSmallIcon(R.drawable.ic_power_low);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("android.substName", this.mContext.getString(R.string.battery_low_sec_app_name));
            style.addExtras(bundle);
            style.setSmallIcon(R.drawable.stat_notify_battery_caution);
        }
        return style;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mBatteryLevel = secBatterySnapshot.batteryLevel;
        this.mCurrentBatteryMode = secBatterySnapshot.currentBatteryMode;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        this.mNotificationManager.notifyAsUser("low_battery", 3, getBuilder().build(), UserHandle.ALL);
    }
}
