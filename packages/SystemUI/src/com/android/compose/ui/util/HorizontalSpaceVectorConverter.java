package com.android.compose.ui.util;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.Velocity;

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
    public final float mo916toFloatTH1AsA0$1(long j) {
        return Velocity.m880getXimpl(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-k-4lQ0M$1 */
    public final float mo917toFloatk4lQ0M$1(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toIntOffset-Bjo55l4 */
    public final long mo918toIntOffsetBjo55l4(int i) {
        long j = (i << 32) | (0 & 4294967295L);
        IntOffset.Companion companion = IntOffset.Companion;
        return j;
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toOffset-tuRUvjQ$1 */
    public final long mo919toOffsettuRUvjQ$1(float f) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    public final String toString() {
        return "HorizontalSpaceVectorConverter";
    }
}
