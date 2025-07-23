package android.hardware.gnss.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ElapsedRealtime {
    public short flags;
    public long timestampNs = 0;
    public long timeUncertaintyNs = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ElapsedRealtime.class) {
            return false;
        }
        ElapsedRealtime elapsedRealtime = (ElapsedRealtime) obj;
        return HidlSupport.deepEquals(Short.valueOf(this.flags), Short.valueOf(elapsedRealtime.flags)) && this.timestampNs == elapsedRealtime.timestampNs && this.timeUncertaintyNs == elapsedRealtime.timeUncertaintyNs;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.flags))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.timestampNs))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.timeUncertaintyNs))));
    }

    public final String toString() {
        return "{.flags = " + ElapsedRealtimeFlags.dumpBitfield(this.flags) + ", .timestampNs = " + this.timestampNs + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<ElapsedRealtime> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ElapsedRealtime> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ElapsedRealtime elapsedRealtime = new ElapsedRealtime();
            elapsedRealtime.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(elapsedRealtime);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.flags = hwBlob.getInt16(j);
        this.timestampNs = hwBlob.getInt64(8 + j);
        this.timeUncertaintyNs = hwBlob.getInt64(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ElapsedRealtime> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt16(j, this.flags);
        hwBlob.putInt64(8 + j, this.timestampNs);
        hwBlob.putInt64(j + 16, this.timeUncertaintyNs);
    }
}
