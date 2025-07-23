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
import com.sec.android.allshare.iface.message.EventMsg;

/* loaded from: classes5.dex */
public final class ColorTemperatureController {
    private static final boolean DEBUG = true;
    private static final String TAG = "ColorTemperatureController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public interface Callback {
        default void onActivated(boolean z) {
        }

        default void onColorTempLevelChanged(int i) {
        }
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    public int getDefaultColorTempLevel() {
        return EventMsg.UEVENT_UNZIP_PROFILE;
    }

    public int getMaximumColorTempLevel() {
        return 9000;
    }

    public int getMinimumColorTempLevel() {
        return 4000;
    }

    public ColorTemperatureController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public ColorTemperatureController(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mUserId = i;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.ColorTemperatureController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                if (lastPathSegment != null) {
                    ColorTemperatureController.this.onSettingChanged(lastPathSegment);
                }
            }
        };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setColorTempLevel(getDefaultColorTempLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getColorTempLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: color_temp_display_temp_level");
        return getDefaultColorTempLevel();
    }

    public boolean setColorTempLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL, i, this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingChanged(String str) {
        Slog.d(TAG, "onSettingChanged: " + str);
        if (this.mCallback != null) {
            str.hashCode();
            if (str.equals(Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL)) {
                this.mCallback.onColorTempLevelChanged(getColorTempLevel());
            } else if (str.equals(Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED)) {
                this.mCallback.onActivated(isActivated());
            }
        }
    }

    public void setListener(Callback callback) {
        Callback callback2 = this.mCallback;
        if (callback2 != callback) {
            this.mCallback = callback;
            if (callback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            } else if (callback2 == null) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL), false, this.mContentObserver, this.mUserId);
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
