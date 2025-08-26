package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoNr {
    public NrSignalStrength signalStrength = new NrSignalStrength();
    public CellIdentityNr cellidentity = new CellIdentityNr();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellInfoNr.class) {
            return false;
        }
        CellInfoNr cellInfoNr = (CellInfoNr) obj;
        return HidlSupport.deepEquals(this.signalStrength, cellInfoNr.signalStrength) && HidlSupport.deepEquals(this.cellidentity, cellInfoNr.cellidentity);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.signalStrength)), Integer.valueOf(HidlSupport.deepHashCode(this.cellidentity)));
    }

    public final String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .cellidentity = " + this.cellidentity + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<CellInfoNr> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoNr> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfoNr cellInfoNr = new CellInfoNr();
            cellInfoNr.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
            arrayList.add(cellInfoNr);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.signalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.cellidentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoNr> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.signalStrength.writeEmbeddedToBlob(hwBlob, j);
        this.cellidentity.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
