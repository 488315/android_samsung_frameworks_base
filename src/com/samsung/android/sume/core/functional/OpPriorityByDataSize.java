package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;

/* loaded from: classes6.dex */
public class OpPriorityByDataSize implements OpPriorityCompute {
    @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
    public float compute(Shape shape, Shape shape2) {
        return shape2.getDimension() / shape.getDimension();
    }

    @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
    public float compute(ColorFormat colorFormat, ColorFormat colorFormat2) {
        return colorFormat2.bytePerPixel() / colorFormat.bytePerPixel();
    }

    @Override // com.samsung.android.sume.core.functional.OpPriorityCompute
    public float compute(DataType dataType, DataType dataType2) {
        return dataType2.size() / dataType.size();
    }
}
