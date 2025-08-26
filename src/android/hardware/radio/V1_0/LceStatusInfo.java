package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LceStatusInfo {
    public int lceStatus = 0;
    public byte actualIntervalMs = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LceStatusInfo.class) {
            return false;
        }
        LceStatusInfo lceStatusInfo = (LceStatusInfo) obj;
        return this.lceStatus == lceStatusInfo.lceStatus && this.actualIntervalMs == lceStatusInfo.actualIntervalMs;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lceStatus))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.actualIntervalMs))));
    }

    public final String toString() {
        return "{.lceStatus = " + LceStatus.toString(this.lceStatus) + ", .actualIntervalMs = " + ((int) this.actualIntervalMs) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<LceStatusInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LceStatusInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LceStatusInfo lceStatusInfo = new LceStatusInfo();
            lceStatusInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
            arrayList.add(lceStatusInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.lceStatus = hwBlob.getInt32(j);
        this.actualIntervalMs = hwBlob.getInt8(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LceStatusInfo> arrayList) {
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
        hwBlob.putInt32(j, this.lceStatus);
        hwBlob.putInt8(j + 4, this.actualIntervalMs);
    }
}
