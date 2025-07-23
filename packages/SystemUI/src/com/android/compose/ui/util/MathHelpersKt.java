package com.android.compose.ui.util;

import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Scale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MathHelpersKt {
    public static final Scale lerp(Scale scale, Scale scale2, float f) {
        long j = scale.pivot;
        if ((j & 9223372034707292159L) != 9205357640488583168L) {
            long j2 = scale2.pivot;
            if ((j2 & 9223372034707292159L) != 9205357640488583168L) {
                j = OffsetKt.m404lerpWko1d7g(j, j2, f);
                return new Scale(androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleX, scale2.scaleX, f), androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleY, scale2.scaleY, f), j, null);
            }
        }
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            j = scale2.pivot;
        }
        return new Scale(androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleX, scale2.scaleX, f), androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleY, scale2.scaleY, f), j, null);
    }

    /* renamed from: lerp-e0twbBA, reason: not valid java name */
    public static final long m940lerpe0twbBA(long j, long j2, float f) {
        long lerp = (androidx.compose.ui.util.MathHelpersKt.lerp(f, (int) (j >> 32), (int) (j2 >> 32)) << 32) | (androidx.compose.ui.util.MathHelpersKt.lerp(f, (int) (j & 4294967295L), (int) (j2 & 4294967295L)) & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return lerp;
    }
}
