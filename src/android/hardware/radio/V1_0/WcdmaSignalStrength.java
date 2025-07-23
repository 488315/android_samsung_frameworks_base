package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class WcdmaSignalStrength {
    public int signalStrength = 0;
    public int bitErrorRate = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != WcdmaSignalStrength.class) {
            return false;
        }
        WcdmaSignalStrength wcdmaSignalStrength = (WcdmaSignalStrength) obj;
        return this.signalStrength == wcdmaSignalStrength.signalStrength && this.bitErrorRate == wcdmaSignalStrength.bitErrorRate;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalStrength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bitErrorRate))));
    }

    public final String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .bitErrorRate = " + this.bitErrorRate + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<WcdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<WcdmaSignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            WcdmaSignalStrength wcdmaSignalStrength = new WcdmaSignalStrength();
            wcdmaSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            arrayList.add(wcdmaSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.signalStrength = hwBlob.getInt32(j);
        this.bitErrorRate = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<WcdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 8);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.signalStrength);
        hwBlob.putInt32(j + 4, this.bitErrorRate);
    }
}
