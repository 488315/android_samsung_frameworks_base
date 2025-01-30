package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class CubicEaseInOut implements Interpolator {
  public CubicEaseInOut() {}

  public CubicEaseInOut(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return inout(t);
  }

  private float inout(float t) {
    float t2 = t * 2.0f;
    if (t2 < 1.0f) {
      return 0.5f * t2 * t2 * t2;
    }
    float t3 = t2 - 2.0f;
    return ((t3 * t3 * t3) + 2.0f) * 0.5f;
  }
}
