package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class WcdmaSignalStrength {
    public android.hardware.radio.V1_0.WcdmaSignalStrength base = new android.hardware.radio.V1_0.WcdmaSignalStrength();
    public int rscp = 0;
    public int ecno = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != WcdmaSignalStrength.class) {
            return false;
        }
        WcdmaSignalStrength wcdmaSignalStrength = (WcdmaSignalStrength) obj;
        return HidlSupport.deepEquals(this.base, wcdmaSignalStrength.base) && this.rscp == wcdmaSignalStrength.rscp && this.ecno == wcdmaSignalStrength.ecno;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rscp))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ecno))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .rscp = " + this.rscp + ", .ecno = " + this.ecno + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<WcdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<WcdmaSignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            WcdmaSignalStrength wcdmaSignalStrength = new WcdmaSignalStrength();
            wcdmaSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(wcdmaSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.rscp = hwBlob.getInt32(8 + j);
        this.ecno = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<WcdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(8 + j, this.rscp);
        hwBlob.putInt32(j + 12, this.ecno);
    }
}
