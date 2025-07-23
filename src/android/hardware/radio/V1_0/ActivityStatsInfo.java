package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ActivityStatsInfo {
    public int sleepModeTimeMs = 0;
    public int idleModeTimeMs = 0;
    public int[] txmModetimeMs = new int[5];
    public int rxModeTimeMs = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ActivityStatsInfo.class) {
            return false;
        }
        ActivityStatsInfo activityStatsInfo = (ActivityStatsInfo) obj;
        return this.sleepModeTimeMs == activityStatsInfo.sleepModeTimeMs && this.idleModeTimeMs == activityStatsInfo.idleModeTimeMs && HidlSupport.deepEquals(this.txmModetimeMs, activityStatsInfo.txmModetimeMs) && this.rxModeTimeMs == activityStatsInfo.rxModeTimeMs;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sleepModeTimeMs))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.idleModeTimeMs))), Integer.valueOf(HidlSupport.deepHashCode(this.txmModetimeMs)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rxModeTimeMs))));
    }

    public final String toString() {
        return "{.sleepModeTimeMs = " + this.sleepModeTimeMs + ", .idleModeTimeMs = " + this.idleModeTimeMs + ", .txmModetimeMs = " + Arrays.toString(this.txmModetimeMs) + ", .rxModeTimeMs = " + this.rxModeTimeMs + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<ActivityStatsInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ActivityStatsInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ActivityStatsInfo activityStatsInfo = new ActivityStatsInfo();
            activityStatsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(activityStatsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.sleepModeTimeMs = hwBlob.getInt32(j);
        this.idleModeTimeMs = hwBlob.getInt32(4 + j);
        hwBlob.copyToInt32Array(8 + j, this.txmModetimeMs, 5);
        this.rxModeTimeMs = hwBlob.getInt32(j + 28);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ActivityStatsInfo> arrayList) {
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
        hwBlob.putInt32(j, this.sleepModeTimeMs);
        hwBlob.putInt32(4 + j, this.idleModeTimeMs);
        long j2 = 8 + j;
        int[] iArr = this.txmModetimeMs;
        if (iArr == null || iArr.length != 5) {
            throw new IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt32Array(j2, iArr);
        hwBlob.putInt32(j + 28, this.rxModeTimeMs);
    }
}
