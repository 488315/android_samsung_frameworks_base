package com.android.server.power;

import android.content.Context;
import android.os.PowerManager;
import android.provider.DeviceConfig;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ScreenUndimDetector {
    static final long DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS;
    static final long DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS;
    static final int DEFAULT_UNDIMS_REQUIRED = 2;
    static final String KEY_KEEP_SCREEN_ON_FOR_MILLIS = "keep_screen_on_for_millis";
    static final String KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS = "max_duration_between_undims_millis";
    static final String KEY_UNDIMS_REQUIRED = "undims_required";
    int mCurrentScreenPolicy;
    public boolean mKeepScreenOnEnabled;
    public long mKeepScreenOnForMillis;
    public long mMaxDurationBetweenUndimsMillis;
    long mUndimCounterStartedMillis;
    public int mUndimsRequired;
    PowerManager.WakeLock mWakeLock;
    int mUndimCounter = 0;
    public long mUndimOccurredTime = -1;
    public long mInteractionAfterUndimTime = -1;
    public InternalClock mClock = new InternalClock();

    public class InternalClock {
    }

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS = timeUnit.toMillis(10L);
        DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS = timeUnit.toMillis(5L);
    }

    public void systemReady(Context context) {
        readValuesFromDeviceConfig();
        DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", context.getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.ScreenUndimDetector$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                ScreenUndimDetector.this.lambda$systemReady$0(properties);
            }
        });
        this.mWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(536870922, "UndimDetectorWakeLock");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0(DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
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

    public final boolean readKeepScreenOnNotificationEnabled() {
        return DeviceConfig.getBoolean("attention_manager_service", "keep_screen_on_enabled", true);
    }

    public final long readKeepScreenOnForMillis() {
        return DeviceConfig.getLong("attention_manager_service", KEY_KEEP_SCREEN_ON_FOR_MILLIS, DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS);
    }

    public final int readUndimsRequired() {
        int i = DeviceConfig.getInt("attention_manager_service", KEY_UNDIMS_REQUIRED, 2);
        if (i >= 1 && i <= 5) {
            return i;
        }
        android.util.Slog.e("ScreenUndimDetector", "Provided undimsRequired=" + i + " is not allowed [1, 5]; using the default=2");
        return 2;
    }

    public final long readMaxDurationBetweenUndimsMillis() {
        return DeviceConfig.getLong("attention_manager_service", KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS, DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS);
    }

    public final void onDeviceConfigChange(Set set) {
        String str;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            str = (String) it.next();
            android.util.Slog.i("ScreenUndimDetector", "onDeviceConfigChange; key=" + str);
            str.hashCode();
            switch (str) {
                case "undims_required":
                case "keep_screen_on_enabled":
                case "keep_screen_on_for_millis":
                case "max_duration_between_undims_millis":
                    readValuesFromDeviceConfig();
                    return;
                default:
                    android.util.Slog.i("ScreenUndimDetector", "Ignoring change on " + str);
            }
        }
    }

    public void readValuesFromDeviceConfig() {
        this.mKeepScreenOnEnabled = readKeepScreenOnNotificationEnabled();
        this.mKeepScreenOnForMillis = readKeepScreenOnForMillis();
        this.mUndimsRequired = readUndimsRequired();
        this.mMaxDurationBetweenUndimsMillis = readMaxDurationBetweenUndimsMillis();
        android.util.Slog.i("ScreenUndimDetector", "readValuesFromDeviceConfig():\nmKeepScreenOnForMillis=" + this.mKeepScreenOnForMillis + "\nmKeepScreenOnNotificationEnabled=" + this.mKeepScreenOnEnabled + "\nmUndimsRequired=" + this.mUndimsRequired);
    }
}
