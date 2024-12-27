package android.hardware.biometrics.face.V1_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.Objects;

public final class OptionalUint64 {
    public int status = 0;
    public long value = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != OptionalUint64.class) {
            return false;
        }
        OptionalUint64 optionalUint64 = (OptionalUint64) obj;
        return this.status == optionalUint64.status && this.value == optionalUint64.value;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.status),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.value))));
    }

    public final void readFromParcel(HwParcel hwParcel) {
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        this.status = readBuffer.getInt32(0L);
        this.value = readBuffer.getInt64(8L);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.status = ");
        sb.append(Status.toString(this.status));
        sb.append(", .value = ");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.value, "}");
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        hwBlob.putInt32(0L, this.status);
        hwBlob.putInt64(8L, this.value);
        hwParcel.writeBuffer(hwBlob);
    }
}
