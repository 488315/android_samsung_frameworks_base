package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LceDataInfo {
    public int lastHopCapacityKbps = 0;
    public byte confidenceLevel = 0;
    public boolean lceSuspended = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LceDataInfo.class) {
            return false;
        }
        LceDataInfo lceDataInfo = (LceDataInfo) obj;
        return this.lastHopCapacityKbps == lceDataInfo.lastHopCapacityKbps && this.confidenceLevel == lceDataInfo.confidenceLevel && this.lceSuspended == lceDataInfo.lceSuspended;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lastHopCapacityKbps))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.confidenceLevel))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.lceSuspended))));
    }

    public final String toString() {
        return "{.lastHopCapacityKbps = " + this.lastHopCapacityKbps + ", .confidenceLevel = " + ((int) this.confidenceLevel) + ", .lceSuspended = " + this.lceSuspended + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<LceDataInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LceDataInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LceDataInfo lceDataInfo = new LceDataInfo();
            lceDataInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
            arrayList.add(lceDataInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.lastHopCapacityKbps = hwBlob.getInt32(j);
        this.confidenceLevel = hwBlob.getInt8(4 + j);
        this.lceSuspended = hwBlob.getBool(j + 5);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LceDataInfo> arrayList) {
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
        hwBlob.putInt32(j, this.lastHopCapacityKbps);
        hwBlob.putInt8(4 + j, this.confidenceLevel);
        hwBlob.putBool(j + 5, this.lceSuspended);
    }
}
