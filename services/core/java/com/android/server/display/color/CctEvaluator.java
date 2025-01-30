package com.android.server.display.color;

import android.animation.TypeEvaluator;
import android.util.Slog;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class CctEvaluator implements TypeEvaluator {
  public final int mIndexOffset;
  final int[] mSteppedCctsAtOffsetCcts;
  final int[] mStepsAtOffsetCcts;

  public CctEvaluator(int i, int i2, int[] iArr, int[] iArr2) {
    int i3 = (i2 - i) + 1;
    this.mStepsAtOffsetCcts = new int[i3];
    this.mSteppedCctsAtOffsetCcts = new int[i3];
    this.mIndexOffset = i;
    int length = iArr.length;
    if (iArr.length != iArr2.length) {
      Slog.e(
          "CctEvaluator",
          "Parallel arrays cctRangeMinimums and steps are different lengths; setting step of 1");
      setStepOfOne();
      return;
    }
    if (length == 0) {
      Slog.e("CctEvaluator", "No cctRangeMinimums or steps are set; setting step of 1");
      setStepOfOne();
      return;
    }
    int i4 = Integer.MIN_VALUE;
    int i5 = 0;
    for (int i6 = 0; i6 < i3; i6++) {
      int i7 = this.mIndexOffset + i6;
      int i8 = i5 + 1;
      while (i8 < length && i7 >= iArr[i8]) {
        int i9 = i8;
        i8++;
        i5 = i9;
      }
      this.mStepsAtOffsetCcts[i6] = iArr2[i5];
      if (i4 == Integer.MIN_VALUE || Math.abs(i4 - i7) >= iArr2[i5]) {
        i4 = i7;
      }
      this.mSteppedCctsAtOffsetCcts[i6] = i4;
    }
  }

  @Override // android.animation.TypeEvaluator
  public Integer evaluate(float f, Integer num, Integer num2) {
    int intValue = (int) (num.intValue() + (f * (num2.intValue() - num.intValue())));
    int i = intValue - this.mIndexOffset;
    if (i >= 0) {
      int[] iArr = this.mSteppedCctsAtOffsetCcts;
      if (i < iArr.length) {
        return Integer.valueOf(iArr[i]);
      }
    }
    Slog.e("CctEvaluator", "steppedCctValueAt: returning same since invalid requested index=" + i);
    return Integer.valueOf(intValue);
  }

  public final void setStepOfOne() {
    Arrays.fill(this.mStepsAtOffsetCcts, 1);
    int i = 0;
    while (true) {
      int[] iArr = this.mSteppedCctsAtOffsetCcts;
      if (i >= iArr.length) {
        return;
      }
      iArr[i] = this.mIndexOffset + i;
      i++;
    }
  }
}
