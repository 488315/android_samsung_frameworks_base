package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;

import java.util.Objects;

public final class AmFmBandRange {
    public int lowerBound;
    public int scanSpacing;
    public int spacing;
    public int upperBound;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AmFmBandRange.class) {
            return false;
        }
        AmFmBandRange amFmBandRange = (AmFmBandRange) obj;
        return this.lowerBound == amFmBandRange.lowerBound
                && this.upperBound == amFmBandRange.upperBound
                && this.spacing == amFmBandRange.spacing
                && this.scanSpacing == amFmBandRange.scanSpacing;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.lowerBound),
                AudioConfig$$ExternalSyntheticOutline0.m(this.upperBound),
                AudioConfig$$ExternalSyntheticOutline0.m(this.spacing),
                AudioConfig$$ExternalSyntheticOutline0.m(this.scanSpacing));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.lowerBound = ");
        sb.append(this.lowerBound);
        sb.append(", .upperBound = ");
        sb.append(this.upperBound);
        sb.append(", .spacing = ");
        sb.append(this.spacing);
        sb.append(", .scanSpacing = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.scanSpacing, sb, "}");
    }
}
