package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimLockInfo {
    public int numberOfLockTypes = 0;
    public int lockType = 0;
    public int lockKey = 0;
    public int numberOfRetry = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSimLockInfo.class) {
            return false;
        }
        SehSimLockInfo sehSimLockInfo = (SehSimLockInfo) obj;
        return this.numberOfLockTypes == sehSimLockInfo.numberOfLockTypes && this.lockType == sehSimLockInfo.lockType && this.lockKey == sehSimLockInfo.lockKey && this.numberOfRetry == sehSimLockInfo.numberOfRetry;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberOfLockTypes))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lockType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lockKey))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberOfRetry))));
    }

    public final String toString() {
        return "{.numberOfLockTypes = " + this.numberOfLockTypes + ", .lockType = " + this.lockType + ", .lockKey = " + this.lockKey + ", .numberOfRetry = " + this.numberOfRetry + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<SehSimLockInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSimLockInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSimLockInfo sehSimLockInfo = new SehSimLockInfo();
            sehSimLockInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(sehSimLockInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.numberOfLockTypes = hwBlob.getInt32(j);
        this.lockType = hwBlob.getInt32(4 + j);
        this.lockKey = hwBlob.getInt32(8 + j);
        this.numberOfRetry = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSimLockInfo> arrayList) {
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
        hwBlob.putInt32(j, this.numberOfLockTypes);
        hwBlob.putInt32(4 + j, this.lockType);
        hwBlob.putInt32(8 + j, this.lockKey);
        hwBlob.putInt32(j + 12, this.numberOfRetry);
    }
}
