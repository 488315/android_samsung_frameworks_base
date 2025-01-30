package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class QuartEaseIn implements Interpolator {
  public QuartEaseIn() {}

  public QuartEaseIn(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m275in(t);
  }

  /* renamed from: in */
  private float m275in(float t) {
    return t * t * t * t;
  }
}
