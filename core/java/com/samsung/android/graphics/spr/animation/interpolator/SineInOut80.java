package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class SineInOut80 implements Interpolator {
  private static final float[][] segments = {
    new float[] {0.0f, 0.0f, 0.195f},
    new float[] {0.195f, 0.48f, 0.645f},
    new float[] {0.645f, 0.835f, 0.885f},
    new float[] {0.885f, 0.955f, 0.978f},
    new float[] {0.978f, 0.9999f, 1.0f}
  };

  public SineInOut80() {}

  public SineInOut80(Context context, AttributeSet attrs) {}

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float input) {
    float _loc_5 = input / 1.0f;
    float[][] fArr = segments;
    int _loc_6 = fArr.length;
    int _loc_9 = (int) Math.floor(_loc_6 * _loc_5);
    if (_loc_9 >= fArr.length) {
      _loc_9 = fArr.length - 1;
    }
    float _loc_7 = (_loc_5 - (_loc_9 * (1.0f / _loc_6))) * _loc_6;
    float[] _loc_8 = fArr[_loc_9];
    float ret =
        ((_loc_8[0]
                    + ((((1.0f - _loc_7) * 2.0f * (_loc_8[1] - _loc_8[0]))
                            + ((_loc_8[2] - _loc_8[0]) * _loc_7))
                        * _loc_7))
                * 1.0f)
            + 0.0f;
    return ret;
  }
}
