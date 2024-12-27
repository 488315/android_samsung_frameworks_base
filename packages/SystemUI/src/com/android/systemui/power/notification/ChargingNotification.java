package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.BatteryProtectionUtil;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.NotificationChannels;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        bundle.putString("android.substName", this.mContext.getString(R.string.charging_notice_app_name));
        String str = NotificationChannels.CHARGING;
        String title = getTitle();
        if (this.mChargingType == 12) {
            title = this.mContext.getString(R.string.charging_notice_after_charging);
        }
        long j = this.mChargingTime;
        if (j > 0) {
            title = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(title, " ", PowerUtils.getFormattedTime(this.mContext, j));
        }
        return getCommonBuilder(str, title, getContentText()).setSmallIcon(R.drawable.stat_notify_afc).setGroup("CHARGING").setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(this.mContext, "com.samsung.android.systemui.action.DELETED_CHARGING_NOTI")).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        String sb;
        int i = this.mChargingType;
        String m = i == 8 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_slow_charging_text), "\n\n") : i == 9 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_not_fully_connected_charging_popup_text_connection), "\n\n") : "";
        long j = this.mChargingTime;
        if (j > 0) {
            String formattedTime = PowerUtils.getFormattedTime(this.mContext, j);
            StringBuilder m2 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(m);
            m2.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m2.toString(), " ", formattedTime);
        } else {
            StringBuilder m3 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(m);
            m3.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = m3.toString();
        }
        if (BatteryProtectionUtil.isMaximumProtectionEnabled(this.mContext)) {
            if (!PowerUiRune.BATTERY_PROTECTION) {
                sb = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.protect_battery_notification_text, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n"));
            } else if (BatteryProtectionUtil.getProtectBatteryValue(this.mContext) == 1) {
                StringBuilder m4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n");
                Context context = this.mContext;
                m4.append(context.getString(R.string.maximum_protection_notification_text, Integer.valueOf(Settings.Global.getInt(context.getContentResolver(), "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE))));
                sb = m4.toString();
            } else {
                StringBuilder m5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n");
                m5.append(this.mContext.getString(R.string.maximum_protection_notification_text, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)));
                sb = m5.toString();
            }
        }
        int i2 = this.mChargingType;
        if (i2 == 6 || i2 == 7 || i2 == 10) {
            return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.wireless_charging_use_more_energy, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n"));
        }
        return sb;
    }

    public final String getTitle() {
        int i = this.mChargingType;
        switch (i) {
            case 2:
            case 3:
            case 4:
                Context context = this.mContext;
                return i == 3 ? context.getString(R.string.charging_notice_super_fast_charging_started) : i == 4 ? context.getString(R.string.charging_notice_super_fast_charging_20_started) : PowerUiRune.SPECIFIC_POWER_REQUEST_BY_CHN ? context.getString(R.string.charging_notice_advanced_charging_started_chn) : context.getString(R.string.charging_notice_fast_charging_started);
            case 5:
            default:
                return this.mContext.getString(R.string.charging_notice_charging_started);
            case 6:
                return this.mContext.getString(R.string.charging_notice_wireless_charging_started);
            case 7:
                return PowerUiRune.SPECIFIC_POWER_REQUEST_BY_CHN ? this.mContext.getString(R.string.charging_notice_advanced_wireless_charging_started_chn) : this.mContext.getString(R.string.charging_notice_fast_wireless_charging_started);
            case 8:
                return this.mContext.getString(R.string.battery_slow_charging_title);
            case 9:
                return this.mContext.getString(R.string.battery_not_fully_connected_charging_title);
            case 10:
                return this.mContext.getString(R.string.charging_notice_after_wireless_charging);
            case 11:
                return this.mContext.getString(R.string.charging_notice_after_charging);
            case 12:
                return this.mContext.getString(R.string.after_opt_charging_noti_title);
        }
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mChargingTime = secBatterySnapshot.chargingTime;
        this.mChargingType = secBatterySnapshot.chargingType;
        this.mBatteryLevel = secBatterySnapshot.batteryLevel;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        int i;
        Notification.Builder builder = getBuilder();
        if (this.mChargingTime > 0 || (i = this.mChargingType) == 8 || i == 9 || BatteryProtectionUtil.isMaximumProtectionEnabled(this.mContext)) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(getTitle());
            bigTextStyle.bigText(getContentText());
            builder.setStyle(bigTextStyle);
        }
        this.mNotificationManager.notifyAsUser("charging_state", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
