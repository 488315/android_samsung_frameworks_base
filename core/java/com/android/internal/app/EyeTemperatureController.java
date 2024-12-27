package com.android.internal.app;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;

public final class EyeTemperatureController {
    private static final boolean DEBUG = true;
    private static final String TAG = "EyeTemperatureController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public EyeTemperatureController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public EyeTemperatureController(Context context, int userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.internal.app.EyeTemperatureController.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean selfChange, Uri uri) {
                        super.onChange(selfChange, uri);
                        String setting = uri == null ? null : uri.getLastPathSegment();
                        if (setting != null) {
                            EyeTemperatureController.this.onSettingChanged(setting);
                        }
                    }
                };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        Settings.Secure.EYE_TEMP_DISPLAY_ACTIVATED,
                        0,
                        this.mUserId)
                == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setEyeTempLevel(getDefaultEyeTempLevel());
        }
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                Settings.Secure.EYE_TEMP_DISPLAY_ACTIVATED,
                z ? 1 : 0,
                this.mUserId);
    }

    public int getEyeTempLevel() {
        int level =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        Settings.Secure.EYE_TEMP_DISPLAY_TEMP_LEVEL,
                        -1,
                        this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: eye_temp_display_temp_level");
            return getDefaultEyeTempLevel();
        }
        return level;
    }

    public boolean setEyeTempLevel(int level) {
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                Settings.Secure.EYE_TEMP_DISPLAY_TEMP_LEVEL,
                level,
                this.mUserId);
    }

    public int getMinimumEyeTempLevel() {
        return 0;
    }

    public int getMaximumEyeTempLevel() {
        return 10;
    }

    public int getDefaultEyeTempLevel() {
        return 7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onSettingChanged(String setting) {
        char c;
        Slog.d(TAG, "onSettingChanged: " + setting);
        if (this.mCallback != null) {
            switch (setting.hashCode()) {
                case 672373043:
                    if (setting.equals(Settings.Secure.EYE_TEMP_DISPLAY_TEMP_LEVEL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1996824759:
                    if (setting.equals(Settings.Secure.EYE_TEMP_DISPLAY_ACTIVATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mCallback.onActivated(isActivated());
                    break;
                case 1:
                    this.mCallback.onLevelChanged(getEyeTempLevel());
                    break;
            }
        }
    }

    public void setListener(Callback callback) {
        Callback oldCallback = this.mCallback;
        if (oldCallback != callback) {
            this.mCallback = callback;
            if (callback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            } else if (oldCallback == null) {
                ContentResolver cr = this.mContext.getContentResolver();
                cr.registerContentObserver(
                        Settings.Secure.getUriFor(Settings.Secure.EYE_TEMP_DISPLAY_ACTIVATED),
                        false,
                        this.mContentObserver,
                        this.mUserId);
                cr.registerContentObserver(
                        Settings.Secure.getUriFor(Settings.Secure.EYE_TEMP_DISPLAY_TEMP_LEVEL),
                        false,
                        this.mContentObserver,
                        this.mUserId);
            }
        }
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    public interface Callback {
        default void onActivated(boolean activated) {}

        default void onLevelChanged(int level) {}
    }
}
