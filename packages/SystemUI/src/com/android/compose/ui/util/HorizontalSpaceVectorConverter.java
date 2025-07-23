package com.android.compose.ui.util;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.Velocity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HorizontalSpaceVectorConverter implements SpaceVectorConverter {
    public static final HorizontalSpaceVectorConverter INSTANCE = new HorizontalSpaceVectorConverter();

    private HorizontalSpaceVectorConverter() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof HorizontalSpaceVectorConverter);
    }

    public final int hashCode() {
        return 844560757;
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-TH1AsA0$1 */
    public final float mo914toFloatTH1AsA0$1(long j) {
        return Velocity.m878getXimpl(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-k-4lQ0M$1 */
    public final float mo915toFloatk4lQ0M$1(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toIntOffset-Bjo55l4 */
    public final long mo916toIntOffsetBjo55l4(int i) {
        long j = (i << 32) | (0 & 4294967295L);
        IntOffset.Companion companion = IntOffset.Companion;
        return j;
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toOffset-tuRUvjQ$1 */
    public final long mo917toOffsettuRUvjQ$1(float f) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    public final String toString() {
        return "HorizontalSpaceVectorConverter";
    }
}
