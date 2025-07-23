package com.google.android.msdl.domain;

import android.os.VibrationAttributes;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface InteractionProperties {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DynamicVibrationScale implements InteractionProperties {
        public final float scale;
        public final VibrationAttributes vibrationAttributes;

        public DynamicVibrationScale(float f, VibrationAttributes vibrationAttributes) {
            this.scale = f;
            this.vibrationAttributes = vibrationAttributes;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DynamicVibrationScale)) {
                return false;
            }
            DynamicVibrationScale dynamicVibrationScale = (DynamicVibrationScale) obj;
            return Float.compare(this.scale, dynamicVibrationScale.scale) == 0 && Intrinsics.areEqual(this.vibrationAttributes, dynamicVibrationScale.vibrationAttributes);
        }

        @Override // com.google.android.msdl.domain.InteractionProperties
        public final VibrationAttributes getVibrationAttributes() {
            return this.vibrationAttributes;
        }

        public final int hashCode() {
            return this.vibrationAttributes.hashCode() + (Float.hashCode(this.scale) * 31);
        }

        public final String toString() {
            return "DynamicVibrationScale(scale=" + this.scale + ", vibrationAttributes=" + this.vibrationAttributes + ")";
        }
    }

    VibrationAttributes getVibrationAttributes();
}
