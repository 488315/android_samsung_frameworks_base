package com.android.internal.app;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.Looper;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.logging.MetricsLogger;

/* loaded from: classes4.dex */
public final class WhitePointColorController {
  private static final boolean DEBUG = true;
  private static final String TAG = "WhitePointColorController";
  private Callback mCallback;
  private final ContentObserver mContentObserver;
  private final Context mContext;
  private MetricsLogger mMetricsLogger;
  private final int mUserId;

  public WhitePointColorController(Context context) {
    this(context, ActivityManager.getCurrentUser());
  }

  public WhitePointColorController(Context context, int userId) {
    this.mContext = context.getApplicationContext();
    this.mUserId = userId;
    this.mContentObserver =
        new ContentObserver(
            new Handler(
                Looper
                    .getMainLooper())) { // from class:
                                         // com.android.internal.app.WhitePointColorController.1
          @Override // android.database.ContentObserver
          public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            String setting = uri == null ? null : uri.getLastPathSegment();
            if (setting != null) {
              WhitePointColorController.this.onSettingChanged(setting);
            }
          }
        };
  }

  public boolean isActivated() {
    return Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED,
            0,
            this.mUserId)
        == 1;
  }

  public boolean setActivated(boolean z) {
    if (!z) {
      setWhitePointColorLevel(getDefaultWhitePointColorLevel());
    }
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED,
        z ? 1 : 0,
        this.mUserId);
  }

  public int getWhitePointColorLevel() {
    int colorLevel =
        Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL,
            -1,
            this.mUserId);
    if (colorLevel == -1) {
      Slog.m113d(TAG, "Using default value for setting: white_point_display_color_level");
      return getDefaultWhitePointColorLevel();
    }
    return colorLevel;
  }

  public boolean setWhitePointColorLevel(int level) {
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL,
        level,
        this.mUserId);
  }

  public int getMinimumWhitePointColorLevel() {
    return 0;
  }

  public int getMaximumWhitePointColorLevel() {
    return 255;
  }

  public int getDefaultWhitePointColorLevel() {
    return 127;
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public void onSettingChanged(String setting) {
    char c;
    Slog.m113d(TAG, "onSettingChanged: " + setting);
    if (this.mCallback != null) {
      switch (setting.hashCode()) {
        case -1594226202:
          if (setting.equals(Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL)) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 2141025167:
          if (setting.equals(Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED)) {
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
          this.mCallback.onLevelChanged(getWhitePointColorLevel());
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
            Settings.Secure.getUriFor(Settings.Secure.WHITE_POINT_DISPLAY_ACTIVATED),
            false,
            this.mContentObserver,
            this.mUserId);
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.WHITE_POINT_DISPLAY_COLOR_LEVEL),
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
