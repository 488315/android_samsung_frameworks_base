package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class SineEaseIn implements Interpolator {
  public SineEaseIn() {}

  public SineEaseIn(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m277in(t);
  }

  /* renamed from: in */
  private float m277in(float t) {
    return (float) ((-Math.cos(t * 1.5707963267948966d)) + 1.0d);
  }
}
