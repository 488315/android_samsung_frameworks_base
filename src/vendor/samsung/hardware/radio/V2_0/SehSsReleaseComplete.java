package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSsReleaseComplete {
    public int size = 0;
    public int dataLen = 0;
    public int params = 0;
    public int status = 0;
    public String data = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSsReleaseComplete.class) {
            return false;
        }
        SehSsReleaseComplete sehSsReleaseComplete = (SehSsReleaseComplete) obj;
        return this.size == sehSsReleaseComplete.size && this.dataLen == sehSsReleaseComplete.dataLen && this.params == sehSsReleaseComplete.params && this.status == sehSsReleaseComplete.status && HidlSupport.deepEquals(this.data, sehSsReleaseComplete.data);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.size))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.dataLen))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.params))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
    }

    public final String toString() {
        return "{.size = " + this.size + ", .dataLen = " + this.dataLen + ", .params = " + this.params + ", .status = " + this.status + ", .data = " + this.data + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SehSsReleaseComplete> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSsReleaseComplete> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSsReleaseComplete sehSsReleaseComplete = new SehSsReleaseComplete();
            sehSsReleaseComplete.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(sehSsReleaseComplete);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.size = hwBlob.getInt32(j);
        this.dataLen = hwBlob.getInt32(4 + j);
        this.params = hwBlob.getInt32(8 + j);
        this.status = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.data = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSsReleaseComplete> arrayList) {
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
        hwBlob.putInt32(j, this.size);
        hwBlob.putInt32(4 + j, this.dataLen);
        hwBlob.putInt32(8 + j, this.params);
        hwBlob.putInt32(12 + j, this.status);
        hwBlob.putString(j + 16, this.data);
    }
}
