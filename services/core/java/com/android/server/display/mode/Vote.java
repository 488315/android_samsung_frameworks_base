package com.android.server.display.mode;

import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes2.dex */
public final class Vote {
  public final float appRequestBaseModeRefreshRate;
  public final boolean disableRefreshRateSwitching;
  public final int height;
  public final SurfaceControl.RefreshRateRanges refreshRateRanges;
  public final int width;

  public static Vote forPhysicalRefreshRates(float f, float f2) {
    return new Vote(
        -1,
        -1,
        f,
        f2,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        f == f2,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static Vote forRenderFrameRates(float f, float f2) {
    return new Vote(
        -1,
        -1,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        f,
        f2,
        false,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static Vote forSize(int i, int i2) {
    return new Vote(
        i,
        i2,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        false,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static Vote forDisableRefreshRateSwitching() {
    return new Vote(
        -1,
        -1,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        true,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public static Vote forBaseModeRefreshRate(float f) {
    return new Vote(
        -1,
        -1,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        Float.POSITIVE_INFINITY,
        false,
        f);
  }

  public Vote(int i, int i2, float f, float f2, float f3, float f4, boolean z, float f5) {
    this.width = i;
    this.height = i2;
    this.refreshRateRanges =
        new SurfaceControl.RefreshRateRanges(
            new SurfaceControl.RefreshRateRange(f, f2),
            new SurfaceControl.RefreshRateRange(f3, f4));
    this.disableRefreshRateSwitching = z;
    this.appRequestBaseModeRefreshRate = f5;
  }

  public static String priorityToString(int i) {
    switch (i) {
      case 0:
        return "PRIORITY_DEFAULT_REFRESH_RATE";
      case 1:
        return "PRIORITY_REFRESH_RATE_MAX_LIMIT";
      case 2:
        return "PRIORITY_REFRESH_RATE_MIN_LIMIT";
      case 3:
        return "PRIORITY_FLICKER_REFRESH_RATE";
      case 4:
        return "PRIORITY_HIGH_BRIGHTNESS_MODE";
      case 5:
        return "PRIORITY_USER_SETTING_MIN_RENDER_FRAME_RATE";
      case 6:
        return "PRIORITY_APP_REQUEST_RENDER_FRAME_RATE_RANGE";
      case 7:
        return "PRIORITY_APP_REQUEST_BASE_MODE_REFRESH_RATE";
      case 8:
        return "PRIORITY_APP_REQUEST_SIZE";
      case 9:
        return "PRIORITY_USER_SETTING_PEAK_RENDER_FRAME_RATE";
      case 10:
        return "PRIORITY_REFRESH_RATE_MODE";
      case 11:
        return "PRIORITY_RESOLUTION";
      case 12:
        return "PRIORITY_AUTH_OPTIMIZER_RENDER_FRAME_RATE";
      case 13:
        return "PRIORITY_LAYOUT_LIMITED_FRAME_RATE";
      case 14:
        return "PRIORITY_LOW_POWER_MODE";
      case 15:
        return "PRIORITY_FLICKER_REFRESH_RATE_SWITCH";
      case 16:
        return "PRIORITY_SKIN_TEMPERATURE";
      case 17:
        return "PRIORITY_PROXIMITY";
      case 18:
        return "PRIORITY_UDFPS";
      default:
        return Integer.toString(i);
    }
  }

  public String toString() {
    return "Vote: {width: "
        + this.width
        + ", height: "
        + this.height
        + ", refreshRateRanges: "
        + this.refreshRateRanges
        + ", disableRefreshRateSwitching: "
        + this.disableRefreshRateSwitching
        + ", appRequestBaseModeRefreshRate: "
        + this.appRequestBaseModeRefreshRate
        + "}";
  }
}
