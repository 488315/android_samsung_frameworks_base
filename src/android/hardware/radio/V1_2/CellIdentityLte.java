package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityLte {
    public android.hardware.radio.V1_0.CellIdentityLte base = new android.hardware.radio.V1_0.CellIdentityLte();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();
    public int bandwidth = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityLte.class) {
            return false;
        }
        CellIdentityLte cellIdentityLte = (CellIdentityLte) obj;
        return HidlSupport.deepEquals(this.base, cellIdentityLte.base) && HidlSupport.deepEquals(this.operatorNames, cellIdentityLte.operatorNames) && this.bandwidth == cellIdentityLte.bandwidth;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.operatorNames)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bandwidth))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .operatorNames = " + this.operatorNames + ", .bandwidth = " + this.bandwidth + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<CellIdentityLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityLte> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityLte cellIdentityLte = new CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(cellIdentityLte);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, 48 + j);
        this.bandwidth = hwBlob.getInt32(j + 80);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityLte> arrayList) {
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
        this.operatorNames.writeEmbeddedToBlob(hwBlob, 48 + j);
        hwBlob.putInt32(j + 80, this.bandwidth);
    }
}
