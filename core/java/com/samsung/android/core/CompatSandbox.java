package com.samsung.android.core;

import android.content.res.Configuration;
import android.graphics.Rect;

/* loaded from: classes5.dex */
public class CompatSandbox {
  public static final float OFFSET_DEFAULT = 0.0f;
  public static final int SANDBOX_DISABLED = 1;
  public static final int SANDBOX_DISPLAY = 2;
  public static final int SANDBOX_INSETS_HINT = 64;
  public static final int SANDBOX_IS_BOUNDS_COMPAT_MODE_ENABLED = 32;
  public static final int SANDBOX_MOCK_FULL_SCREEN = 16;
  public static final int SANDBOX_MOTION_EVENT = 8;
  public static final int SANDBOX_UNDEFINED = 0;
  public static final int SANDBOX_VIEW_BOUNDS = 4;
  public static final float SCALE_DEFAULT = 1.0f;
  public static final float SCALE_UNDEFINED = -1.0f;

  private static class LazyHolder {
    private static final Rect EMPTY_RECT = new Rect();

    private LazyHolder() {}
  }

  public static Rect getEmptyRect() {
    return LazyHolder.EMPTY_RECT;
  }

  private static boolean hasCompatSandboxFlags(Configuration config, int mask) {
    return (config == null || (config.windowConfiguration.getCompatSandboxFlags() & mask) == 0)
        ? false
        : true;
  }

  public static boolean isDisplaySandboxingEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 2);
  }

  public static boolean isViewBoundsSandboxingEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 4);
  }

  public static boolean isMotionEventSandboxingEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 8);
  }

  public static boolean isInsetsHintSandboxingEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 64);
  }

  public static boolean isMockFullScreenSandboxingEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 16);
  }

  public static boolean isBoundsCompatModeEnabled(Configuration config) {
    return hasCompatSandboxFlags(config, 32);
  }

  public static int getCompatWindowingMode(Configuration config, int defaultValue) {
    if (isMockFullScreenSandboxingEnabled(config)) {
      return 1;
    }
    return defaultValue;
  }
}
