package com.android.server.am;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BroadcastConstants {
    public static final long DEFAULT_ALLOW_BG_ACTIVITY_START_TIMEOUT;
    public static final int DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS;
    public static final int DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS;
    public static final int DEFAULT_MAX_HISTORY_COMPLETE_SIZE;
    public static final int DEFAULT_MAX_HISTORY_SUMMARY_SIZE;
    public static final int DEFAULT_MAX_PENDING_BROADCASTS;
    public static final int DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS;
    public static final int DEFAULT_MAX_RUNNING_PROCESS_QUEUES;
    public static final long DEFAULT_TIMEOUT;
    public static final int MAX_HISTORY_ABORTED_BROADCAST;
    public ContentResolver mResolver;
    public final String mSettingsKey;
    public SettingsObserver mSettingsObserver;
    public long TIMEOUT = DEFAULT_TIMEOUT;
    public long ALLOW_BG_ACTIVITY_START_TIMEOUT = DEFAULT_ALLOW_BG_ACTIVITY_START_TIMEOUT;
    public int MAX_RUNNING_PROCESS_QUEUES = DEFAULT_MAX_RUNNING_PROCESS_QUEUES;
    public int EXTRA_RUNNING_URGENT_PROCESS_QUEUES = 1;
    public int MAX_CONSECUTIVE_URGENT_DISPATCHES = 3;
    public int MAX_CONSECUTIVE_NORMAL_DISPATCHES = 10;
    public int MAX_RUNNING_ACTIVE_BROADCASTS = DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS;
    public int MAX_CORE_RUNNING_BLOCKING_BROADCASTS = DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS;
    public int MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS =
            DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS;
    public int MAX_PENDING_BROADCASTS = DEFAULT_MAX_PENDING_BROADCASTS;
    public long DELAY_NORMAL_MILLIS = 500;
    public long DELAY_CACHED_MILLIS = 120000;
    public long DELAY_URGENT_MILLIS = -120000;
    public long DELAY_FOREGROUND_PROC_MILLIS = -120000;
    public long DELAY_PERSISTENT_PROC_MILLIS = -120000;
    public int MAX_HISTORY_COMPLETE_SIZE = DEFAULT_MAX_HISTORY_COMPLETE_SIZE;
    public int MAX_HISTORY_SUMMARY_SIZE = DEFAULT_MAX_HISTORY_SUMMARY_SIZE;
    public boolean CORE_DEFER_UNTIL_ACTIVE = true;
    public long PENDING_COLD_START_CHECK_INTERVAL_MILLIS = 30000;
    public int MAX_FROZEN_OUTGOING_BROADCASTS = 32;
    public final KeyValueListParser mParser = new KeyValueListParser(',');

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            BroadcastConstants.this.updateSettingsConstants();
        }
    }

    static {
        ActivityManager.isLowRamDeviceStatic();
        MAX_HISTORY_ABORTED_BROADCAST = ActivityManager.isLowRamDeviceStatic() ? 1 : 3;
        long j = Build.HW_TIMEOUT_MULTIPLIER * 10000;
        DEFAULT_TIMEOUT = j;
        DEFAULT_ALLOW_BG_ACTIVITY_START_TIMEOUT = j;
        DEFAULT_MAX_RUNNING_PROCESS_QUEUES = ActivityManager.isLowRamDeviceStatic() ? 2 : 4;
        DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS = ActivityManager.isLowRamDeviceStatic() ? 8 : 16;
        DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS =
                ActivityManager.isLowRamDeviceStatic() ? 8 : 16;
        DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS =
                ActivityManager.isLowRamDeviceStatic() ? 32 : 64;
        DEFAULT_MAX_PENDING_BROADCASTS = ActivityManager.isLowRamDeviceStatic() ? 128 : 256;
        DEFAULT_MAX_HISTORY_COMPLETE_SIZE = ActivityManager.isLowRamDeviceStatic() ? 64 : 256;
        DEFAULT_MAX_HISTORY_SUMMARY_SIZE = ActivityManager.isLowRamDeviceStatic() ? 256 : 1024;
    }

    public BroadcastConstants(String str) {
        this.mSettingsKey = str;
        updateDeviceConfigConstants$1();
    }

    public static int getDeviceConfigInt(int i, String str) {
        return SystemProperties.getInt(
                "persist.sys.activity_manager_native_boot.".concat(str),
                SystemProperties.getInt(
                        "persist.device_config.activity_manager_native_boot.".concat(str), i));
    }

    public static long getDeviceConfigLong(long j, String str) {
        return SystemProperties.getLong(
                "persist.sys.activity_manager_native_boot.".concat(str),
                SystemProperties.getLong(
                        "persist.device_config.activity_manager_native_boot.".concat(str), j));
    }

    public final void startObserving(Handler handler, ContentResolver contentResolver) {
        this.mResolver = contentResolver;
        this.mSettingsObserver = new SettingsObserver(handler);
        this.mResolver.registerContentObserver(
                Settings.Global.getUriFor(this.mSettingsKey), false, this.mSettingsObserver);
        updateSettingsConstants();
        DeviceConfig.addOnPropertiesChangedListener(
                "activity_manager_native_boot",
                new HandlerExecutor(handler),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.am.BroadcastConstants$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        BroadcastConstants.this.updateDeviceConfigConstants$1();
                    }
                });
        updateDeviceConfigConstants$1();
    }

    public final void updateDeviceConfigConstants$1() {
        synchronized (this) {
            this.MAX_RUNNING_PROCESS_QUEUES =
                    getDeviceConfigInt(
                            DEFAULT_MAX_RUNNING_PROCESS_QUEUES, "bcast_max_running_process_queues");
            this.EXTRA_RUNNING_URGENT_PROCESS_QUEUES =
                    getDeviceConfigInt(1, "bcast_extra_running_urgent_process_queues");
            this.MAX_CONSECUTIVE_URGENT_DISPATCHES =
                    getDeviceConfigInt(3, "bcast_max_consecutive_urgent_dispatches");
            this.MAX_CONSECUTIVE_NORMAL_DISPATCHES =
                    getDeviceConfigInt(10, "bcast_max_consecutive_normal_dispatches");
            this.MAX_RUNNING_ACTIVE_BROADCASTS =
                    getDeviceConfigInt(
                            DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS,
                            "bcast_max_running_active_broadcasts");
            this.MAX_CORE_RUNNING_BLOCKING_BROADCASTS =
                    getDeviceConfigInt(
                            DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS,
                            "bcast_max_core_running_blocking_broadcasts");
            this.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS =
                    getDeviceConfigInt(
                            DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS,
                            "bcast_max_core_running_non_blocking_broadcasts");
            this.MAX_PENDING_BROADCASTS =
                    getDeviceConfigInt(
                            DEFAULT_MAX_PENDING_BROADCASTS, "bcast_max_pending_broadcasts");
            this.DELAY_NORMAL_MILLIS = getDeviceConfigLong(500L, "bcast_delay_normal_millis");
            this.DELAY_CACHED_MILLIS = getDeviceConfigLong(120000L, "bcast_delay_cached_millis");
            this.DELAY_URGENT_MILLIS = getDeviceConfigLong(-120000L, "bcast_delay_urgent_millis");
            this.DELAY_FOREGROUND_PROC_MILLIS =
                    getDeviceConfigLong(-120000L, "bcast_delay_foreground_proc_millis");
            this.DELAY_PERSISTENT_PROC_MILLIS =
                    getDeviceConfigLong(-120000L, "bcast_delay_persistent_proc_millis");
            this.MAX_HISTORY_COMPLETE_SIZE = ActivityManager.isLowRamDeviceStatic() ? 256 : 1024;
            this.MAX_HISTORY_SUMMARY_SIZE = ActivityManager.isLowRamDeviceStatic() ? 512 : 2048;
            this.CORE_DEFER_UNTIL_ACTIVE =
                    SystemProperties.getBoolean(
                            "persist.sys.activity_manager_native_boot."
                                    .concat("bcast_core_defer_until_active"),
                            SystemProperties.getBoolean(
                                    "persist.device_config.activity_manager_native_boot."
                                            .concat("bcast_core_defer_until_active"),
                                    true));
            this.PENDING_COLD_START_CHECK_INTERVAL_MILLIS =
                    getDeviceConfigLong(30000L, "pending_cold_start_check_interval_millis");
            this.MAX_FROZEN_OUTGOING_BROADCASTS =
                    getDeviceConfigInt(32, "max_frozen_outgoing_broadcasts");
        }
        BroadcastRecord.CORE_DEFER_UNTIL_ACTIVE = this.CORE_DEFER_UNTIL_ACTIVE;
    }

    public final void updateSettingsConstants() {
        synchronized (this) {
            try {
                try {
                    this.mParser.setString(
                            Settings.Global.getString(this.mResolver, this.mSettingsKey));
                    this.TIMEOUT = this.mParser.getLong("bcast_timeout", this.TIMEOUT);
                    this.ALLOW_BG_ACTIVITY_START_TIMEOUT =
                            this.mParser.getLong(
                                    "bcast_allow_bg_activity_start_timeout",
                                    this.ALLOW_BG_ACTIVITY_START_TIMEOUT);
                } catch (IllegalArgumentException e) {
                    Slog.e(
                            "BroadcastConstants",
                            "Bad broadcast settings in key '" + this.mSettingsKey + "'",
                            e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
