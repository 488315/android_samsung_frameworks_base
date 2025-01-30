package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class QuadEaseIn implements Interpolator {
  public QuadEaseIn() {}

  public QuadEaseIn(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m274in(t);
  }

  /* renamed from: in */
  private float m274in(float t) {
    return t * t;
  }
}
