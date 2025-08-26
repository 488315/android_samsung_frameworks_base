package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehStoredMsgCount {
    public int usedCount = 0;
    public int totalCount = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehStoredMsgCount.class) {
            return false;
        }
        SehStoredMsgCount sehStoredMsgCount = (SehStoredMsgCount) obj;
        return this.usedCount == sehStoredMsgCount.usedCount && this.totalCount == sehStoredMsgCount.totalCount;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.usedCount))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.totalCount))));
    }

    public final String toString() {
        return "{.usedCount = " + this.usedCount + ", .totalCount = " + this.totalCount + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<SehStoredMsgCount> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehStoredMsgCount> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehStoredMsgCount sehStoredMsgCount = new SehStoredMsgCount();
            sehStoredMsgCount.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
            arrayList.add(sehStoredMsgCount);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.usedCount = hwBlob.getInt32(j);
        this.totalCount = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehStoredMsgCount> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 8);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.usedCount);
        hwBlob.putInt32(j + 4, this.totalCount);
    }
}
