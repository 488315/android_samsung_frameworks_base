package android.hardware.thermal.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CpuUsage {
    public String name = new String();
    public long active = 0;
    public long total = 0;
    public boolean isOnline = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CpuUsage.class) {
            return false;
        }
        CpuUsage cpuUsage = (CpuUsage) obj;
        return HidlSupport.deepEquals(this.name, cpuUsage.name) && this.active == cpuUsage.active && this.total == cpuUsage.total && this.isOnline == cpuUsage.isOnline;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.active))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.total))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isOnline))));
    }

    public final String toString() {
        return "{.name = " + this.name + ", .active = " + this.active + ", .total = " + this.total + ", .isOnline = " + this.isOnline + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<CpuUsage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CpuUsage> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CpuUsage cpuUsage = new CpuUsage();
            cpuUsage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(cpuUsage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.name = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j, false);
        this.active = hwBlob.getInt64(16 + j);
        this.total = hwBlob.getInt64(24 + j);
        this.isOnline = hwBlob.getBool(32 + j);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CpuUsage> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.name);
        hwBlob.putInt64(16 + j, this.active);
        hwBlob.putInt64(24 + j, this.total);
        hwBlob.putBool(j + 32, this.isOnline);
    }
}
