package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MemRange {
    public int flags;
    public int totalBytes = 0;
    public int freeBytes = 0;
    public int type = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != MemRange.class) {
            return false;
        }
        MemRange memRange = (MemRange) obj;
        return this.totalBytes == memRange.totalBytes && this.freeBytes == memRange.freeBytes && this.type == memRange.type && HidlSupport.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(memRange.flags));
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.totalBytes))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.freeBytes))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.flags))));
    }

    public final String toString() {
        return "{.totalBytes = " + this.totalBytes + ", .freeBytes = " + this.freeBytes + ", .type = " + HubMemoryType.toString(this.type) + ", .flags = " + HubMemoryFlag.dumpBitfield(this.flags) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<MemRange> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<MemRange> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            MemRange memRange = new MemRange();
            memRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(memRange);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.totalBytes = hwBlob.getInt32(j);
        this.freeBytes = hwBlob.getInt32(4 + j);
        this.type = hwBlob.getInt32(8 + j);
        this.flags = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<MemRange> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.totalBytes);
        hwBlob.putInt32(4 + j, this.freeBytes);
        hwBlob.putInt32(8 + j, this.type);
        hwBlob.putInt32(j + 12, this.flags);
    }
}
