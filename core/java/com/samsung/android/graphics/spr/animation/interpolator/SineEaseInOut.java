package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class SineEaseInOut implements Interpolator {
  public SineEaseInOut() {}

  public SineEaseInOut(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return inout(t);
  }

  private float inout(float t) {
    return (float) ((Math.cos(t * 3.141592653589793d) - 1.0d) * (-0.5d));
  }
}
