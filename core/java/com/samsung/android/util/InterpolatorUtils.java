package com.samsung.android.util;

import android.animation.TimeInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes5.dex */
public class InterpolatorUtils {
  public static final PathInterpolator ONE_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
  public static final PathInterpolator SINE_OUT_33 =
      new PathInterpolator(0.17f, 0.09f, 0.67f, 1.0f);
  public static final PathInterpolator SINE_OUT_60 = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
  public static final PathInterpolator SINE_OUT_70 = new PathInterpolator(0.17f, 0.17f, 0.3f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_10 =
      new PathInterpolator(0.33f, 0.0f, 0.9f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_33 =
      new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_60 =
      new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_70 =
      new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_80 =
      new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
  public static final PathInterpolator SINE_IN_OUT_90 =
      new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
  public static final TimeInterpolator LINEAR = new LinearInterpolator();
}
