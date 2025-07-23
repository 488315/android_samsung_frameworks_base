package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityGsm {
    public android.hardware.radio.V1_0.CellIdentityGsm base = new android.hardware.radio.V1_0.CellIdentityGsm();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityGsm.class) {
            return false;
        }
        CellIdentityGsm cellIdentityGsm = (CellIdentityGsm) obj;
        return HidlSupport.deepEquals(this.base, cellIdentityGsm.base) && HidlSupport.deepEquals(this.operatorNames, cellIdentityGsm.operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final ArrayList<CellIdentityGsm> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityGsm> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityGsm cellIdentityGsm = new CellIdentityGsm();
            cellIdentityGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            arrayList.add(cellIdentityGsm);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 48);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityGsm> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 48);
    }
}
