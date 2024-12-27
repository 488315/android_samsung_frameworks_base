package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;

import java.util.Objects;

public final class ProgramIdentifier {
    public int type = 0;
    public long value = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ProgramIdentifier.class) {
            return false;
        }
        ProgramIdentifier programIdentifier = (ProgramIdentifier) obj;
        return this.type == programIdentifier.type && this.value == programIdentifier.value;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.type),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.value))));
    }

    public final void readEmbeddedFromParcel(HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        this.value = hwBlob.getInt64(j + 8);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.type = ");
        sb.append(this.type);
        sb.append(", .value = ");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.value, "}");
    }
}
