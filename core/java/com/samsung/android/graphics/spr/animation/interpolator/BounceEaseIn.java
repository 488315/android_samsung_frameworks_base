package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class BounceEaseIn implements Interpolator {
  public BounceEaseIn() {}

  public BounceEaseIn(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return m268in(t);
  }

  private float out(float t) {
    if (t < 0.36363636363636365d) {
      return 7.5625f * t * t;
    }
    if (t < 0.7272727272727273d) {
      float t2 = (float) (t - 0.5454545454545454d);
      return (t2 * 7.5625f * t2) + 0.75f;
    }
    if (t < 0.9090909090909091d) {
      float t3 = (float) (t - 0.8181818181818182d);
      return (t3 * 7.5625f * t3) + 0.9375f;
    }
    float t4 = (float) (t - 0.9545454545454546d);
    return (t4 * 7.5625f * t4) + 0.984375f;
  }

  /* renamed from: in */
  private float m268in(float t) {
    return 1.0f - out(1.0f - t);
  }
}
