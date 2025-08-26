package com.google.android.msdl.data.model;

import android.os.VibrationEffect;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class HapticComposition {
    public final VibrationEffect fallbackEffect;
    public final List primitives;

    public HapticComposition(List<HapticCompositionPrimitive> list, VibrationEffect vibrationEffect) {
        this.primitives = list;
        this.fallbackEffect = vibrationEffect;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HapticComposition)) {
            return false;
        }
        HapticComposition hapticComposition = (HapticComposition) obj;
        return Intrinsics.areEqual(this.primitives, hapticComposition.primitives) && Intrinsics.areEqual(this.fallbackEffect, hapticComposition.fallbackEffect);
    }

    public final int hashCode() {
        return this.fallbackEffect.hashCode() + (this.primitives.hashCode() * 31);
    }

    public final String toString() {
        return "HapticComposition(primitives=" + this.primitives + ", fallbackEffect=" + this.fallbackEffect + ")";
    }
}
