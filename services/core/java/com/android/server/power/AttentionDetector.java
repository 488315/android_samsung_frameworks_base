package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.app.SynchronousUserSwitchObserver;
import android.attention.AttentionManagerInternal;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.provider.Settings;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import com.android.server.wm.WindowManagerInternal;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AttentionDetector {
    protected AttentionManagerInternal mAttentionManager;
    AttentionCallbackInternalImpl mCallback;
    protected ContentResolver mContentResolver;
    public Context mContext;
    protected long mDefaultMaximumExtensionMillis;
    public long mEffectivePostDimTimeoutMillis;
    public boolean mIsSettingEnabled;
    public long mLastActedOnNextScreenDimming;
    public long mLastUserActivityTime;
    public final Object mLock;
    public long mMaximumExtensionMillis;
    public final Runnable mOnUserAttention;
    protected long mPreDimCheckDurationMillis;
    public long mRequestedPostDimTimeoutMillis;
    protected WindowManagerInternal mWindowManager;
    public final AtomicLong mConsecutiveTimeoutExtendedCount = new AtomicLong(0);
    public final AtomicBoolean mRequested = new AtomicBoolean(false);
    protected int mRequestId = 0;
    public int mWakefulness = 1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class AttentionCallbackInternalImpl
            extends AttentionManagerInternal.AttentionCallbackInternal {
        public final int mId;

        public AttentionCallbackInternalImpl(int i) {
            this.mId = i;
        }

        public final void onFailure(int i) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Failed to check attention: ", ", ID: "),
                    this.mId,
                    "AttentionDetector");
            AttentionDetector.this.mRequested.set(false);
        }

        public final void onSuccess(int i, long j) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(i, "onSuccess: ", ", ID: "),
                    this.mId,
                    "AttentionDetector");
            int i2 = this.mId;
            AttentionDetector attentionDetector = AttentionDetector.this;
            if (i2 == attentionDetector.mRequestId
                    && attentionDetector.mRequested.getAndSet(false)) {
                synchronized (AttentionDetector.this.mLock) {
                    try {
                        AttentionDetector attentionDetector2 = AttentionDetector.this;
                        if (attentionDetector2.mWakefulness != 1) {
                            return;
                        }
                        if (i == 1) {
                            attentionDetector2.mOnUserAttention.run();
                        } else {
                            attentionDetector2.resetConsecutiveExtensionCount();
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserSwitchObserver extends SynchronousUserSwitchObserver {
        public UserSwitchObserver() {}

        public final void onUserSwitching(int i) {
            AttentionDetector attentionDetector = AttentionDetector.this;
            attentionDetector.updateEnabledFromSettings(attentionDetector.mContext);
        }
    }

    public AttentionDetector(
            PowerManagerService$$ExternalSyntheticLambda1
                    powerManagerService$$ExternalSyntheticLambda1,
            Object obj) {
        this.mOnUserAttention = powerManagerService$$ExternalSyntheticLambda1;
        this.mLock = obj;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m$1(
                                                                                printWriter,
                                                                                "AttentionDetector:",
                                                                                " mIsSettingEnabled="),
                                                                this.mIsSettingEnabled,
                                                                printWriter,
                                                                " mMaxExtensionMillis="),
                                                this.mMaximumExtensionMillis,
                                                printWriter,
                                                " mPreDimCheckDurationMillis="),
                                        this.mPreDimCheckDurationMillis,
                                        printWriter,
                                        " mEffectivePostDimTimeout="),
                                this.mEffectivePostDimTimeoutMillis,
                                printWriter,
                                " mLastUserActivityTime(excludingAttention)="),
                        this.mLastUserActivityTime,
                        printWriter,
                        " mAttentionServiceSupported=");
        m.append(isAttentionServiceSupported());
        printWriter.println(m.toString());
        printWriter.println(" mRequested=" + this.mRequested);
    }

    public long getMaxExtensionMillis() {
        long j =
                DeviceConfig.getLong(
                        "attention_manager_service",
                        "max_extension_millis",
                        this.mDefaultMaximumExtensionMillis);
        if (j >= 0 && j <= ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
            return j;
        }
        android.util.Slog.w(
                "AttentionDetector", "Bad flag value supplied for: max_extension_millis");
        return this.mDefaultMaximumExtensionMillis;
    }

    public long getPostDimCheckDurationMillis() {
        long j =
                DeviceConfig.getLong(
                        "attention_manager_service", "post_dim_check_duration_millis", 0L);
        if (j >= 0 && j <= 10000) {
            return j;
        }
        android.util.Slog.w(
                "AttentionDetector", "Bad flag value supplied for: post_dim_check_duration_millis");
        return 0L;
    }

    public long getPreDimCheckDurationMillis() {
        long j =
                DeviceConfig.getLong(
                        "attention_manager_service", "pre_dim_check_duration_millis", 2000L);
        if (j >= 0 && j <= 13000) {
            return j;
        }
        android.util.Slog.w(
                "AttentionDetector", "Bad flag value supplied for: pre_dim_check_duration_millis");
        return 2000L;
    }

    public boolean isAttentionServiceSupported() {
        AttentionManagerInternal attentionManagerInternal = this.mAttentionManager;
        return attentionManagerInternal != null
                && attentionManagerInternal.isAttentionServiceSupported();
    }

    public final void readValuesFromDeviceConfig() {
        this.mMaximumExtensionMillis = getMaxExtensionMillis();
        this.mPreDimCheckDurationMillis = getPreDimCheckDurationMillis();
        this.mRequestedPostDimTimeoutMillis = getPostDimCheckDurationMillis();
        android.util.Slog.i(
                "AttentionDetector",
                "readValuesFromDeviceConfig():\nmMaximumExtensionMillis="
                        + this.mMaximumExtensionMillis
                        + "\nmPreDimCheckDurationMillis="
                        + this.mPreDimCheckDurationMillis
                        + "\nmRequestedPostDimTimeoutMillis="
                        + this.mRequestedPostDimTimeoutMillis);
    }

    public final void resetConsecutiveExtensionCount() {
        long andSet = this.mConsecutiveTimeoutExtendedCount.getAndSet(0L);
        if (andSet > 0) {
            FrameworkStatsLog.write(168, andSet);
        }
    }

    public final void systemReady(final Context context) {
        this.mContext = context;
        updateEnabledFromSettings(context);
        this.mContentResolver = context.getContentResolver();
        this.mAttentionManager =
                (AttentionManagerInternal) LocalServices.getService(AttentionManagerInternal.class);
        this.mWindowManager =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mDefaultMaximumExtensionMillis =
                context.getResources().getInteger(R.integer.config_bg_current_drain_window);
        try {
            ActivityManager.getService()
                    .registerUserSwitchObserver(new UserSwitchObserver(), "AttentionDetector");
        } catch (RemoteException unused) {
        }
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("adaptive_sleep"),
                        false,
                        new ContentObserver(
                                new Handler(
                                        context
                                                .getMainLooper())) { // from class:
                                                                     // com.android.server.power.AttentionDetector.1
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                AttentionDetector.this.updateEnabledFromSettings(context);
                            }
                        },
                        -1);
        readValuesFromDeviceConfig();
        DeviceConfig.addOnPropertiesChangedListener(
                "attention_manager_service",
                context.getMainExecutor(),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.power.AttentionDetector$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        AttentionDetector attentionDetector = AttentionDetector.this;
                        attentionDetector.getClass();
                        for (String str : properties.getKeyset()) {
                            str.getClass();
                            switch (str) {
                                case "post_dim_check_duration_millis":
                                case "max_extension_millis":
                                case "pre_dim_check_duration_millis":
                                    attentionDetector.readValuesFromDeviceConfig();
                                    return;
                                default:
                                    android.util.Slog.i(
                                            "AttentionDetector", "Ignoring change on ".concat(str));
                            }
                        }
                    }
                });
    }

    public void updateEnabledFromSettings(Context context) {
        this.mIsSettingEnabled =
                Settings.Secure.getIntForUser(context.getContentResolver(), "adaptive_sleep", 0, -2)
                        == 1;
    }
}
