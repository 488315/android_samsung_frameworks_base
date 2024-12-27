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

    public HsvGainController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public HsvGainController(Context context, int userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.HsvGainController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                String setting = uri == null ? null : uri.getLastPathSegment();
                if (setting != null) {
                    HsvGainController.this.onSettingChanged(setting);
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
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: hsv_gain_display_hue_level");
            return getDefaultHsvGainLevel();
        }
        return level;
    }

    public int getHsvGainSatLevel() {
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: hsv_gain_display_sat_level");
            return getDefaultHsvGainLevel();
        }
        return level;
    }

    public int getHsvGainValLevel() {
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: hsv_gain_display_val_level");
            return getDefaultHsvGainLevel();
        }
        return level;
    }

    public boolean setHsvGainHueLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL, level, this.mUserId);
    }

    public boolean setHsvGainSatLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL, level, this.mUserId);
    }

    public boolean setHsvGainValLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL, level, this.mUserId);
    }

    public int getMinimumHsvGainLevel() {
        return 0;
    }

    public int getMaximumHsvGainLevel() {
        return 254;
    }

    public int getDefaultHsvGainLevel() {
        return 127;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onSettingChanged(String setting) {
        char c;
        Slog.d(TAG, "onSettingChanged: " + setting);
        if (this.mCallback != null) {
            switch (setting.hashCode()) {
                case -562078776:
                    if (setting.equals(Settings.Secure.HSV_GAIN_DISPLAY_ACTIVATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 253397876:
                    if (setting.equals(Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1777420258:
                    if (setting.equals(Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2139929309:
                    if (setting.equals(Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL)) {
                        c = 3;
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
                    this.mCallback.onHsvGainHueLevelChanged(getHsvGainHueLevel());
                    break;
                case 2:
                    this.mCallback.onHsvGainSatLevelChanged(getHsvGainSatLevel());
                    break;
                case 3:
                    this.mCallback.onHsvGainValLevelChanged(getHsvGainValLevel());
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
                return;
            }
            if (oldCallback == null) {
                ContentResolver cr = this.mContext.getContentResolver();
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_HUE_LEVEL), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_SAT_LEVEL), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.HSV_GAIN_DISPLAY_VAL_LEVEL), false, this.mContentObserver, this.mUserId);
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
        default void onActivated(boolean activated) {
        }

        default void onHsvGainHueLevelChanged(int level) {
        }

        default void onHsvGainSatLevelChanged(int level) {
        }

        default void onHsvGainValLevelChanged(int level) {
        }
    }
}
