package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Slog;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.power.utils.BatteryProtectionUtils;
import com.android.systemui.power.utils.DateTimeUtils;
import com.android.systemui.power.utils.PowerUtils;
import com.android.systemui.power.utils.SettingsUtils;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ChargingNotification extends PowerUiNotification {
    public int mBatteryLevel;
    public long mChargingTime;
    public int mChargingType;
    public SharedPreferences mSharedPref;

    public ChargingNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("charging_state", R.id.notification_power, UserHandle.ALL);
        if (PowerUiRune.BATTERY_PROTECTION) {
            SharedPreferences sharedPreferences = this.mSharedPref;
            if (sharedPreferences != null ? sharedPreferences.getBoolean("key_first_charge", true) : false) {
                SharedPreferences sharedPreferences2 = this.mSharedPref;
                if (sharedPreferences2 != null ? sharedPreferences2.getBoolean("key_first_charge_content_added", false) : false) {
                    Slog.d("PowerUi.ChargingNotification", "First charge has been completed.");
                    SharedPreferences sharedPreferences3 = this.mSharedPref;
                    if (sharedPreferences3 != null) {
                        SharedPreferences.Editor edit = sharedPreferences3.edit();
                        edit.putBoolean("key_first_charge", false);
                        edit.commit();
                    }
                    SharedPreferences sharedPreferences4 = this.mSharedPref;
                    if (sharedPreferences4 != null) {
                        SharedPreferences.Editor edit2 = sharedPreferences4.edit();
                        edit2.putBoolean("key_first_charge_content_added", false);
                        edit2.commit();
                    }
                }
            }
        }
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
            title = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(title, " ", DateTimeUtils.getFormattedTime(this.mContext, j));
        }
        return getCommonBuilder(str, title, getContentText()).setSmallIcon(R.drawable.ic_charging_noti).setGroup("CHARGING").setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(this.mContext, "com.samsung.android.systemui.action.DELETED_CHARGING_NOTI")).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        String sb;
        int i = this.mChargingType;
        String m = i == 8 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_slow_charging_text), "\n\n") : i == 9 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_not_fully_connected_charging_popup_text_connection), "\n\n") : "";
        long j = this.mChargingTime;
        if (j > 0) {
            String formattedTime = DateTimeUtils.getFormattedTime(this.mContext, j);
            StringBuilder m2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
            m2.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m2.toString(), " ", formattedTime);
        } else {
            StringBuilder m3 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
            m3.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            sb = m3.toString();
        }
        if (BatteryProtectionUtils.isMaximumProtectionEnabled(this.mContext)) {
            if (!PowerUiRune.BATTERY_PROTECTION) {
                sb = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.protect_battery_notification_text, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n"));
            } else if (BatteryProtectionUtils.getProtectBatteryValue(this.mContext) == 1) {
                StringBuilder m4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n");
                Context context = this.mContext;
                m4.append(context.getString(R.string.maximum_protection_notification_text, Integer.valueOf(SettingsUtils.globalGetInt(context, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE))));
                sb = m4.toString();
            } else {
                StringBuilder m5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n");
                m5.append(this.mContext.getString(R.string.maximum_protection_notification_text, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)));
                sb = m5.toString();
            }
        } else if (PowerUiRune.BATTERY_PROTECTION) {
            SharedPreferences sharedPreferences = this.mSharedPref;
            if (sharedPreferences != null ? sharedPreferences.getBoolean("key_first_charge", true) : false) {
                if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, 0) != 0) {
                    StringBuilder m6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, "\n");
                    m6.append(this.mContext.getString(R.string.recommend_battery_protection_first_charge, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)));
                    sb = m6.toString();
                    SharedPreferences sharedPreferences2 = this.mSharedPref;
                    if (sharedPreferences2 != null) {
                        SharedPreferences.Editor edit = sharedPreferences2.edit();
                        edit.putBoolean("key_first_charge_content_added", true);
                        edit.commit();
                    }
                }
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
        this.mSharedPref = this.mContext.getSharedPreferences("charging_shared_pref", 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        if (com.android.systemui.power.utils.BatteryProtectionUtils.isMaximumProtectionEnabled(r5.mContext) == false) goto L22;
     */
    @Override // com.android.systemui.power.notification.PowerUiNotification
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showNotification() {
        /*
            r5 = this;
            android.app.Notification$Builder r0 = r5.getBuilder()
            long r1 = r5.mChargingTime
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L36
            int r1 = r5.mChargingType
            r2 = 8
            if (r1 == r2) goto L36
            r2 = 9
            if (r1 == r2) goto L36
            r2 = 6
            if (r1 == r2) goto L36
            r2 = 7
            if (r1 == r2) goto L36
            r2 = 10
            if (r1 != r2) goto L21
            goto L36
        L21:
            android.content.SharedPreferences r1 = r5.mSharedPref
            r2 = 0
            if (r1 == 0) goto L2c
            java.lang.String r3 = "key_first_charge_content_added"
            boolean r2 = r1.getBoolean(r3, r2)
        L2c:
            if (r2 != 0) goto L36
            android.content.Context r1 = r5.mContext
            boolean r1 = com.android.systemui.power.utils.BatteryProtectionUtils.isMaximumProtectionEnabled(r1)
            if (r1 == 0) goto L4c
        L36:
            android.app.Notification$BigTextStyle r1 = new android.app.Notification$BigTextStyle
            r1.<init>()
            java.lang.String r2 = r5.getTitle()
            r1.setBigContentTitle(r2)
            java.lang.String r2 = r5.getContentText()
            r1.bigText(r2)
            r0.setStyle(r1)
        L4c:
            android.app.Notification r0 = r0.build()
            android.app.NotificationManager r5 = r5.mNotificationManager
            android.os.UserHandle r1 = android.os.UserHandle.ALL
            java.lang.String r2 = "charging_state"
            r3 = 2131364000(0x7f0a08a0, float:1.8347825E38)
            r5.notifyAsUser(r2, r3, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.notification.ChargingNotification.showNotification():void");
    }
}
