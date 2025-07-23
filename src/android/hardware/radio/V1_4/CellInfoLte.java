package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoLte {
    public android.hardware.radio.V1_2.CellInfoLte base = new android.hardware.radio.V1_2.CellInfoLte();
    public CellConfigLte cellConfig = new CellConfigLte();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoLte.class) {
            return false;
        }
        CellInfoLte cellInfoLte = (CellInfoLte) obj;
        return HidlSupport.deepEquals(this.base, cellInfoLte.base) && HidlSupport.deepEquals(this.cellConfig, cellInfoLte.cellConfig);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.cellConfig)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .cellConfig = " + this.cellConfig + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final ArrayList<CellInfoLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoLte> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoLte cellInfoLte = new CellInfoLte();
            cellInfoLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(cellInfoLte);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.cellConfig.readEmbeddedFromParcel(hwParcel, hwBlob, j + 112);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        this.cellConfig.writeEmbeddedToBlob(hwBlob, j + 112);
    }
}
