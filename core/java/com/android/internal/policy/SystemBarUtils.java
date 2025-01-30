package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.util.RotationUtils;
import android.view.DisplayCutout;
import com.android.internal.C4337R;

/* loaded from: classes5.dex */
public final class SystemBarUtils {
  private static boolean STATUS_LAYOUT = true;
  private static int ROTATION_LANDSCAPE = 1;
  private static int ROTATION_SEASCAPE = 3;

  public static int getStatusBarHeight(Context context) {
    return getStatusBarHeight(context.getResources(), context.getDisplay().getCutout());
  }

  public static int getStatusBarHeight(Resources resources, DisplayCutout displayCutout) {
    if (STATUS_LAYOUT) {
      return getStatusBarHeight(
          resources, displayCutout, resources.getConfiguration().orientation == 2);
    }
    return Math.max(
        displayCutout == null ? 0 : displayCutout.getSafeInsetTop(),
        resources.getDimensionPixelSize(C4337R.dimen.status_bar_height_default)
            + (displayCutout != null ? displayCutout.getWaterfallInsets().top : 0));
  }

  public static int getStatusBarHeightForRotation(Context context, int targetRot) {
    int rotation = context.getDisplay().getRotation();
    DisplayCutout cutout = context.getDisplay().getCutout();
    if (STATUS_LAYOUT) {
      return getStatusBarHeight(
          context.getResources(),
          cutout,
          targetRot == ROTATION_LANDSCAPE || targetRot == ROTATION_SEASCAPE);
    }
    Insets insets = cutout == null ? Insets.NONE : Insets.m15of(cutout.getSafeInsets());
    Insets waterfallInsets = cutout == null ? Insets.NONE : cutout.getWaterfallInsets();
    if (rotation != targetRot) {
      if (!insets.equals(Insets.NONE)) {
        insets =
            RotationUtils.rotateInsets(insets, RotationUtils.deltaRotation(rotation, targetRot));
      }
      if (!waterfallInsets.equals(Insets.NONE)) {
        waterfallInsets =
            RotationUtils.rotateInsets(
                waterfallInsets, RotationUtils.deltaRotation(rotation, targetRot));
      }
    }
    int defaultSize =
        context.getResources().getDimensionPixelSize(C4337R.dimen.status_bar_height_default);
    return Math.max(insets.top, waterfallInsets.top + defaultSize);
  }

  private static int getStatusBarHeight(Resources res, DisplayCutout cutout, boolean isLandscape) {
    if (isLandscape) {
      return res.getDimensionPixelSize(C4337R.dimen.status_bar_height_landscape);
    }
    int safeInsetTop = cutout == null ? 0 : cutout.getSafeInsetTop();
    return Math.max(
        safeInsetTop, res.getDimensionPixelSize(C4337R.dimen.status_bar_height_portrait));
  }

  public static int getQuickQsOffsetHeight(Context context) {
    int defaultSize =
        context.getResources().getDimensionPixelSize(C4337R.dimen.quick_qs_offset_height);
    int statusBarHeight = getStatusBarHeight(context);
    return Math.max(defaultSize, statusBarHeight);
  }
}
