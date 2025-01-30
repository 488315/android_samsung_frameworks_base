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
public final class RgbGainController {
  private static final boolean DEBUG = true;
  private static final String TAG = "RgbGainController";
  private Callback mCallback;
  private final ContentObserver mContentObserver;
  private final Context mContext;
  private MetricsLogger mMetricsLogger;
  private final int mUserId;

  public RgbGainController(Context context) {
    this(context, ActivityManager.getCurrentUser());
  }

  public RgbGainController(Context context, int userId) {
    this.mContext = context.getApplicationContext();
    this.mUserId = userId;
    this.mContentObserver =
        new ContentObserver(
            new Handler(
                Looper
                    .getMainLooper())) { // from class: com.android.internal.app.RgbGainController.1
          @Override // android.database.ContentObserver
          public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            String setting = uri == null ? null : uri.getLastPathSegment();
            if (setting != null) {
              RgbGainController.this.onSettingChanged(setting);
            }
          }
        };
  }

  public boolean isActivated() {
    return Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.RGB_GAIN_DISPLAY_ACTIVATED,
            0,
            this.mUserId)
        == 1;
  }

  public boolean setActivated(boolean z) {
    if (!z) {
      setRgbGainRedLevel(getDefaultRgbGainLevel());
      setRgbGainGreenLevel(getDefaultRgbGainLevel());
      setRgbGainBlueLevel(getDefaultRgbGainLevel());
    }
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.RGB_GAIN_DISPLAY_ACTIVATED,
        z ? 1 : 0,
        this.mUserId);
  }

  public int getRgbGainRedLevel() {
    int level =
        Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.RGB_GAIN_DISPLAY_RED_LEVEL,
            -1,
            this.mUserId);
    if (level == -1) {
      Slog.m113d(TAG, "Using default value for setting: rgb_gain_display_red_level");
      return getDefaultRgbGainLevel();
    }
    return level;
  }

  public int getRgbGainGreenLevel() {
    int level =
        Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.RGB_GAIN_DISPLAY_GREEN_LEVEL,
            -1,
            this.mUserId);
    if (level == -1) {
      Slog.m113d(TAG, "Using default value for setting: rgb_gain_display_green_level");
      return getDefaultRgbGainLevel();
    }
    return level;
  }

  public int getRgbGainBlueLevel() {
    int level =
        Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(),
            Settings.Secure.RGB_GAIN_DISPLAY_BLUE_LEVEL,
            -1,
            this.mUserId);
    if (level == -1) {
      Slog.m113d(TAG, "Using default value for setting: rgb_gain_display_blue_level");
      return getDefaultRgbGainLevel();
    }
    return level;
  }

  public boolean setRgbGainRedLevel(int level) {
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.RGB_GAIN_DISPLAY_RED_LEVEL,
        level,
        this.mUserId);
  }

  public boolean setRgbGainGreenLevel(int level) {
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.RGB_GAIN_DISPLAY_GREEN_LEVEL,
        level,
        this.mUserId);
  }

  public boolean setRgbGainBlueLevel(int level) {
    return Settings.Secure.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.Secure.RGB_GAIN_DISPLAY_BLUE_LEVEL,
        level,
        this.mUserId);
  }

  public int getMinimumRgbGainLevel() {
    return 0;
  }

  public int getMaximumRgbGainLevel() {
    return 255;
  }

  public int getDefaultRgbGainLevel() {
    return 127;
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public void onSettingChanged(String setting) {
    char c;
    Slog.m113d(TAG, "onSettingChanged: " + setting);
    if (this.mCallback != null) {
      switch (setting.hashCode()) {
        case -1567542851:
          if (setting.equals(Settings.Secure.RGB_GAIN_DISPLAY_GREEN_LEVEL)) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 244962694:
          if (setting.equals(Settings.Secure.RGB_GAIN_DISPLAY_ACTIVATED)) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 1458065931:
          if (setting.equals(Settings.Secure.RGB_GAIN_DISPLAY_RED_LEVEL)) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 1559195210:
          if (setting.equals(Settings.Secure.RGB_GAIN_DISPLAY_BLUE_LEVEL)) {
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
          this.mCallback.onRgbGainRedLevelChanged(getRgbGainRedLevel());
          break;
        case 2:
          this.mCallback.onRgbGainGreenLevelChanged(getRgbGainGreenLevel());
          break;
        case 3:
          this.mCallback.onRgbGainBlueLevelChanged(getRgbGainBlueLevel());
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
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_DISPLAY_ACTIVATED),
            false,
            this.mContentObserver,
            this.mUserId);
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_DISPLAY_RED_LEVEL),
            false,
            this.mContentObserver,
            this.mUserId);
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_DISPLAY_GREEN_LEVEL),
            false,
            this.mContentObserver,
            this.mUserId);
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_DISPLAY_BLUE_LEVEL),
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

    default void onRgbGainRedLevelChanged(int level) {}

    default void onRgbGainGreenLevelChanged(int level) {}

    default void onRgbGainBlueLevelChanged(int level) {}
  }
}
