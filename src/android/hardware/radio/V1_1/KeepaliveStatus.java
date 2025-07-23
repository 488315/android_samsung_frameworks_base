package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeepaliveStatus {
    public int sessionHandle = 0;
    public int code = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != KeepaliveStatus.class) {
            return false;
        }
        KeepaliveStatus keepaliveStatus = (KeepaliveStatus) obj;
        return this.sessionHandle == keepaliveStatus.sessionHandle && this.code == keepaliveStatus.code;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sessionHandle))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.code))));
    }

    public final String toString() {
        return "{.sessionHandle = " + this.sessionHandle + ", .code = " + KeepaliveStatusCode.toString(this.code) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<KeepaliveStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<KeepaliveStatus> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            KeepaliveStatus keepaliveStatus = new KeepaliveStatus();
            keepaliveStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            arrayList.add(keepaliveStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.sessionHandle = hwBlob.getInt32(j);
        this.code = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<KeepaliveStatus> arrayList) {
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
        hwBlob.putInt32(j, this.sessionHandle);
        hwBlob.putInt32(j + 4, this.code);
    }
}
