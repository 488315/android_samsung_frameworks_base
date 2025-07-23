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
public final class HsvGainController {
    private static final boolean DEBUG = true;
    private static final String TAG = "HsvGainController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public interface Callback {
        default void onActivated(boolean z) {
        }

        default void onHsvGainHueLevelChanged(int i) {
        }

        default void onHsvGainSatLevelChanged(int i) {
        }

        default void onHsvGainValLevelChanged(int i) {
        }
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    public int getDefaultHsvGainLevel() {
        return 127;
    }

    public int getMaximumHsvGainLevel() {
        return 254;
    }

    public int getMinimumHsvGainLevel() {
        return 0;
    }

    public HsvGainController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public HsvGainController(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mUserId = i;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.HsvGainController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                if (lastPathSegment != null) {
                    HsvGainController.this.onSettingChanged(lastPathSegment);
                }
            }
        };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setHsvGainHueLevel(getDefaultHsvGainLevel());
            setHsvGainSatLevel(getDefaultHsvGainLevel());
            setHsvGainValLevel(getDefaultHsvGainLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getHsvGainHueLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: hsv_gain_display_hue_level");
        return getDefaultHsvGainLevel();
    }

    public int getHsvGainSatLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: hsv_gain_display_sat_level");
        return getDefaultHsvGainLevel();
    }

    public int getHsvGainValLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: hsv_gain_display_val_level");
        return getDefaultHsvGainLevel();
    }

    public boolean setHsvGainHueLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL, i, this.mUserId);
    }

    public boolean setHsvGainSatLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL, i, this.mUserId);
    }

    public boolean setHsvGainValLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL, i, this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingChanged(String str) {
        Slog.d(TAG, "onSettingChanged: " + str);
        if (this.mCallback != null) {
            str.hashCode();
            switch (str) {
                case "hsv_gain_display_activated":
                    this.mCallback.onActivated(isActivated());
                    break;
                case "hsv_gain_display_hue_level":
                    this.mCallback.onHsvGainHueLevelChanged(getHsvGainHueLevel());
                    break;
                case "hsv_gain_display_sat_level":
                    this.mCallback.onHsvGainSatLevelChanged(getHsvGainSatLevel());
                    break;
                case "hsv_gain_display_val_level":
                    this.mCallback.onHsvGainValLevelChanged(getHsvGainValLevel());
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
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL), false, this.mContentObserver, this.mUserId);
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
