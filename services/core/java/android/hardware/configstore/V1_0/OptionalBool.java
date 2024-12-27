package android.hardware.configstore.V1_0;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;

import java.util.Objects;

public final class OptionalBool {
    public boolean specified = false;
    public boolean value = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != OptionalBool.class) {
            return false;
        }
        OptionalBool optionalBool = (OptionalBool) obj;
        return this.specified == optionalBool.specified && this.value == optionalBool.value;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.specified),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.value));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.specified = ");
        sb.append(this.specified);
        sb.append(", .value = ");
        return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.value);
    }
}
