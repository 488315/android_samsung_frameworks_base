package com.google.android.msdl.domain;

import android.os.VibrationAttributes;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public interface InteractionProperties {

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
