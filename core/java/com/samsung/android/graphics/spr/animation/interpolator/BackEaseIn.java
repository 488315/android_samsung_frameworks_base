package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class BackEaseIn implements Interpolator {
  private float overshot;

  public BackEaseIn() {}

  public BackEaseIn(float overshot) {
    this.overshot = overshot;
  }

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m267in(t, this.overshot);
  }

  /* renamed from: in */
  private float m267in(float t, float o) {
    if (o == 0.0f) {
      o = 1.70158f;
    }
    return t * t * (((1.0f + o) * t) - o);
  }
}
