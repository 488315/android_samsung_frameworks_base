package com.samsung.sesl.compose.foundation.shape;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class RoundedCornerShapeKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        CornerSize CornerSize = CornerSizeKt.CornerSize(50);
        new SeslRoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize, false);
    }

    /* renamed from: SeslRoundedCornerShape-D5KLDUw$default, reason: not valid java name */
    public static SeslRoundedCornerShape m3336SeslRoundedCornerShapeD5KLDUw$default(float f) {
        CornerSize m185CornerSize0680j_4 = CornerSizeKt.m185CornerSize0680j_4(f);
        return new SeslRoundedCornerShape(m185CornerSize0680j_4, m185CornerSize0680j_4, m185CornerSize0680j_4, m185CornerSize0680j_4, false);
    }
}
