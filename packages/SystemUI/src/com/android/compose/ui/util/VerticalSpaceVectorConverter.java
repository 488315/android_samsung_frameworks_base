package com.android.compose.ui.util;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.Velocity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VerticalSpaceVectorConverter implements SpaceVectorConverter {
    public static final VerticalSpaceVectorConverter INSTANCE = new VerticalSpaceVectorConverter();

    private VerticalSpaceVectorConverter() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof VerticalSpaceVectorConverter);
    }

    public final int hashCode() {
        return 1171000903;
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-TH1AsA0$1 */
    public final float mo914toFloatTH1AsA0$1(long j) {
        return Velocity.m879getYimpl(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-k-4lQ0M$1 */
    public final float mo915toFloatk4lQ0M$1(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toIntOffset-Bjo55l4 */
    public final long mo916toIntOffsetBjo55l4(int i) {
        long j = (i & 4294967295L) | (0 << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return j;
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toOffset-tuRUvjQ$1 */
    public final long mo917toOffsettuRUvjQ$1(float f) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    public final String toString() {
        return "VerticalSpaceVectorConverter";
    }
}
