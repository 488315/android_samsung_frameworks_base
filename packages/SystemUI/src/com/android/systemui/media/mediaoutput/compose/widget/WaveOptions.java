package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WaveOptions {
    public final float amplitude;
    public final float frequency;
    public final float trackWidth;

    public WaveOptions(float f, float f2, float f3) {
        this.amplitude = f;
        this.frequency = f2;
        this.trackWidth = f3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WaveOptions)) {
            return false;
        }
        WaveOptions waveOptions = (WaveOptions) obj;
        return Float.compare(this.amplitude, waveOptions.amplitude) == 0 && Float.compare(this.frequency, waveOptions.frequency) == 0 && Float.compare(this.trackWidth, waveOptions.trackWidth) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.trackWidth) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.frequency, Float.hashCode(this.amplitude) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WaveOptions(amplitude=");
        sb.append(this.amplitude);
        sb.append(", frequency=");
        sb.append(this.frequency);
        sb.append(", trackWidth=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.trackWidth, ")");
    }
}
