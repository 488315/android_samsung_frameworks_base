package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoWcdma {
    public CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
    public WcdmaSignalStrength signalStrengthWcdma = new WcdmaSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoWcdma.class) {
            return false;
        }
        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) obj;
        return HidlSupport.deepEquals(this.cellIdentityWcdma, cellInfoWcdma.cellIdentityWcdma) && HidlSupport.deepEquals(this.signalStrengthWcdma, cellInfoWcdma.signalStrengthWcdma);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityWcdma)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthWcdma)));
    }

    public final String toString() {
        return "{.cellIdentityWcdma = " + this.cellIdentityWcdma + ", .signalStrengthWcdma = " + this.signalStrengthWcdma + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<CellInfoWcdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoWcdma> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoWcdma cellInfoWcdma = new CellInfoWcdma();
            cellInfoWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(cellInfoWcdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.signalStrengthWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 80);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoWcdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.cellIdentityWcdma.writeEmbeddedToBlob(hwBlob, j);
        this.signalStrengthWcdma.writeEmbeddedToBlob(hwBlob, j + 80);
    }
}
