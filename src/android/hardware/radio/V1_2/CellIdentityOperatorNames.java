package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityOperatorNames {
    public String alphaLong = new String();
    public String alphaShort = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityOperatorNames.class) {
            return false;
        }
        CellIdentityOperatorNames cellIdentityOperatorNames = (CellIdentityOperatorNames) obj;
        return HidlSupport.deepEquals(this.alphaLong, cellIdentityOperatorNames.alphaLong) && HidlSupport.deepEquals(this.alphaShort, cellIdentityOperatorNames.alphaShort);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.alphaLong)), Integer.valueOf(HidlSupport.deepHashCode(this.alphaShort)));
    }

    public final String toString() {
        return "{.alphaLong = " + this.alphaLong + ", .alphaShort = " + this.alphaShort + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<CellIdentityOperatorNames> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityOperatorNames> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityOperatorNames cellIdentityOperatorNames = new CellIdentityOperatorNames();
            cellIdentityOperatorNames.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(cellIdentityOperatorNames);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.alphaLong = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.alphaShort = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityOperatorNames> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.alphaLong);
        hwBlob.putString(j + 16, this.alphaShort);
    }
}
