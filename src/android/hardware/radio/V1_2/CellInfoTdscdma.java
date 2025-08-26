package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoTdscdma {
    public CellIdentityTdscdma cellIdentityTdscdma = new CellIdentityTdscdma();
    public TdscdmaSignalStrength signalStrengthTdscdma = new TdscdmaSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoTdscdma.class) {
            return false;
        }
        CellInfoTdscdma cellInfoTdscdma = (CellInfoTdscdma) obj;
        return HidlSupport.deepEquals(this.cellIdentityTdscdma, cellInfoTdscdma.cellIdentityTdscdma) && HidlSupport.deepEquals(this.signalStrengthTdscdma, cellInfoTdscdma.signalStrengthTdscdma);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityTdscdma)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthTdscdma)));
    }

    public final String toString() {
        return "{.cellIdentityTdscdma = " + this.cellIdentityTdscdma + ", .signalStrengthTdscdma = " + this.signalStrengthTdscdma + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public static final ArrayList<CellInfoTdscdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoTdscdma> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 104, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma();
            cellInfoTdscdma.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 104);
            arrayList.add(cellInfoTdscdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.signalStrengthTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 88);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(104);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoTdscdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 104);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 104);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.cellIdentityTdscdma.writeEmbeddedToBlob(hwBlob, j);
        this.signalStrengthTdscdma.writeEmbeddedToBlob(hwBlob, j + 88);
    }
}
