package com.google.android.msdl.data.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class HapticCompositionPrimitive {
    public final int delayMillis;
    public final int primitiveId;
    public final float scale;

    public HapticCompositionPrimitive(int i, float f, int i2) {
        this.primitiveId = i;
        this.scale = f;
        this.delayMillis = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HapticCompositionPrimitive)) {
            return false;
        }
        HapticCompositionPrimitive hapticCompositionPrimitive = (HapticCompositionPrimitive) obj;
        return this.primitiveId == hapticCompositionPrimitive.primitiveId && Float.compare(this.scale, hapticCompositionPrimitive.scale) == 0 && this.delayMillis == hapticCompositionPrimitive.delayMillis;
    }

    public final int hashCode() {
        return Integer.hashCode(this.delayMillis) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, Integer.hashCode(this.primitiveId) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HapticCompositionPrimitive(primitiveId=");
        sb.append(this.primitiveId);
        sb.append(", scale=");
        sb.append(this.scale);
        sb.append(", delayMillis=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.delayMillis, ")", sb);
    }

    public /* synthetic */ HapticCompositionPrimitive(int i, float f, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? 1.0f : f, (i3 & 4) != 0 ? 0 : i2);
    }
}
