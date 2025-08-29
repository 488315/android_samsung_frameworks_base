package com.samsung.sesl.compose.utils;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* loaded from: classes4.dex */
public abstract class ColorUtilKt {
    /* renamed from: lerp-IbeAmgk, reason: not valid java name */
    public static final long m3358lerpIbeAmgk(long j, long j2, float f) {
        return ColorKt.Color$default(MathHelperKt.lerp(Color.m463getRedimpl(j), Color.m463getRedimpl(j2), f), MathHelperKt.lerp(Color.m462getGreenimpl(j), Color.m462getGreenimpl(j2), f), MathHelperKt.lerp(Color.m460getBlueimpl(j), Color.m460getBlueimpl(j2), f), 24);
    }
}
