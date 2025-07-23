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

/* loaded from: classes5.dex */
public final class RgbGainAPIController {
    private static final boolean DEBUG = true;
    private static final String TAG = "RgbGainController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public interface Callback {
        default void onActivated(boolean z) {
        }

        default void onRgbGainBlueLevelChanged(int i) {
        }

        default void onRgbGainGreenLevelChanged(int i) {
        }

        default void onRgbGainRedLevelChanged(int i) {
        }
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    public int getDefaultRgbGainLevel() {
        return 32768;
    }

    public int getMaximumRgbGainLevel() {
        return 65536;
    }

    public int getMinimumRgbGainLevel() {
        return 0;
    }

    public RgbGainAPIController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public RgbGainAPIController(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mUserId = i;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.RgbGainAPIController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                if (lastPathSegment != null) {
                    RgbGainAPIController.this.onSettingChanged(lastPathSegment);
                }
            }
        };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setRgbGainRedLevel(getDefaultRgbGainLevel());
            setRgbGainGreenLevel(getDefaultRgbGainLevel());
            setRgbGainBlueLevel(getDefaultRgbGainLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getRgbGainRedLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_red_level");
        return getDefaultRgbGainLevel();
    }

    public int getRgbGainGreenLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_green_level");
        return getDefaultRgbGainLevel();
    }

    public int getRgbGainBlueLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_blue_level");
        return getDefaultRgbGainLevel();
    }

    public boolean setRgbGainRedLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL, i, this.mUserId);
    }

    public boolean setRgbGainGreenLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL, i, this.mUserId);
    }

    public boolean setRgbGainBlueLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL, i, this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingChanged(String str) {
        Slog.d(TAG, "onSettingChanged: " + str);
        if (this.mCallback != null) {
            str.hashCode();
            switch (str) {
                case "rgb_gain_api_display_blue_level":
                    this.mCallback.onRgbGainBlueLevelChanged(getRgbGainBlueLevel());
                    break;
                case "rgb_gain_api_display_activated":
                    this.mCallback.onActivated(isActivated());
                    break;
                case "rgb_gain_api_display_red_level":
                    this.mCallback.onRgbGainRedLevelChanged(getRgbGainRedLevel());
                    break;
                case "rgb_gain_api_display_green_level":
                    this.mCallback.onRgbGainGreenLevelChanged(getRgbGainGreenLevel());
                    break;
            }
        }
    }

    public void setListener(Callback callback) {
        Callback callback2 = this.mCallback;
        if (callback2 != callback) {
            this.mCallback = callback;
            if (callback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
                return;
            }
            if (callback2 == null) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL), false, this.mContentObserver, this.mUserId);
            }
        }
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }
}
