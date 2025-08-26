package com.samsung.sesl.compose.foundation.shape;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;

/* loaded from: classes4.dex */
public abstract class RoundedCornerShapeKt {
    public static final SeslRoundedCornerShape SeslCircleShape;

    static {
        CornerSize CornerSize = CornerSizeKt.CornerSize(50);
        SeslCircleShape = new SeslRoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize, false);
    }

    /* renamed from: SeslRoundedCornerShape-D5KLDUw$default, reason: not valid java name */
    public static SeslRoundedCornerShape m3354SeslRoundedCornerShapeD5KLDUw$default(float f) {
        CornerSize cornerSizeM186CornerSize0680j_4 = CornerSizeKt.m186CornerSize0680j_4(f);
        return new SeslRoundedCornerShape(cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, false);
    }
}
