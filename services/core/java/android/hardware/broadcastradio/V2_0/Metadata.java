package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Metadata {
    public long intValue;
    public int key;
    public String stringValue;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Metadata.class) {
            return false;
        }
        Metadata metadata = (Metadata) obj;
        return this.key == metadata.key
                && this.intValue == metadata.intValue
                && HidlSupport.deepEquals(this.stringValue, metadata.stringValue);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.key),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.intValue))),
                Integer.valueOf(HidlSupport.deepHashCode(this.stringValue)));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.key = ");
        sb.append(this.key);
        sb.append(", .intValue = ");
        sb.append(this.intValue);
        sb.append(", .stringValue = ");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.stringValue, "}");
    }
}
