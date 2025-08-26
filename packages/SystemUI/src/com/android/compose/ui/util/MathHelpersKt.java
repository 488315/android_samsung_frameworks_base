package com.android.compose.ui.util;

import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Scale;

/* loaded from: classes.dex */
public abstract class MathHelpersKt {
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Scale lerp(Scale scale, Scale scale2, float f) {
        long jM406lerpWko1d7g = scale.pivot;
        if ((jM406lerpWko1d7g & 9223372034707292159L) != 9205357640488583168L) {
            long j = scale2.pivot;
            if ((j & 9223372034707292159L) != 9205357640488583168L) {
                jM406lerpWko1d7g = OffsetKt.m406lerpWko1d7g(jM406lerpWko1d7g, j, f);
            } else if ((9223372034707292159L & jM406lerpWko1d7g) == 9205357640488583168L) {
                jM406lerpWko1d7g = scale2.pivot;
            }
        }
        return new Scale(androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleX, scale2.scaleX, f), androidx.compose.ui.util.MathHelpersKt.lerp(scale.scaleY, scale2.scaleY, f), jM406lerpWko1d7g, null);
    }

    /* renamed from: lerp-e0twbBA, reason: not valid java name */
    public static final long m942lerpe0twbBA(long j, long j2, float f) {
        long jLerp = (androidx.compose.ui.util.MathHelpersKt.lerp(f, (int) (j >> 32), (int) (j2 >> 32)) << 32) | (androidx.compose.ui.util.MathHelpersKt.lerp(f, (int) (j & 4294967295L), (int) (j2 & 4294967295L)) & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return jLerp;
    }
}
