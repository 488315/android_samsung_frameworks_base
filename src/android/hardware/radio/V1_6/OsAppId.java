package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class OsAppId {
    public ArrayList<Byte> osAppId = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && obj.getClass() == OsAppId.class && HidlSupport.deepEquals(this.osAppId, ((OsAppId) obj).osAppId);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.osAppId)));
    }

    public final String toString() {
        return "{.osAppId = " + this.osAppId + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<OsAppId> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<OsAppId> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            OsAppId osAppId = new OsAppId();
            osAppId.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(osAppId);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j, true);
        this.osAppId.clear();
        for (int i = 0; i < int32; i++) {
            this.osAppId.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<OsAppId> arrayList) {
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
        int size = this.osAppId.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.osAppId.get(i).byteValue());
        }
        hwBlob.putBlob(j, hwBlob2);
    }
}
