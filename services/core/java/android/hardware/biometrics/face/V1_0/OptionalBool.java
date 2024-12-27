package android.hardware.biometrics.face.V1_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OptionalBool {
    public int status = 0;
    public boolean value = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != OptionalBool.class) {
            return false;
        }
        OptionalBool optionalBool = (OptionalBool) obj;
        return this.status == optionalBool.status && this.value == optionalBool.value;
    }

    public final int hashCode() {
        return Objects.hash(AudioConfig$$ExternalSyntheticOutline0.m(this.status), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.value));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.status = ");
        sb.append(Status.toString(this.status));
        sb.append(", .value = ");
        return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.value);
    }
}
