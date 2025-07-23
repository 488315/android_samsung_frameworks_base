package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GsmSignalStrength {
    public int signalStrength = 0;
    public int bitErrorRate = 0;
    public int timingAdvance = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GsmSignalStrength.class) {
            return false;
        }
        GsmSignalStrength gsmSignalStrength = (GsmSignalStrength) obj;
        return this.signalStrength == gsmSignalStrength.signalStrength && this.bitErrorRate == gsmSignalStrength.bitErrorRate && this.timingAdvance == gsmSignalStrength.timingAdvance;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalStrength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bitErrorRate))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.timingAdvance))));
    }

    public final String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .bitErrorRate = " + this.bitErrorRate + ", .timingAdvance = " + this.timingAdvance + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final ArrayList<GsmSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmSignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GsmSignalStrength gsmSignalStrength = new GsmSignalStrength();
            gsmSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(gsmSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.signalStrength = hwBlob.getInt32(j);
        this.bitErrorRate = hwBlob.getInt32(4 + j);
        this.timingAdvance = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.signalStrength);
        hwBlob.putInt32(4 + j, this.bitErrorRate);
        hwBlob.putInt32(j + 8, this.timingAdvance);
    }
}
