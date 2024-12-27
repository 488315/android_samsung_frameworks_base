package com.android.systemui.audio.soundcraft.model.phone;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PhoneEffectModel {
    public int dolbyIndex;
    public int eqIndex;
    public boolean spatialAudio;
    public boolean uhqUpscaler;
    public boolean voiceBoost;
    public boolean volumeNormalization;

    public PhoneEffectModel() {
        this(0, 0, false, false, false, false, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PhoneEffectModel)) {
            return false;
        }
        PhoneEffectModel phoneEffectModel = (PhoneEffectModel) obj;
        return this.dolbyIndex == phoneEffectModel.dolbyIndex && this.eqIndex == phoneEffectModel.eqIndex && this.voiceBoost == phoneEffectModel.voiceBoost && this.volumeNormalization == phoneEffectModel.volumeNormalization && this.uhqUpscaler == phoneEffectModel.uhqUpscaler && this.spatialAudio == phoneEffectModel.spatialAudio;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.spatialAudio) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.eqIndex, Integer.hashCode(this.dolbyIndex) * 31, 31), 31, this.voiceBoost), 31, this.volumeNormalization), 31, this.uhqUpscaler);
    }

    public final String toString() {
        int i = this.dolbyIndex;
        int i2 = this.eqIndex;
        boolean z = this.voiceBoost;
        boolean z2 = this.volumeNormalization;
        boolean z3 = this.uhqUpscaler;
        boolean z4 = this.spatialAudio;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "PhoneEffectModel(dolbyIndex=", ", eqIndex=", ", voiceBoost=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z, ", volumeNormalization=", z2, ", uhqUpscaler=");
        m.append(z3);
        m.append(", spatialAudio=");
        m.append(z4);
        m.append(")");
        return m.toString();
    }

    public PhoneEffectModel(int i, int i2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.dolbyIndex = i;
        this.eqIndex = i2;
        this.voiceBoost = z;
        this.volumeNormalization = z2;
        this.uhqUpscaler = z3;
        this.spatialAudio = z4;
    }

    public /* synthetic */ PhoneEffectModel(int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? false : z, (i3 & 8) != 0 ? false : z2, (i3 & 16) != 0 ? false : z3, (i3 & 32) != 0 ? false : z4);
    }
}
