package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityGsm {
    public android.hardware.radio.V1_2.CellIdentityGsm base = new android.hardware.radio.V1_2.CellIdentityGsm();
    public ArrayList<String> additionalPlmns = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityGsm.class) {
            return false;
        }
        CellIdentityGsm cellIdentityGsm = (CellIdentityGsm) obj;
        return HidlSupport.deepEquals(this.base, cellIdentityGsm.base) && HidlSupport.deepEquals(this.additionalPlmns, cellIdentityGsm.additionalPlmns);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.additionalPlmns)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .additionalPlmns = " + this.additionalPlmns + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<CellIdentityGsm> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityGsm> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityGsm cellIdentityGsm = new CellIdentityGsm();
            cellIdentityGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(cellIdentityGsm);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 80;
        int int32 = hwBlob.getInt32(j + 88);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2, true);
        this.additionalPlmns.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = readEmbeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), i2, false);
            this.additionalPlmns.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityGsm> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j);
        int size = this.additionalPlmns.size();
        long j2 = 80 + j;
        hwBlob.putInt32(88 + j, size);
        hwBlob.putBool(j + 92, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.additionalPlmns.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
