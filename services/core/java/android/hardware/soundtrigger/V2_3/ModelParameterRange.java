package android.hardware.soundtrigger.V2_3;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
