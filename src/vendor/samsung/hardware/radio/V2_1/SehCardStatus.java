package vendor.samsung.hardware.radio.V2_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCardStatus {
    public vendor.samsung.hardware.radio.V2_0.SehCardStatus base = new vendor.samsung.hardware.radio.V2_0.SehCardStatus();
    public String eid = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCardStatus.class) {
            return false;
        }
        SehCardStatus sehCardStatus = (SehCardStatus) obj;
        return HidlSupport.deepEquals(this.base, sehCardStatus.base) && HidlSupport.deepEquals(this.eid, sehCardStatus.eid);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.eid)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .eid = " + this.eid + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<SehCardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCardStatus> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCardStatus sehCardStatus = new SehCardStatus();
            sehCardStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(sehCardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 80;
        this.eid = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCardStatus> arrayList) {
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
        hwBlob.putString(j + 80, this.eid);
    }
}
