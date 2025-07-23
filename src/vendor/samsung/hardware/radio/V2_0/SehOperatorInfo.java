package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.OperatorInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehOperatorInfo {
    public OperatorInfo base = new OperatorInfo();
    public String rat = new String();
    public String lac = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehOperatorInfo.class) {
            return false;
        }
        SehOperatorInfo sehOperatorInfo = (SehOperatorInfo) obj;
        return HidlSupport.deepEquals(this.base, sehOperatorInfo.base) && HidlSupport.deepEquals(this.rat, sehOperatorInfo.rat) && HidlSupport.deepEquals(this.lac, sehOperatorInfo.lac);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.rat)), Integer.valueOf(HidlSupport.deepHashCode(this.lac)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .rat = " + this.rat + ", .lac = " + this.lac + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<SehOperatorInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehOperatorInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehOperatorInfo sehOperatorInfo = new SehOperatorInfo();
            sehOperatorInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(sehOperatorInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 56;
        this.rat = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r3.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 72;
        this.lac = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, hwBlob.handle(), j3, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehOperatorInfo> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putString(56 + j, this.rat);
        hwBlob.putString(j + 72, this.lac);
    }
}
