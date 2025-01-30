package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class QuintEaseIn implements Interpolator {
  public QuintEaseIn() {}

  public QuintEaseIn(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m276in(t);
  }

  /* renamed from: in */
  private float m276in(float t) {
    return t * t * t * t * t;
  }
}
