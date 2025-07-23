package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class TdScdmaSignalStrength {
    public int rscp = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && obj.getClass() == TdScdmaSignalStrength.class && this.rscp == ((TdScdmaSignalStrength) obj).rscp;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rscp))));
    }

    public final String toString() {
        return "{.rscp = " + this.rscp + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final ArrayList<TdScdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<TdScdmaSignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            TdScdmaSignalStrength tdScdmaSignalStrength = new TdScdmaSignalStrength();
            tdScdmaSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 4);
            arrayList.add(tdScdmaSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.rscp = hwBlob.getInt32(j);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TdScdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.rscp);
    }
}
