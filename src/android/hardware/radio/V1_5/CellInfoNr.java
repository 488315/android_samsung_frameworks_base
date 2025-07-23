package android.hardware.radio.V1_5;

import android.hardware.radio.V1_4.NrSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoNr {
    public CellIdentityNr cellIdentityNr = new CellIdentityNr();
    public NrSignalStrength signalStrengthNr = new NrSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoNr.class) {
            return false;
        }
        CellInfoNr cellInfoNr = (CellInfoNr) obj;
        return HidlSupport.deepEquals(this.cellIdentityNr, cellInfoNr.cellIdentityNr) && HidlSupport.deepEquals(this.signalStrengthNr, cellInfoNr.signalStrengthNr);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityNr)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthNr)));
    }

    public final String toString() {
        return "{.cellIdentityNr = " + this.cellIdentityNr + ", .signalStrengthNr = " + this.signalStrengthNr + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(144L), 0L);
    }

    public static final ArrayList<CellInfoNr> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoNr> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 144, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoNr cellInfoNr = new CellInfoNr();
            cellInfoNr.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 144);
            arrayList.add(cellInfoNr);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellIdentityNr.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.signalStrengthNr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 120);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(144);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoNr> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 144);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 144);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.cellIdentityNr.writeEmbeddedToBlob(hwBlob, j);
        this.signalStrengthNr.writeEmbeddedToBlob(hwBlob, j + 120);
    }
}
