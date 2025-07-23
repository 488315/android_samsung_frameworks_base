package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NrSignalStrength {
    public android.hardware.radio.V1_4.NrSignalStrength base = new android.hardware.radio.V1_4.NrSignalStrength();
    public int csiCqiTableIndex = 0;
    public ArrayList<Byte> csiCqiReport = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NrSignalStrength.class) {
            return false;
        }
        NrSignalStrength nrSignalStrength = (NrSignalStrength) obj;
        return HidlSupport.deepEquals(this.base, nrSignalStrength.base) && this.csiCqiTableIndex == nrSignalStrength.csiCqiTableIndex && HidlSupport.deepEquals(this.csiCqiReport, nrSignalStrength.csiCqiReport);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csiCqiTableIndex))), Integer.valueOf(HidlSupport.deepHashCode(this.csiCqiReport)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .csiCqiTableIndex = " + this.csiCqiTableIndex + ", .csiCqiReport = " + this.csiCqiReport + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<NrSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrSignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NrSignalStrength nrSignalStrength = new NrSignalStrength();
            nrSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(nrSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.csiCqiTableIndex = hwBlob.getInt32(24 + j);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(j + 40);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j2, true);
        this.csiCqiReport.clear();
        for (int i = 0; i < int32; i++) {
            this.csiCqiReport.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(24 + j, this.csiCqiTableIndex);
        int size = this.csiCqiReport.size();
        long j2 = 32 + j;
        hwBlob.putInt32(40 + j, size);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.csiCqiReport.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
