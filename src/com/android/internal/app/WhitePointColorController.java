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
public final class WhitePointColorController {
    private static final boolean DEBUG = true;
    private static final String TAG = "WhitePointColorController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public interface Callback {
        default void onActivated(boolean z) {
        }

        default void onLevelChanged(int i) {
        }
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    public int getDefaultWhitePointColorLevel() {
        return 127;
    }

    public int getMaximumWhitePointColorLevel() {
        return 255;
    }

    public int getMinimumWhitePointColorLevel() {
        return 0;
    }

    public WhitePointColorController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public WhitePointColorController(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mUserId = i;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.WhitePointColorController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                if (lastPathSegment != null) {
                    WhitePointColorController.this.onSettingChanged(lastPathSegment);
                }
            }
        };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setWhitePointColorLevel(getDefaultWhitePointColorLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getWhitePointColorLevel() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL, -1, this.mUserId);
        if (intForUser != -1) {
            return intForUser;
        }
        Slog.d(TAG, "Using default value for setting: white_point_display_color_level");
        return getDefaultWhitePointColorLevel();
    }

    public boolean setWhitePointColorLevel(int i) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL, i, this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingChanged(String str) {
        Slog.d(TAG, "onSettingChanged: " + str);
        if (this.mCallback != null) {
            str.hashCode();
            if (str.equals(Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL)) {
                this.mCallback.onLevelChanged(getWhitePointColorLevel());
            } else if (str.equals(Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED)) {
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
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL), false, this.mContentObserver, this.mUserId);
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
