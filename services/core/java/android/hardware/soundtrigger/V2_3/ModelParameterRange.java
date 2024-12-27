package android.hardware.soundtrigger.V2_3;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.Objects;

public final class ModelParameterRange {
    public int end;
    public int start;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ModelParameterRange.class) {
            return false;
        }
        ModelParameterRange modelParameterRange = (ModelParameterRange) obj;
        return this.start == modelParameterRange.start && this.end == modelParameterRange.end;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.start),
                AudioConfig$$ExternalSyntheticOutline0.m(this.end));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.start = ");
        sb.append(this.start);
        sb.append(", .end = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.end, sb, "}");
    }
}
