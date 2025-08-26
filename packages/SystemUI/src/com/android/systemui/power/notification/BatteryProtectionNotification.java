package com.android.systemui.power.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.power.constants.PowerUiConstants;
import com.android.systemui.power.utils.BatteryProtectionUtils;
import com.android.systemui.power.utils.SettingsUtils;
import com.android.systemui.util.NotificationChannels;

/* loaded from: classes2.dex */
public class BatteryProtectionNotification extends PowerUiNotification {
    public int mBatteryProtectionValue;
    public int mMaximumThresholdValue;
    public int mRechargeLevel;

    public BatteryProtectionNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("tag_battery_protection", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R.string.charging_notice_app_name));
        String str = NotificationChannels.CHARGING;
        int i = this.mBatteryProtectionValue;
        return getCommonBuilder(str, (i == 1 || i == 2) ? this.mContext.getString(R.string.maximum_protection_noti_title) : this.mContext.getString(R.string.battery_protection_noti_title), getContentText()).setSmallIcon(R.drawable.stat_notify_battery_protection).setGroup("CHARGING").setOnlyAlertOnce(true).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("com.samsung.android.sm.ACTION_BATTERY_PROTECTION").setPackage(PowerUiConstants.DC_PACKAGE_NAME).setFlags(268435456), 335544320, null, UserHandle.CURRENT)).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        int i = this.mBatteryProtectionValue;
        return i != 1 ? i != 2 ? this.mContext.getString(R.string.battery_protection_not_charging_noti_content, Integer.valueOf(this.mRechargeLevel)) : this.mContext.getString(R.string.maximum_protection_noti_content, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)) : this.mContext.getString(R.string.maximum_protection_noti_content, Integer.valueOf(this.mMaximumThresholdValue));
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        Context context = this.mContext;
        BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
        this.mRechargeLevel = SettingsUtils.globalGetInt(context, "battery_protection_recharge_level", 95);
        this.mBatteryProtectionValue = BatteryProtectionUtils.getProtectBatteryValue(this.mContext);
        this.mMaximumThresholdValue = SettingsUtils.globalGetInt(this.mContext, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification.Builder builder = getBuilder();
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        int i = this.mBatteryProtectionValue;
        bigTextStyle.setBigContentTitle((i == 1 || i == 2) ? this.mContext.getString(R.string.maximum_protection_noti_title) : this.mContext.getString(R.string.battery_protection_noti_title));
        bigTextStyle.bigText(getContentText());
        builder.setStyle(bigTextStyle);
        this.mNotificationManager.notifyAsUser("tag_battery_protection", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
