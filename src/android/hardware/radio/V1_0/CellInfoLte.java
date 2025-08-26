package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoLte {
    public CellIdentityLte cellIdentityLte = new CellIdentityLte();
    public LteSignalStrength signalStrengthLte = new LteSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoLte.class) {
            return false;
        }
        CellInfoLte cellInfoLte = (CellInfoLte) obj;
        return HidlSupport.deepEquals(this.cellIdentityLte, cellInfoLte.cellIdentityLte) && HidlSupport.deepEquals(this.signalStrengthLte, cellInfoLte.signalStrengthLte);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityLte)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthLte)));
    }

    public final String toString() {
        return "{.cellIdentityLte = " + this.cellIdentityLte + ", .signalStrengthLte = " + this.signalStrengthLte + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final ArrayList<CellInfoLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoLte> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoLte cellInfoLte = new CellInfoLte();
            cellInfoLte.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 72);
            arrayList.add(cellInfoLte);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellIdentityLte.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.signalStrengthLte.readEmbeddedFromParcel(hwParcel, hwBlob, j + 48);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.cellIdentityLte.writeEmbeddedToBlob(hwBlob, j);
        this.signalStrengthLte.writeEmbeddedToBlob(hwBlob, j + 48);
    }
}
