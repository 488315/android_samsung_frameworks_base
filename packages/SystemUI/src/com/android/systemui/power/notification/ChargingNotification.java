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
import com.android.systemui.Dependency;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.power.utils.BatteryProtectionUtils;
import com.android.systemui.power.utils.DateTimeUtils;
import com.android.systemui.power.utils.PowerUtils;
import com.android.systemui.power.utils.SettingsUtils;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SettingsHelper;

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
                        SharedPreferences.Editor editorEdit = sharedPreferences3.edit();
                        editorEdit.putBoolean("key_first_charge", false);
                        editorEdit.commit();
                    }
                    SharedPreferences sharedPreferences4 = this.mSharedPref;
                    if (sharedPreferences4 != null) {
                        SharedPreferences.Editor editorEdit2 = sharedPreferences4.edit();
                        editorEdit2.putBoolean("key_first_charge_content_added", false);
                        editorEdit2.commit();
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
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantMinimized()) {
            if (this.mChargingType == 12) {
                title = this.mContext.getString(R.string.charging_notice_after_charging);
            }
            long j = this.mChargingTime;
            if (j > 0) {
                title = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(title, " ", DateTimeUtils.getFormattedTime(this.mContext, j));
            }
        }
        return getCommonBuilder(str, title, getContentText()).setSmallIcon(R.drawable.ic_charging_noti).setGroup("CHARGING").setOnlyAlertOnce(true).setDeleteIntent(PowerUtils.pendingBroadcast(this.mContext, "com.samsung.android.systemui.action.DELETED_CHARGING_NOTI")).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        String string;
        int i = this.mChargingType;
        String strM = i == 8 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_slow_charging_text), "\n\n") : i == 9 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.battery_not_fully_connected_charging_popup_text_connection), "\n\n") : "";
        long j = this.mChargingTime;
        if (j > 0) {
            String formattedTime = DateTimeUtils.getFormattedTime(this.mContext, j);
            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM.toString(), " ", formattedTime);
        } else {
            StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
            sbM2.append(this.mContext.getString(R.string.used_percentage, String.format("%d", Integer.valueOf(this.mBatteryLevel))));
            string = sbM2.toString();
        }
        if (BatteryProtectionUtils.isMaximumProtectionEnabled(this.mContext)) {
            if (!PowerUiRune.BATTERY_PROTECTION) {
                string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.protect_battery_notification_text, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n"));
            } else if (BatteryProtectionUtils.getProtectBatteryValue(this.mContext) == 1) {
                StringBuilder sbM3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n");
                Context context = this.mContext;
                sbM3.append(context.getString(R.string.maximum_protection_notification_text, Integer.valueOf(SettingsUtils.globalGetInt(context, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE))));
                string = sbM3.toString();
            } else {
                StringBuilder sbM4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n");
                sbM4.append(this.mContext.getString(R.string.maximum_protection_notification_text, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)));
                string = sbM4.toString();
            }
        } else if (PowerUiRune.BATTERY_PROTECTION) {
            SharedPreferences sharedPreferences = this.mSharedPref;
            if (sharedPreferences != null ? sharedPreferences.getBoolean("key_first_charge", true) : false) {
                if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, 0) != 0) {
                    StringBuilder sbM5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n");
                    sbM5.append(this.mContext.getString(R.string.recommend_battery_protection_first_charge, Integer.valueOf(Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE)));
                    string = sbM5.toString();
                    SharedPreferences sharedPreferences2 = this.mSharedPref;
                    if (sharedPreferences2 != null) {
                        SharedPreferences.Editor editorEdit = sharedPreferences2.edit();
                        editorEdit.putBoolean("key_first_charge_content_added", true);
                        editorEdit.commit();
                    }
                }
            }
        }
        int i2 = this.mChargingType;
        if (i2 == 6 || i2 == 7 || i2 == 10) {
            return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.wireless_charging_use_more_energy, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n"));
        }
        return string;
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0036  */
    @Override // com.android.systemui.power.notification.PowerUiNotification
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showNotification() {
        int i;
        Notification.Builder builder = getBuilder();
        if (this.mChargingTime > 0 || (i = this.mChargingType) == 8 || i == 9 || i == 6 || i == 7 || i == 10) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(getTitle());
            bigTextStyle.bigText(getContentText());
            builder.setStyle(bigTextStyle);
        } else {
            SharedPreferences sharedPreferences = this.mSharedPref;
            if ((sharedPreferences != null ? sharedPreferences.getBoolean("key_first_charge_content_added", false) : false) || BatteryProtectionUtils.isMaximumProtectionEnabled(this.mContext)) {
            }
        }
        this.mNotificationManager.notifyAsUser("charging_state", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
