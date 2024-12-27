package com.android.server.ibs.sleepmode;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.location.LocationManager;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

public final class SleepModePolicyController {
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public LocalTime mEndTime;
    public final SleepModeHandler mHandler;
    public final SleepModeLogger mLogger;
    public boolean mSleepModeEnabled;
    public LocalTime mStartTime;
    public final Object mActionsLock = new Object();
    public final SleepModePolicyController$$ExternalSyntheticLambda0 mTimeoutAlarmListener =
            new AlarmManager
                    .OnAlarmListener() { // from class:
                                         // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda0
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    SleepModePolicyController.this.updateActivated();
                }
            };
    public boolean mInited = false;
    public int mSysState = 0;
    public final ArrayList mEntryArrayList = new ArrayList();
    public final SleepModeReceiver mReceiver = new SleepModeReceiver();
    public final SleepModeReceiver mDeviceStatusReceiver = new SleepModeReceiver();

    public final class ActionEntry {
        public SleepModeCallBack callBack;
        public String tag;

        public final boolean equals(Object obj) {
            if (!(obj instanceof ActionEntry)) {
                return super.equals(obj);
            }
            ActionEntry actionEntry = (ActionEntry) obj;
            return this.tag.equals(actionEntry.tag) && this.callBack == actionEntry.callBack;
        }

        public final int hashCode() {
            String str = this.tag;
            return (str == null ? 0 : str.hashCode()) + 31;
        }
    }

    public final class LocalTime {
        public final int hourOfDay;
        public final int minute;

        public LocalTime(int i, int i2) {
            if (i < 0 || i > 23) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid hourOfDay: "));
            }
            if (i2 < 0 || i2 > 59) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid minute: "));
            }
            this.hourOfDay = i;
            this.minute = i2;
        }

        public static LocalTime valueOf(long j) {
            return new LocalTime((int) (j / 60), (int) (j % 60));
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || LocalTime.class != obj.getClass()) {
                return false;
            }
            LocalTime localTime = (LocalTime) obj;
            return this.hourOfDay == localTime.hourOfDay && this.minute == localTime.minute;
        }

        public final Calendar getDateTimeAfter(Calendar calendar, boolean z) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(1, calendar.get(1));
            if (z) {
                calendar2.set(6, calendar.get(6) + 1);
            } else {
                calendar2.set(6, calendar.get(6));
            }
            calendar2.set(11, this.hourOfDay);
            calendar2.set(12, this.minute);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            if (calendar2.before(calendar)) {
                calendar2.add(5, 1);
            }
            return calendar2;
        }

        public final Calendar getDateTimeBefore(Calendar calendar) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(1, calendar.get(1));
            calendar2.set(6, calendar.get(6));
            calendar2.set(11, this.hourOfDay);
            calendar2.set(12, this.minute);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            if (calendar2.after(calendar)) {
                calendar2.add(5, -1);
            }
            return calendar2;
        }

        public final int hashCode() {
            return ((this.hourOfDay + 31) * 31) + this.minute;
        }

        public final String toString() {
            return String.format(
                    Locale.US,
                    "%02d:%02d",
                    Integer.valueOf(this.hourOfDay),
                    Integer.valueOf(this.minute));
        }
    }

    public final class SleepModeAction {
        public final AnonymousClass1 btCallBack;
        public final AnonymousClass1 cameraFlashNotificationCallBack;
        public final AnonymousClass2 gpsCallBack;
        public final Context mContext;
        public final AnonymousClass1 masterSyncCallBack;
        public final AnonymousClass1 nearbyCallBack;
        public final AnonymousClass1 notificationCallBack;
        public final AnonymousClass1 psmCallBack;
        public final AnonymousClass2 wifiCallBack;

        /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$2] */
        /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$2] */
        /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        /* JADX WARN: Type inference failed for: r2v5, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        /* JADX WARN: Type inference failed for: r2v6, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        /* JADX WARN: Type inference failed for: r2v7, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        /* JADX WARN: Type inference failed for: r2v8, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$SleepModeAction$1] */
        public SleepModeAction(final Context context) {
            this.mContext = context;
            final int i = 0;
            this.psmCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            this.gpsCallBack =
                    new SleepModeCallBack(
                            this) { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.2
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            int i2;
                            switch (i) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "GPS cancelAction");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    try {
                                        i2 =
                                                sleepModeAction
                                                        .mContext
                                                        .getSharedPreferences("sleep_mode_pref", 0)
                                                        .getInt("pref_sleep_mode_location_key", 0);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                        i2 = 0;
                                    }
                                    if (i2 != 0) {
                                        Settings.Secure.putInt(
                                                sleepModeAction.mContext.getContentResolver(),
                                                "location_mode",
                                                i2);
                                        SleepModePolicyController.this.mLogger.add("Enable GPS");
                                    }
                                    SharePrefUtils.putInt(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_location_key",
                                            0);
                                    break;
                                default:
                                    Slog.d("SleepModePolicyController", "Wifi cancelAction");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (SharePrefUtils.getBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_wifi_key")) {
                                        Settings.Global.putInt(
                                                sleepModeAction2.mContext.getContentResolver(),
                                                "wifi_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable Wifi scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_wifi_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            int i2;
                            boolean z;
                            switch (i) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "GPS triggerAction");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    LocationManager locationManager =
                                            (LocationManager)
                                                    sleepModeAction.mContext.getSystemService(
                                                            "location");
                                    if (locationManager != null) {
                                        z = locationManager.isLocationEnabled();
                                        i2 =
                                                Settings.Secure.getInt(
                                                        sleepModeAction.mContext
                                                                .getContentResolver(),
                                                        "location_mode",
                                                        0);
                                    } else {
                                        i2 =
                                                Settings.Secure.getInt(
                                                        sleepModeAction.mContext
                                                                .getContentResolver(),
                                                        "location_mode",
                                                        0);
                                        z = i2 != 0;
                                    }
                                    if (!z) {
                                        SharePrefUtils.putInt(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_location_key",
                                                0);
                                        break;
                                    } else {
                                        Settings.Secure.putInt(
                                                sleepModeAction.mContext.getContentResolver(),
                                                "location_mode",
                                                0);
                                        SharePrefUtils.putInt(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_location_key",
                                                i2);
                                        SleepModePolicyController.this.mLogger.add("Disable GPS");
                                        SleepModePolicyController sleepModePolicyController =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController,
                                                sleepModePolicyController.mSysState | 4);
                                        break;
                                    }
                                default:
                                    Slog.d("SleepModePolicyController", "Wifi triggerAction");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (Settings.Global.getInt(
                                                    sleepModeAction2.mContext.getContentResolver(),
                                                    "wifi_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                sleepModeAction2.mContext.getContentResolver(),
                                                "wifi_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction2.mContext,
                                                "pref_sleep_mode_wifi_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 2);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable Wifi scan");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i2 = 1;
            this.wifiCallBack =
                    new SleepModeCallBack(
                            this) { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.2
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            int i22;
                            switch (i2) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "GPS cancelAction");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    try {
                                        i22 =
                                                sleepModeAction
                                                        .mContext
                                                        .getSharedPreferences("sleep_mode_pref", 0)
                                                        .getInt("pref_sleep_mode_location_key", 0);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                        i22 = 0;
                                    }
                                    if (i22 != 0) {
                                        Settings.Secure.putInt(
                                                sleepModeAction.mContext.getContentResolver(),
                                                "location_mode",
                                                i22);
                                        SleepModePolicyController.this.mLogger.add("Enable GPS");
                                    }
                                    SharePrefUtils.putInt(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_location_key",
                                            0);
                                    break;
                                default:
                                    Slog.d("SleepModePolicyController", "Wifi cancelAction");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (SharePrefUtils.getBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_wifi_key")) {
                                        Settings.Global.putInt(
                                                sleepModeAction2.mContext.getContentResolver(),
                                                "wifi_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable Wifi scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_wifi_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            int i22;
                            boolean z;
                            switch (i2) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "GPS triggerAction");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    LocationManager locationManager =
                                            (LocationManager)
                                                    sleepModeAction.mContext.getSystemService(
                                                            "location");
                                    if (locationManager != null) {
                                        z = locationManager.isLocationEnabled();
                                        i22 =
                                                Settings.Secure.getInt(
                                                        sleepModeAction.mContext
                                                                .getContentResolver(),
                                                        "location_mode",
                                                        0);
                                    } else {
                                        i22 =
                                                Settings.Secure.getInt(
                                                        sleepModeAction.mContext
                                                                .getContentResolver(),
                                                        "location_mode",
                                                        0);
                                        z = i22 != 0;
                                    }
                                    if (!z) {
                                        SharePrefUtils.putInt(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_location_key",
                                                0);
                                        break;
                                    } else {
                                        Settings.Secure.putInt(
                                                sleepModeAction.mContext.getContentResolver(),
                                                "location_mode",
                                                0);
                                        SharePrefUtils.putInt(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_location_key",
                                                i22);
                                        SleepModePolicyController.this.mLogger.add("Disable GPS");
                                        SleepModePolicyController sleepModePolicyController =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController,
                                                sleepModePolicyController.mSysState | 4);
                                        break;
                                    }
                                default:
                                    Slog.d("SleepModePolicyController", "Wifi triggerAction");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (Settings.Global.getInt(
                                                    sleepModeAction2.mContext.getContentResolver(),
                                                    "wifi_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                sleepModeAction2.mContext.getContentResolver(),
                                                "wifi_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction2.mContext,
                                                "pref_sleep_mode_wifi_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 2);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable Wifi scan");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            this.btCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i2) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i2) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i3 = 2;
            this.nearbyCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i3) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i3) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i4 = 3;
            this.masterSyncCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i4) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i4) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i5 = 4;
            this.notificationCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i5) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i5) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i6 = 5;
            this.cameraFlashNotificationCallBack =
                    new SleepModeCallBack(this) { // from class:
                        // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
                        public final /* synthetic */ SleepModeAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void cancelAction() {
                            switch (i6) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM cancelAction");
                                    boolean z =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_psm_key");
                                    SleepModeAction sleepModeAction = this.this$1;
                                    if (z) {
                                        SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, false);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable low power mode");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction.mContext,
                                            "pref_sleep_mode_psm_key",
                                            false);
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_bt_key")) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable BT scan");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_bt_key", false);
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_nearby_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                1);
                                        SleepModePolicyController.this.mLogger.add("Enable nearby");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_nearby_key", false);
                                    break;
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context, "pref_sleep_mode_master_sync_key")) {
                                        ContentResolver.setMasterSyncAutomatically(true);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable master sync");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context, "pref_sleep_mode_master_sync_key", false);
                                    break;
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification cancelAction");
                                    boolean z2 =
                                            SharePrefUtils.getBoolean(
                                                    context, "pref_sleep_mode_notification_key");
                                    SleepModeAction sleepModeAction2 = this.this$1;
                                    if (z2) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                0);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be always");
                                    }
                                    SharePrefUtils.putBoolean(
                                            sleepModeAction2.mContext,
                                            "pref_sleep_mode_notification_key",
                                            false);
                                    break;
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification cancelAction");
                                    if (SharePrefUtils.getBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key")) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                1);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Enable camera flash notification");
                                    }
                                    SharePrefUtils.putBoolean(
                                            context,
                                            "pref_sleep_mode_camera_flash_notification_key",
                                            false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
                        public final void triggerAction() {
                            SleepModeAction sleepModeAction = this.this$1;
                            switch (i6) {
                                case 0:
                                    Slog.d("SleepModePolicyController", "PSM triggerAction");
                                    Context context2 = sleepModeAction.mContext;
                                    boolean z = SleepModeUtil.DEBUG;
                                    boolean z2 =
                                            Settings.Global.getInt(
                                                            context2.getContentResolver(),
                                                            "low_power",
                                                            0)
                                                    == 1;
                                    boolean z3 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "ultra_powersaving_mode",
                                                            0)
                                                    == 1;
                                    boolean z4 =
                                            Settings.System.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "emergency_mode",
                                                            0)
                                                    == 1;
                                    boolean z5 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "sem_power_mode_limited_apps_and_home_screen",
                                                            0)
                                                    == 1;
                                    boolean z6 =
                                            Settings.Global.getInt(
                                                            sleepModeAction.mContext
                                                                    .getContentResolver(),
                                                            "adaptive_power_saving_setting",
                                                            0)
                                                    == 1;
                                    if (!z5 && !z2 && !z3 && !z4 && !z6) {
                                        if (SleepModeUtil.handlePowerSavingModeViaApi(
                                                sleepModeAction.mContext, true)) {
                                            SharePrefUtils.putBoolean(
                                                    sleepModeAction.mContext,
                                                    "pref_sleep_mode_psm_key",
                                                    true);
                                            SleepModePolicyController sleepModePolicyController =
                                                    SleepModePolicyController.this;
                                            SleepModePolicyController.m582$$Nest$msetSysState(
                                                    sleepModePolicyController,
                                                    1 | sleepModePolicyController.mSysState);
                                            SleepModePolicyController.this.mLogger.add(
                                                    "Enable low power mode");
                                            break;
                                        }
                                    } else {
                                        SharePrefUtils.putBoolean(
                                                sleepModeAction.mContext,
                                                "pref_sleep_mode_psm_key",
                                                false);
                                        if (!z5) {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": already PSM enabled. It will not enable PSM"
                                                        + " control by SleepPSM");
                                            break;
                                        } else {
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    ": limit app and home enabled. It will not"
                                                        + " enable PSM control by SleepPSM");
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
                                    if (Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "ble_scan_always_enabled",
                                                    0)
                                            == 1) {
                                        Settings.Global.putInt(
                                                context.getContentResolver(),
                                                "ble_scan_always_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_bt_key", true);
                                        SleepModePolicyController sleepModePolicyController2 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController2,
                                                sleepModePolicyController2.mSysState | 8);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable BT scan");
                                        break;
                                    }
                                    break;
                                case 2:
                                    Slog.d("SleepModePolicyController", "Nearby triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "nearby_scanning_enabled",
                                                    0)
                                            != 1) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "nearby_scanning_enabled",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_nearby_key", true);
                                        SleepModePolicyController sleepModePolicyController3 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController3,
                                                sleepModePolicyController3.mSysState | 16);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable nearby");
                                        break;
                                    }
                                case 3:
                                    Slog.d("SleepModePolicyController", "MasterSync triggerAction");
                                    if (!ContentResolver.getMasterSyncAutomatically()) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", false);
                                        break;
                                    } else {
                                        ContentResolver.setMasterSyncAutomatically(false);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_master_sync_key", true);
                                        SleepModePolicyController sleepModePolicyController4 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController4,
                                                sleepModePolicyController4.mSysState | 32);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable master sync");
                                        break;
                                    }
                                case 4:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "Notification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "edge_lighting_show_condition",
                                                    0)
                                            != 0) {
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", false);
                                        break;
                                    } else {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "edge_lighting_show_condition",
                                                1);
                                        SharePrefUtils.putBoolean(
                                                context, "pref_sleep_mode_notification_key", true);
                                        SleepModePolicyController sleepModePolicyController5 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController5,
                                                sleepModePolicyController5.mSysState | 64);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Set edge lighting condition to be screen on");
                                        break;
                                    }
                                default:
                                    Slog.d(
                                            "SleepModePolicyController",
                                            "CameraFlashNotification triggerAction");
                                    if (Settings.System.getInt(
                                                    context.getContentResolver(),
                                                    "camera_flash_notification",
                                                    0)
                                            == 1) {
                                        Settings.System.putInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0);
                                        SharePrefUtils.putBoolean(
                                                context,
                                                "pref_sleep_mode_camera_flash_notification_key",
                                                true);
                                        SleepModePolicyController sleepModePolicyController6 =
                                                SleepModePolicyController.this;
                                        SleepModePolicyController.m582$$Nest$msetSysState(
                                                sleepModePolicyController6,
                                                sleepModePolicyController6.mSysState | 128);
                                        SleepModePolicyController.this.mLogger.add(
                                                "Disable camera flash notification");
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
        }
    }

    public interface SleepModeCallBack {
        void cancelAction();

        void triggerAction();
    }

    public final class SleepModeHandler extends Handler {
        public SleepModeHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    SleepModePolicyController sleepModePolicyController =
                            SleepModePolicyController.this;
                    sleepModePolicyController.getClass();
                    Slog.i("SleepModePolicyController", "handleSleepModeStartEvent");
                    if (sleepModePolicyController.mInited) {
                        return;
                    }
                    sleepModePolicyController.initBroadcast(true);
                    sleepModePolicyController.mInited = true;
                    return;
                case 2:
                    SleepModePolicyController sleepModePolicyController2 =
                            SleepModePolicyController.this;
                    sleepModePolicyController2.getClass();
                    Slog.i("SleepModePolicyController", "handleSleepModeStopEvent");
                    if (sleepModePolicyController2.mInited) {
                        sleepModePolicyController2.initBroadcast(false);
                        sleepModePolicyController2.mInited = false;
                    }
                    sleepModePolicyController2.initAlarm(false);
                    return;
                case 3:
                    SleepModePolicyController sleepModePolicyController3 =
                            SleepModePolicyController.this;
                    sleepModePolicyController3.getClass();
                    Slog.i("SleepModePolicyController", "handleAlarmStartEvent");
                    if (sleepModePolicyController3.isSleepModeActivated()
                            || !sleepModePolicyController3.isIdleStatus()) {
                        return;
                    }
                    sleepModePolicyController3.enterSleepMode();
                    return;
                case 4:
                    SleepModePolicyController sleepModePolicyController4 =
                            SleepModePolicyController.this;
                    sleepModePolicyController4.getClass();
                    Slog.i("SleepModePolicyController", "handleAlarmEndEvent");
                    if (sleepModePolicyController4.isSleepModeActivated()) {
                        sleepModePolicyController4.exitSleepMode("reason_alarm_end");
                        return;
                    }
                    return;
                case 5:
                    SleepModePolicyController sleepModePolicyController5 =
                            SleepModePolicyController.this;
                    sleepModePolicyController5.getClass();
                    Slog.i("SleepModePolicyController", "handleIdleChangedEvent");
                    boolean isSleepModeActivated =
                            sleepModePolicyController5.isSleepModeActivated();
                    if (!sleepModePolicyController5.isIdleStatus()) {
                        if (isSleepModeActivated) {
                            if (!SleepModeUtil.isDeviceIdleMode(sleepModePolicyController5.mContext)
                                    && (SleepModeUtil.isScreenOn(
                                                    sleepModePolicyController5.mContext)
                                            || SleepModeUtil.isPowerConnected(
                                                    sleepModePolicyController5.mContext))) {
                                z = true;
                            }
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    "revort state is ", "SleepModePolicyController", z);
                            if (z) {
                                sleepModePolicyController5.exitSleepMode("reason_idle_change");
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (sleepModePolicyController5.mStartTime != null
                            && sleepModePolicyController5.mEndTime != null) {
                        Calendar calendar = Calendar.getInstance();
                        Calendar dateTimeBefore =
                                sleepModePolicyController5.mStartTime.getDateTimeBefore(calendar);
                        LocalTime localTime = sleepModePolicyController5.mEndTime;
                        z =
                                calendar.before(
                                        localTime.getDateTimeAfter(
                                                dateTimeBefore,
                                                sleepModePolicyController5.mStartTime.equals(
                                                        localTime)));
                    }
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "isDuringUserSetupTime: isInSetupTime = ",
                            "SleepModePolicyController",
                            z);
                    if (!z || isSleepModeActivated) {
                        return;
                    }
                    sleepModePolicyController5.enterSleepMode();
                    return;
                case 6:
                    SleepModePolicyController sleepModePolicyController6 =
                            SleepModePolicyController.this;
                    String str = (String) message.obj;
                    sleepModePolicyController6.getClass();
                    Slog.i("SleepModePolicyController", "handleStatusCheckEvent");
                    if (!sleepModePolicyController6.isSleepModeActivated()
                            || sleepModePolicyController6.isIdleStatus()) {
                        return;
                    }
                    sleepModePolicyController6.exitSleepMode(str);
                    return;
                case 7:
                    SleepModePolicyController sleepModePolicyController7 =
                            SleepModePolicyController.this;
                    sleepModePolicyController7.getClass();
                    Slog.i("SleepModePolicyController", "handleSetTimeEvent");
                    ArrayMap arrayMap = (ArrayMap) message.obj;
                    sleepModePolicyController7.mStartTime = (LocalTime) arrayMap.get("start_time");
                    sleepModePolicyController7.mEndTime = (LocalTime) arrayMap.get("end_time");
                    sleepModePolicyController7.initAlarm(true);
                    return;
                case 8:
                    SleepModePolicyController sleepModePolicyController8 =
                            SleepModePolicyController.this;
                    sleepModePolicyController8.getClass();
                    Slog.i("SleepModePolicyController", "handleTimeChangedEvent");
                    sleepModePolicyController8.initAlarm(true);
                    return;
                case 9:
                    SleepModePolicyController sleepModePolicyController9 =
                            SleepModePolicyController.this;
                    String str2 = (String) message.obj;
                    sleepModePolicyController9.getClass();
                    Slog.i("SleepModePolicyController", "handleSleepModeExitEvent");
                    if (sleepModePolicyController9.isSleepModeActivated()) {
                        sleepModePolicyController9.exitSleepMode(str2);
                        return;
                    }
                    return;
                case 10:
                    SleepModePolicyController sleepModePolicyController10 =
                            SleepModePolicyController.this;
                    if (sleepModePolicyController10.mInited) {
                        sleepModePolicyController10.initBroadcast(false);
                        sleepModePolicyController10.mInited = false;
                    }
                    if (sleepModePolicyController10.isSleepModeActivated()) {
                        sleepModePolicyController10.exitSleepMode("reason_package_removed");
                    }
                    sleepModePolicyController10.initAlarm(false);
                    sleepModePolicyController10.mSleepModeEnabled = false;
                    SharedPreferences.Editor edit =
                            sleepModePolicyController10
                                    .mContext
                                    .getSharedPreferences("sleep_mode_pref", 0)
                                    .edit();
                    edit.clear();
                    edit.apply();
                    return;
                case 11:
                    SleepModePolicyController.this.getClass();
                    Slog.i("SleepModePolicyController", "handleBeforeBedtimeEvent");
                    return;
                case 12:
                    SleepModePolicyController sleepModePolicyController11 =
                            SleepModePolicyController.this;
                    sleepModePolicyController11.getClass();
                    Slog.i("SleepModePolicyController", "handleProbablyAsleepEvent");
                    if (!sleepModePolicyController11.isIdleStatus()
                            || sleepModePolicyController11.isSleepModeActivated()) {
                        return;
                    }
                    sleepModePolicyController11.enterSleepMode();
                    return;
                case 13:
                    SleepModePolicyController sleepModePolicyController12 =
                            SleepModePolicyController.this;
                    sleepModePolicyController12.getClass();
                    Slog.i("SleepModePolicyController", "handleWakeupEvent");
                    if (sleepModePolicyController12.isSleepModeActivated()) {
                        sleepModePolicyController12.exitSleepMode("reason_wakeup");
                        return;
                    }
                    return;
                case 14:
                    SleepModePolicyController sleepModePolicyController13 =
                            SleepModePolicyController.this;
                    sleepModePolicyController13.getClass();
                    Slog.d("SleepModePolicyController", "handleBootCompleteEvent");
                    if (sleepModePolicyController13.isSleepModeActivated()) {
                        synchronized (sleepModePolicyController13.mActionsLock) {
                            sleepModePolicyController13.mLogger.add("recoverSleepMode");
                            sleepModePolicyController13.mEntryArrayList.forEach(
                                    new SleepModePolicyController$$ExternalSyntheticLambda1(2));
                            SharePrefUtils.putBoolean(
                                    sleepModePolicyController13.mContext,
                                    "pref_sleep_mode_activated_key",
                                    false);
                            Context context = sleepModePolicyController13.mContext;
                            boolean z2 = SleepModeUtil.DEBUG;
                            SharePrefUtils.putLong(
                                    context,
                                    "pref_sleep_mode_cancel_time_key",
                                    Calendar.getInstance().getTimeInMillis());
                        }
                    }
                    boolean z3 =
                            SharePrefUtils.getBoolean(
                                    sleepModePolicyController13.mContext,
                                    "pref_sleep_mode_enabled_key");
                    sleepModePolicyController13.mSleepModeEnabled = z3;
                    if (z3) {
                        Slog.d("SleepModePolicyController", "sleep mode enabled!");
                        long j =
                                SharePrefUtils.getLong(
                                        sleepModePolicyController13.mContext,
                                        "pref_sleep_mode_start_time_key",
                                        0L);
                        sleepModePolicyController13.mStartTime =
                                j != 0 ? LocalTime.valueOf(j) : null;
                        long j2 =
                                SharePrefUtils.getLong(
                                        sleepModePolicyController13.mContext,
                                        "pref_sleep_mode_end_time_key",
                                        0L);
                        sleepModePolicyController13.mEndTime =
                                j2 != 0 ? LocalTime.valueOf(j2) : null;
                        sleepModePolicyController13.mInited = true;
                        sleepModePolicyController13.initBroadcast(true);
                        sleepModePolicyController13.initAlarm(true);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public final class SleepModeReceiver extends BroadcastReceiver {
        public SleepModeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            Slog.d("SleepModePolicyController", "action is >> " + intent.getAction());
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.SCREEN_ON":
                    SleepModePolicyController sleepModePolicyController =
                            SleepModePolicyController.this;
                    sleepModePolicyController.getClass();
                    Slog.d("SleepModePolicyController", "sendCheckStatusMessage");
                    SleepModeHandler sleepModeHandler = sleepModePolicyController.mHandler;
                    if (sleepModeHandler != null) {
                        Message obtainMessage = sleepModeHandler.obtainMessage(6);
                        obtainMessage.obj = "reason_screen_on";
                        sleepModeHandler.sendMessage(obtainMessage);
                        break;
                    }
                    break;
                case "android.intent.action.TIME_SET":
                    SleepModePolicyController sleepModePolicyController2 =
                            SleepModePolicyController.this;
                    sleepModePolicyController2.getClass();
                    Slog.d("SleepModePolicyController", "sendTimeChangedMessage");
                    SleepModeHandler sleepModeHandler2 = sleepModePolicyController2.mHandler;
                    if (sleepModeHandler2 != null) {
                        sleepModeHandler2.sendMessage(sleepModeHandler2.obtainMessage(8));
                        break;
                    }
                    break;
                case "android.intent.action.BOOT_COMPLETED":
                    SleepModePolicyController sleepModePolicyController3 =
                            SleepModePolicyController.this;
                    sleepModePolicyController3.getClass();
                    Slog.d("SleepModePolicyController", "sendBootCompleteMessage");
                    SleepModeHandler sleepModeHandler3 = sleepModePolicyController3.mHandler;
                    if (sleepModeHandler3 != null) {
                        sleepModeHandler3.sendMessage(sleepModeHandler3.obtainMessage(14));
                        break;
                    }
                    break;
                case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                    SleepModePolicyController sleepModePolicyController4 =
                            SleepModePolicyController.this;
                    sleepModePolicyController4.getClass();
                    Slog.d("SleepModePolicyController", "sendIdleChangedMessage");
                    SleepModeHandler sleepModeHandler4 = sleepModePolicyController4.mHandler;
                    if (sleepModeHandler4 != null) {
                        sleepModeHandler4.sendMessage(sleepModeHandler4.obtainMessage(5));
                        break;
                    }
                    break;
                case "android.intent.action.ACTION_POWER_CONNECTED":
                    SleepModePolicyController sleepModePolicyController5 =
                            SleepModePolicyController.this;
                    sleepModePolicyController5.getClass();
                    Slog.d("SleepModePolicyController", "sendCheckStatusMessage");
                    SleepModeHandler sleepModeHandler5 = sleepModePolicyController5.mHandler;
                    if (sleepModeHandler5 != null) {
                        Message obtainMessage2 = sleepModeHandler5.obtainMessage(6);
                        obtainMessage2.obj = "reason_charging";
                        sleepModeHandler5.sendMessage(obtainMessage2);
                        break;
                    }
                    break;
                case "android.intent.action.PACKAGE_FULLY_REMOVED":
                    final SleepModePolicyController sleepModePolicyController6 =
                            SleepModePolicyController.this;
                    sleepModePolicyController6.getClass();
                    Optional.ofNullable(intent.getData())
                            .ifPresent(
                                    new Consumer() { // from class:
                                                     // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            SleepModePolicyController.SleepModeHandler
                                                    sleepModeHandler6;
                                            SleepModePolicyController sleepModePolicyController7 =
                                                    SleepModePolicyController.this;
                                            sleepModePolicyController7.getClass();
                                            if (!"com.samsung.android.statsd"
                                                            .equals(
                                                                    ((Uri) obj)
                                                                            .getSchemeSpecificPart())
                                                    || (sleepModeHandler6 =
                                                                    sleepModePolicyController7
                                                                            .mHandler)
                                                            == null) {
                                                return;
                                            }
                                            Slog.d(
                                                    "SleepModePolicyController",
                                                    "sendPackageRemovedMessage");
                                            sleepModeHandler6.sendMessage(
                                                    sleepModeHandler6.obtainMessage(10));
                                        }
                                    });
                    break;
                case "android.intent.action.ACTION_SHUTDOWN":
                    SleepModePolicyController sleepModePolicyController7 =
                            SleepModePolicyController.this;
                    sleepModePolicyController7.getClass();
                    Slog.d("SleepModePolicyController", "sendExitSleepModeMessage");
                    SleepModeHandler sleepModeHandler6 = sleepModePolicyController7.mHandler;
                    if (sleepModeHandler6 != null) {
                        Message obtainMessage3 = sleepModeHandler6.obtainMessage(9);
                        obtainMessage3.obj = "reason_shutdown";
                        sleepModeHandler6.sendMessage(obtainMessage3);
                        break;
                    }
                    break;
                case "android.intent.action.REBOOT":
                    SleepModePolicyController sleepModePolicyController8 =
                            SleepModePolicyController.this;
                    sleepModePolicyController8.getClass();
                    Slog.d("SleepModePolicyController", "sendExitSleepModeMessage");
                    SleepModeHandler sleepModeHandler7 = sleepModePolicyController8.mHandler;
                    if (sleepModeHandler7 != null) {
                        Message obtainMessage4 = sleepModeHandler7.obtainMessage(9);
                        obtainMessage4.obj = "reason_reboot";
                        sleepModeHandler7.sendMessage(obtainMessage4);
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mregisterSleepModeAction, reason: not valid java name */
    public static void m581$$Nest$mregisterSleepModeAction(
            SleepModePolicyController sleepModePolicyController,
            String str,
            SleepModeCallBack sleepModeCallBack) {
        synchronized (sleepModePolicyController.mActionsLock) {
            ActionEntry actionEntry = new ActionEntry();
            actionEntry.tag = str;
            actionEntry.callBack = sleepModeCallBack;
            sleepModePolicyController.mEntryArrayList.add(actionEntry);
        }
    }

    /* renamed from: -$$Nest$msetSysState, reason: not valid java name */
    public static void m582$$Nest$msetSysState(
            SleepModePolicyController sleepModePolicyController, int i) {
        int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        if (i2 != sleepModePolicyController.mSysState) {
            sleepModePolicyController.mSysState = i2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda0] */
    public SleepModePolicyController(
            Context context, HandlerThread handlerThread, SleepModeLogger sleepModeLogger) {
        this.mContext = context;
        this.mLogger = sleepModeLogger;
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SleepModeReceiver sleepModeReceiver = new SleepModeReceiver();
        SleepModeAction sleepModeAction = new SleepModeAction(context);
        m581$$Nest$mregisterSleepModeAction(
                this, "PMS_SleepModeAction", sleepModeAction.psmCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "GPS_SleepModeAction", sleepModeAction.gpsCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "Wifi_SleepModeAction", sleepModeAction.wifiCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "BlueTooth_SleepModeAction", sleepModeAction.btCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "Nearby_SleepModeAction", sleepModeAction.nearbyCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "MasterSync_SleepModeAction", sleepModeAction.masterSyncCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this, "Notification_SleepModeAction", sleepModeAction.notificationCallBack);
        m581$$Nest$mregisterSleepModeAction(
                this,
                "CF_Notification_SleepModeAction",
                sleepModeAction.cameraFlashNotificationCallBack);
        this.mHandler = new SleepModeHandler(handlerThread.getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        context.registerReceiver(sleepModeReceiver, intentFilter, 2);
    }

    public final void enterSleepMode() {
        if (!this.mSleepModeEnabled) {
            Slog.d(
                    "SleepModePolicyController",
                    "UI switch off disable the sleep mode restriction.");
            return;
        }
        synchronized (this.mActionsLock) {
            this.mLogger.add("enterSleepMode");
            this.mSysState = 0;
            this.mEntryArrayList.forEach(
                    new SleepModePolicyController$$ExternalSyntheticLambda1(0));
            SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_activated_key", true);
            Context context = this.mContext;
            boolean z = SleepModeUtil.DEBUG;
            SharePrefUtils.putLong(
                    context,
                    "pref_sleep_mode_trigger_time_key",
                    Calendar.getInstance().getTimeInMillis());
            SharePrefUtils.putInt(
                    this.mContext, "pref_sleep_mode_policy_state_key", this.mSysState);
            initDeviceStatusBroadcast(true);
        }
    }

    public final void exitSleepMode(String str) {
        if (!this.mSleepModeEnabled) {
            Slog.d(
                    "SleepModePolicyController",
                    "UI switch off disable the cancel sleep mode restriction.");
            return;
        }
        synchronized (this.mActionsLock) {
            this.mLogger.add("exitSleepMode " + str);
            this.mEntryArrayList.forEach(
                    new SleepModePolicyController$$ExternalSyntheticLambda1(1));
            SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_activated_key", false);
            Context context = this.mContext;
            boolean z = SleepModeUtil.DEBUG;
            SharePrefUtils.putLong(
                    context,
                    "pref_sleep_mode_cancel_time_key",
                    Calendar.getInstance().getTimeInMillis());
            initDeviceStatusBroadcast(false);
        }
    }

    public final Bundle getSleepTime() {
        Slog.d("SleepModePolicyController", "getSleepTime");
        long j = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_start_time_key", -1L);
        long j2 = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_end_time_key", -1L);
        if (j == -1 || j2 == -1) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("bundle_start_sleep_time_key", j);
        bundle.putLong("bundle_end_sleep_time_key", j2);
        return bundle;
    }

    public final void initAlarm(boolean z) {
        SleepModePolicyController$$ExternalSyntheticLambda0
                sleepModePolicyController$$ExternalSyntheticLambda0 = this.mTimeoutAlarmListener;
        if (sleepModePolicyController$$ExternalSyntheticLambda0 != null) {
            this.mAlarmManager.cancel(sleepModePolicyController$$ExternalSyntheticLambda0);
        }
        if (z) {
            updateActivated();
        }
    }

    public final void initBroadcast(boolean z) {
        SleepModeReceiver sleepModeReceiver = this.mReceiver;
        if (!z) {
            this.mContext.unregisterReceiver(sleepModeReceiver);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        this.mContext.registerReceiver(sleepModeReceiver, intentFilter, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiver(sleepModeReceiver, intentFilter2, 2);
    }

    public final void initDeviceStatusBroadcast(boolean z) {
        SleepModeReceiver sleepModeReceiver = this.mDeviceStatusReceiver;
        if (!z) {
            this.mContext.unregisterReceiver(sleepModeReceiver);
            return;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        this.mContext.registerReceiver(sleepModeReceiver, intentFilter, 2);
    }

    public final boolean isIdleStatus() {
        boolean z =
                (!SleepModeUtil.isDeviceIdleMode(this.mContext)
                                || SleepModeUtil.isScreenOn(this.mContext)
                                || SleepModeUtil.isPowerConnected(this.mContext))
                        ? false
                        : true;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "current idle status is ", "SleepModePolicyController", z);
        return z;
    }

    public final boolean isSleepModeActivated() {
        boolean z = SharePrefUtils.getBoolean(this.mContext, "pref_sleep_mode_activated_key");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "isSleepModeActivated to be ", "SleepModePolicyController", z);
        return z;
    }

    public final void setRubinEvent(String str) {
        if (!this.mSleepModeEnabled) {
            Slog.d(
                    "SleepModePolicyController",
                    "sleep mode off, doesn't deal with runstone event!");
            return;
        }
        boolean equalsIgnoreCase = str.equalsIgnoreCase("BEFORE_BEDTIME");
        SleepModeHandler sleepModeHandler = this.mHandler;
        if (equalsIgnoreCase) {
            Slog.i("SleepModePolicyController", "BEFORE_BEDTIME");
            sleepModeHandler.sendEmptyMessage(11);
        } else if (str.equalsIgnoreCase("PROBABLY_ASLEEP")) {
            Slog.i("SleepModePolicyController", "PROBABLY_ASLEEP");
            sleepModeHandler.sendEmptyMessage(12);
        } else if (str.equalsIgnoreCase("WAKEUP")) {
            Slog.i("SleepModePolicyController", "WAKEUP");
            sleepModeHandler.sendEmptyMessage(13);
        }
    }

    public final void setSleepModeEnable(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setSleepModeEnable >> ", "SleepModePolicyController", z);
        SleepModeHandler sleepModeHandler = this.mHandler;
        if (z) {
            Slog.d("SleepModePolicyController", "startSleepModePolicy");
            sleepModeHandler.sendEmptyMessage(1);
        } else {
            Slog.d("SleepModePolicyController", "stopSleepModePolicy");
            sleepModeHandler.sendEmptyMessage(2);
        }
        this.mSleepModeEnabled = z;
        SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_enabled_key", z);
    }

    public final void setSleepTime(long j, long j2) {
        Slog.d("SleepModePolicyController", "setSleepTime");
        Slog.d("SleepModePolicyController", "sendSetTimeMessage");
        SleepModeHandler sleepModeHandler = this.mHandler;
        if (sleepModeHandler != null) {
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put("start_time", LocalTime.valueOf(j));
            arrayMap.put("end_time", LocalTime.valueOf(j2));
            sleepModeHandler.sendMessage(sleepModeHandler.obtainMessage(7, arrayMap));
        }
        SharePrefUtils.putLong(this.mContext, "pref_sleep_mode_start_time_key", j);
        SharePrefUtils.putLong(this.mContext, "pref_sleep_mode_end_time_key", j2);
    }

    public final boolean testState(int i) {
        return (this.mSysState & i) == i;
    }

    public final void updateActivated() {
        Calendar dateTimeAfter;
        Slog.d("SleepModePolicyController", "updateActivated");
        if (this.mStartTime == null || this.mEndTime == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        Calendar dateTimeBefore = this.mStartTime.getDateTimeBefore(calendar);
        LocalTime localTime = this.mEndTime;
        boolean before =
                calendar.before(
                        localTime.getDateTimeAfter(
                                dateTimeBefore, this.mStartTime.equals(localTime)));
        if (before) {
            LocalTime localTime2 = this.mEndTime;
            dateTimeAfter =
                    localTime2.getDateTimeAfter(calendar, this.mStartTime.equals(localTime2));
        } else {
            dateTimeAfter = this.mStartTime.getDateTimeAfter(calendar, false);
        }
        SleepModeHandler sleepModeHandler = this.mHandler;
        if (before) {
            Slog.d("SleepModePolicyController", "sendAlarmStartMessage");
            if (sleepModeHandler != null) {
                sleepModeHandler.sendMessage(sleepModeHandler.obtainMessage(3));
            }
            Slog.d(
                    "SleepModePolicyController",
                    "In Active Duration, set inactive alarm at "
                            + dateTimeAfter.get(11)
                            + ":"
                            + dateTimeAfter.get(12));
        } else {
            Slog.d("SleepModePolicyController", "sendAlarmEndMessage");
            if (sleepModeHandler != null) {
                sleepModeHandler.sendMessage(sleepModeHandler.obtainMessage(4));
            }
            Slog.d(
                    "SleepModePolicyController",
                    "Out Active Duration, set active alarm at "
                            + dateTimeAfter.get(11)
                            + ":"
                            + dateTimeAfter.get(12));
        }
        this.mAlarmManager.setExact(
                0,
                dateTimeAfter.getTimeInMillis(),
                "SleepModePolicyController",
                this.mTimeoutAlarmListener,
                null);
    }
}
