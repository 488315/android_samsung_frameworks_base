package android.hardware.tv.cec.V1_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CecMessage {
    public ArrayList body;
    public int destination;
    public int initiator;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CecMessage.class) {
            return false;
        }
        CecMessage cecMessage = (CecMessage) obj;
        return this.initiator == cecMessage.initiator
                && this.destination == cecMessage.destination
                && HidlSupport.deepEquals(this.body, cecMessage.body);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.initiator),
                AudioConfig$$ExternalSyntheticOutline0.m(this.destination),
                Integer.valueOf(HidlSupport.deepHashCode(this.body)));
    }

    public final String toString() {
        return "{.initiator = "
                + CecLogicalAddress.toString(this.initiator)
                + ", .destination = "
                + CecLogicalAddress.toString(this.destination)
                + ", .body = "
                + this.body
                + "}";
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        hwBlob.putInt32(0L, this.initiator);
        hwBlob.putInt32(4L, this.destination);
        int size = this.body.size();
        hwBlob.putInt32(16L, size);
        hwBlob.putBool(20L, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, ((Byte) this.body.get(i)).byteValue());
        }
        hwBlob.putBlob(8L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }
}
