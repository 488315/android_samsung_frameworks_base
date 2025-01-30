package com.android.server.display.utils;

import android.R;
import android.content.res.Resources;
import android.util.TypedValue;

/* loaded from: classes2.dex */
public abstract class AmbientFilterFactory {
  public static AmbientFilter createAmbientFilter(String str, int i, float f) {
    if (!Float.isNaN(f)) {
      return new AmbientFilter.WeightedMovingAverageAmbientFilter(str, i, f);
    }
    throw new IllegalArgumentException(
        "missing configurations: expected config_displayWhiteBalanceBrightnessFilterIntercept");
  }

  public static AmbientFilter createBrightnessFilter(String str, Resources resources) {
    return createAmbientFilter(
        str,
        resources.getInteger(R.integer.config_dozeWakeLockScreenDebounce),
        getFloat(resources, R.dimen.config_appTransitionAnimationDurationScaleDefault));
  }

  public static float getFloat(Resources resources, int i) {
    TypedValue typedValue = new TypedValue();
    resources.getValue(i, typedValue, true);
    if (typedValue.type != 4) {
      return Float.NaN;
    }
    return typedValue.getFloat();
  }
}
