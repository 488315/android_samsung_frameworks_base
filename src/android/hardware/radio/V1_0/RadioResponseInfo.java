package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioResponseInfo {
    public int type = 0;
    public int serial = 0;
    public int error = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioResponseInfo.class) {
            return false;
        }
        RadioResponseInfo radioResponseInfo = (RadioResponseInfo) obj;
        return this.type == radioResponseInfo.type && this.serial == radioResponseInfo.serial && this.error == radioResponseInfo.error;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serial))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.error))));
    }

    public final String toString() {
        return "{.type = " + RadioResponseType.toString(this.type) + ", .serial = " + this.serial + ", .error = " + RadioError.toString(this.error) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final ArrayList<RadioResponseInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioResponseInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
            radioResponseInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(radioResponseInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        this.serial = hwBlob.getInt32(4 + j);
        this.error = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioResponseInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.type);
        hwBlob.putInt32(4 + j, this.serial);
        hwBlob.putInt32(j + 8, this.error);
    }
}
