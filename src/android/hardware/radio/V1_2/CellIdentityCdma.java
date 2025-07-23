package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityCdma {
    public android.hardware.radio.V1_0.CellIdentityCdma base = new android.hardware.radio.V1_0.CellIdentityCdma();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityCdma.class) {
            return false;
        }
        CellIdentityCdma cellIdentityCdma = (CellIdentityCdma) obj;
        return HidlSupport.deepEquals(this.base, cellIdentityCdma.base) && HidlSupport.deepEquals(this.operatorNames, cellIdentityCdma.operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<CellIdentityCdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityCdma> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(cellIdentityCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityCdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
