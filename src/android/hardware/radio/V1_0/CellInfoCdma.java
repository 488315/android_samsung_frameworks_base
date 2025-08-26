package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoCdma {
    public CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
    public CdmaSignalStrength signalStrengthCdma = new CdmaSignalStrength();
    public EvdoSignalStrength signalStrengthEvdo = new EvdoSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoCdma.class) {
            return false;
        }
        CellInfoCdma cellInfoCdma = (CellInfoCdma) obj;
        return HidlSupport.deepEquals(this.cellIdentityCdma, cellInfoCdma.cellIdentityCdma) && HidlSupport.deepEquals(this.signalStrengthCdma, cellInfoCdma.signalStrengthCdma) && HidlSupport.deepEquals(this.signalStrengthEvdo, cellInfoCdma.signalStrengthEvdo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityCdma)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthCdma)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthEvdo)));
    }

    public final String toString() {
        return "{.cellIdentityCdma = " + this.cellIdentityCdma + ", .signalStrengthCdma = " + this.signalStrengthCdma + ", .signalStrengthEvdo = " + this.signalStrengthEvdo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<CellInfoCdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoCdma> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoCdma cellInfoCdma = new CellInfoCdma();
            cellInfoCdma.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(cellInfoCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellIdentityCdma.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.signalStrengthCdma.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.signalStrengthEvdo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 28);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoCdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.cellIdentityCdma.writeEmbeddedToBlob(hwBlob, j);
        this.signalStrengthCdma.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.signalStrengthEvdo.writeEmbeddedToBlob(hwBlob, j + 28);
    }
}
