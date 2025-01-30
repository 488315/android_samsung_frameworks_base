package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;

/* loaded from: classes4.dex */
public class OpPriorityByDataSize implements OpPriorityCompute {
  @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
  public float compute(Shape from, Shape to) {
    return to.getDimension() / from.getDimension();
  }

  @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
  public float compute(ColorFormat from, ColorFormat to) {
    return to.bytePerPixel() / from.bytePerPixel();
  }

  @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
  public float compute(DataType from, DataType to) {
    return to.size() / from.size();
  }
}
