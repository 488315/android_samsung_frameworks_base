package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityTdscdma {
    public android.hardware.radio.V1_0.CellIdentityTdscdma base = new android.hardware.radio.V1_0.CellIdentityTdscdma();
    public int uarfcn = 0;
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityTdscdma.class) {
            return false;
        }
        CellIdentityTdscdma cellIdentityTdscdma = (CellIdentityTdscdma) obj;
        return HidlSupport.deepEquals(this.base, cellIdentityTdscdma.base) && this.uarfcn == cellIdentityTdscdma.uarfcn && HidlSupport.deepEquals(this.operatorNames, cellIdentityTdscdma.operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uarfcn))), Integer.valueOf(HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .uarfcn = " + this.uarfcn + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<CellIdentityTdscdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityTdscdma> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityTdscdma cellIdentityTdscdma = new CellIdentityTdscdma();
            cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(cellIdentityTdscdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.uarfcn = hwBlob.getInt32(48 + j);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityTdscdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(48 + j, this.uarfcn);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 56);
    }
}
