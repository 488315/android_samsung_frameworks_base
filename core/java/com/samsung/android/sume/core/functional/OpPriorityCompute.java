package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;

/* loaded from: classes6.dex */
public interface OpPriorityCompute {
    public static final float FIRST_CLASS = Float.MIN_VALUE;
    public static final float FIRST_OF_ALL = 0.0f;
    public static final float LAST_CLASS = Float.MAX_VALUE;
    public static final float NOT_APPLICABLE = -1.0f;

    float compute(Shape shape, Shape shape2);

    float compute(ColorFormat colorFormat, ColorFormat colorFormat2);

    float compute(DataType dataType, DataType dataType2);
}
