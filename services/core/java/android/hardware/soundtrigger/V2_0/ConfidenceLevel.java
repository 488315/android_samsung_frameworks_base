package android.hardware.soundtrigger.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.Objects;

public final class ConfidenceLevel {
    public int userId = 0;
    public int levelPercent = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ConfidenceLevel.class) {
            return false;
        }
        ConfidenceLevel confidenceLevel = (ConfidenceLevel) obj;
        return this.userId == confidenceLevel.userId
                && this.levelPercent == confidenceLevel.levelPercent;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.userId),
                AudioConfig$$ExternalSyntheticOutline0.m(this.levelPercent));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.userId = ");
        sb.append(this.userId);
        sb.append(", .levelPercent = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.levelPercent, sb, "}");
    }
}
