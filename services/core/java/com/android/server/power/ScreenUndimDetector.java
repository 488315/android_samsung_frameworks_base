package com.android.server.power;

import android.os.PowerManager;
import android.provider.DeviceConfig;

import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import java.util.concurrent.TimeUnit;

public final class ScreenUndimDetector {
    static final long DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS;
    static final long DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS;
    static final int DEFAULT_UNDIMS_REQUIRED = 2;
    static final String KEY_KEEP_SCREEN_ON_FOR_MILLIS = "keep_screen_on_for_millis";
    static final String KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS =
            "max_duration_between_undims_millis";
    static final String KEY_UNDIMS_REQUIRED = "undims_required";
    int mCurrentScreenPolicy;
    public boolean mKeepScreenOnEnabled;
    public long mKeepScreenOnForMillis;
    int mUndimCounter;
    long mUndimCounterStartedMillis;
    public int mUndimsRequired;
    PowerManager.WakeLock mWakeLock;

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS = timeUnit.toMillis(10L);
        DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS = timeUnit.toMillis(5L);
    }

    public void readValuesFromDeviceConfig() {
        this.mKeepScreenOnEnabled =
                DeviceConfig.getBoolean(
                        "attention_manager_service", "keep_screen_on_enabled", true);
        this.mKeepScreenOnForMillis =
                DeviceConfig.getLong(
                        "attention_manager_service",
                        KEY_KEEP_SCREEN_ON_FOR_MILLIS,
                        DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS);
        int i = 2;
        int i2 = DeviceConfig.getInt("attention_manager_service", KEY_UNDIMS_REQUIRED, 2);
        if (i2 < 1 || i2 > 5) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(
                    i2,
                    "Provided undimsRequired=",
                    " is not allowed [1, 5]; using the default=2",
                    "ScreenUndimDetector");
        } else {
            i = i2;
        }
        this.mUndimsRequired = i;
        DeviceConfig.getLong(
                "attention_manager_service",
                KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS,
                DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS);
        StringBuilder sb =
                new StringBuilder("readValuesFromDeviceConfig():\nmKeepScreenOnForMillis=");
        sb.append(this.mKeepScreenOnForMillis);
        sb.append("\nmKeepScreenOnNotificationEnabled=");
        sb.append(this.mKeepScreenOnEnabled);
        sb.append("\nmUndimsRequired=");
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                sb, this.mUndimsRequired, "ScreenUndimDetector");
    }

    public void reset() {
        this.mUndimCounter = 0;
        this.mUndimCounterStartedMillis = 0L;
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock == null || !wakeLock.isHeld()) {
            return;
        }
        this.mWakeLock.release();
    }
}
