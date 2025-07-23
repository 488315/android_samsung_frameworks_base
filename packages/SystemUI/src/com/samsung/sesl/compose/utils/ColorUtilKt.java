package com.samsung.sesl.compose.utils;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ColorUtilKt {
    /* renamed from: lerp-IbeAmgk, reason: not valid java name */
    public static final long m3341lerpIbeAmgk(long j, long j2, float f) {
        return ColorKt.Color$default(MathHelperKt.lerp(Color.m461getRedimpl(j), Color.m461getRedimpl(j2), f), MathHelperKt.lerp(Color.m460getGreenimpl(j), Color.m460getGreenimpl(j2), f), MathHelperKt.lerp(Color.m458getBlueimpl(j), Color.m458getBlueimpl(j2), f), 24);
    }
}
