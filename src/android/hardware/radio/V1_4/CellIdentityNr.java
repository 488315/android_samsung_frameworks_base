package android.hardware.radio.V1_4;

import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityNr {
    public String mcc = new String();
    public String mnc = new String();
    public long nci = 0;
    public int pci = 0;
    public int tac = 0;
    public int nrarfcn = 0;
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityNr.class) {
            return false;
        }
        CellIdentityNr cellIdentityNr = (CellIdentityNr) obj;
        return HidlSupport.deepEquals(this.mcc, cellIdentityNr.mcc) && HidlSupport.deepEquals(this.mnc, cellIdentityNr.mnc) && this.nci == cellIdentityNr.nci && this.pci == cellIdentityNr.pci && this.tac == cellIdentityNr.tac && this.nrarfcn == cellIdentityNr.nrarfcn && HidlSupport.deepEquals(this.operatorNames, cellIdentityNr.operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.mcc)), Integer.valueOf(HidlSupport.deepHashCode(this.mnc)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.nci))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pci))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.tac))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nrarfcn))), Integer.valueOf(HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .nci = " + this.nci + ", .pci = " + this.pci + ", .tac = " + this.tac + ", .nrarfcn = " + this.nrarfcn + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<CellIdentityNr> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityNr> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityNr cellIdentityNr = new CellIdentityNr();
            cellIdentityNr.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(cellIdentityNr);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.mcc = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.mnc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.nci = hwBlob.getInt64(j + 32);
        this.pci = hwBlob.getInt32(j + 40);
        this.tac = hwBlob.getInt32(j + 44);
        this.nrarfcn = hwBlob.getInt32(j + 48);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityNr> arrayList) {
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
        hwBlob.putString(j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt64(32 + j, this.nci);
        hwBlob.putInt32(40 + j, this.pci);
        hwBlob.putInt32(44 + j, this.tac);
        hwBlob.putInt32(48 + j, this.nrarfcn);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 56);
    }
}
