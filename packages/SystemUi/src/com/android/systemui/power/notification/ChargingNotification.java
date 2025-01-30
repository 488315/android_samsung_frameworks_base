package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChargingNotification extends PowerUiNotification {
    public int mBatteryLevel;
    public long mChargingTime;
    public int mChargingType;

    public ChargingNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("charging_state", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Bundle bundle = new Bundle();
        Context context = this.mContext;
        bundle.putString("android.substName", context.getString(R.string.charging_notice_app_name));
        String title = getTitle();
        if (this.mChargingType == 12) {
            title = context.getString(R.string.charging_notice_after_charging);
        }
        long j = this.mChargingTime;
        if (j > 0) {
            title = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(title, " ", PowerUtils.getFormattedTime(context, j));
        }
        return getCommonBuilder(title, getContentText(), "CHR").setSmallIcon(R.drawable.stat_notify_afc).setGroup("CHARGING").setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(context, "com.samsung.android.systemui.action.DELETED_CHARGING_NOTI")).setContentIntent(PowerUtils.pendingBroadcast(context, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        String sb;
        int i = this.mChargingType;
        Context context = this.mContext;
        String m14m = i == 8 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(context.getString(R.string.battery_slow_charging_text), "\n\n") : i == 9 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(context.getString(R.string.battery_not_fully_connected_charging_popup_text_connection), "\n\n") : "";
        long j = this.mChargingTime;
        if (j > 0) {
            String formattedTime = PowerUtils.getFormattedTime(context, j);
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m14m);
            m18m.append(context.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(m18m.toString(), " ", formattedTime);
        } else {
            StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m14m);
            m18m2.append(context.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = m18m2.toString();
        }
        return PowerUtils.isMaximumProtectionEnabled(context) ? PowerUiRune.BATTERY_PROTECTION ? KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.maximum_protection_notification_text, AbstractC0000x2c234b15.m2m(sb, "\n")) : KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.protect_battery_notification_text, AbstractC0000x2c234b15.m2m(sb, "\n")) : sb;
    }

    public final String getTitle() {
        int i = this.mChargingType;
        Context context = this.mContext;
        switch (i) {
            case 2:
            case 3:
            case 4:
                return i == 3 ? context.getString(R.string.charging_notice_super_fast_charging_started) : i == 4 ? context.getString(R.string.charging_notice_super_fast_charging_20_started) : PowerUiRune.SPECIFIC_POWER_REQUEST_BY_CHN ? context.getString(R.string.charging_notice_advanced_charging_started_chn) : context.getString(R.string.charging_notice_fast_charging_started);
            case 5:
            default:
                return context.getString(R.string.charging_notice_charging_started);
            case 6:
                return context.getString(R.string.charging_notice_wireless_charging_started);
            case 7:
                return PowerUiRune.SPECIFIC_POWER_REQUEST_BY_CHN ? context.getString(R.string.charging_notice_advanced_wireless_charging_started_chn) : context.getString(R.string.charging_notice_fast_wireless_charging_started);
            case 8:
                return context.getString(R.string.battery_slow_charging_title);
            case 9:
                return context.getString(R.string.battery_not_fully_connected_charging_title);
            case 10:
                return context.getString(R.string.charging_notice_after_wireless_charging);
            case 11:
                return context.getString(R.string.charging_notice_after_charging);
            case 12:
                return context.getString(R.string.after_opt_charging_noti_title);
        }
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mChargingTime = secBatteryStatsSnapshot.chargingTime;
        this.mChargingType = secBatteryStatsSnapshot.chargingType;
        this.mBatteryLevel = secBatteryStatsSnapshot.batteryLevel;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        int i;
        Notification.Builder builder = getBuilder();
        if (this.mChargingTime > 0 || (i = this.mChargingType) == 8 || i == 9 || PowerUtils.isMaximumProtectionEnabled(this.mContext)) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(getTitle());
            bigTextStyle.bigText(getContentText());
            builder.setStyle(bigTextStyle);
        }
        this.mNotificationManager.notifyAsUser("charging_state", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
