package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityWcdma {
    public String mcc = new String();
    public String mnc = new String();
    public int lac = 0;
    public int cid = 0;
    public int psc = 0;
    public int uarfcn = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityWcdma.class) {
            return false;
        }
        CellIdentityWcdma cellIdentityWcdma = (CellIdentityWcdma) obj;
        return HidlSupport.deepEquals(this.mcc, cellIdentityWcdma.mcc) && HidlSupport.deepEquals(this.mnc, cellIdentityWcdma.mnc) && this.lac == cellIdentityWcdma.lac && this.cid == cellIdentityWcdma.cid && this.psc == cellIdentityWcdma.psc && this.uarfcn == cellIdentityWcdma.uarfcn;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.mcc)), Integer.valueOf(HidlSupport.deepHashCode(this.mnc)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lac))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.psc))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uarfcn))));
    }

    public final String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .lac = " + this.lac + ", .cid = " + this.cid + ", .psc = " + this.psc + ", .uarfcn = " + this.uarfcn + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<CellIdentityWcdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityWcdma> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
            cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 48);
            arrayList.add(cellIdentityWcdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.mcc = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.mnc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.lac = hwBlob.getInt32(j + 32);
        this.cid = hwBlob.getInt32(j + 36);
        this.psc = hwBlob.getInt32(j + 40);
        this.uarfcn = hwBlob.getInt32(j + 44);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityWcdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt32(32 + j, this.lac);
        hwBlob.putInt32(36 + j, this.cid);
        hwBlob.putInt32(40 + j, this.psc);
        hwBlob.putInt32(j + 44, this.uarfcn);
    }
}
