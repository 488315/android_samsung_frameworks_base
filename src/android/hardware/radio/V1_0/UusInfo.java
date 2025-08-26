package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class UusInfo {
    public int uusType = 0;
    public int uusDcs = 0;
    public String uusData = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != UusInfo.class) {
            return false;
        }
        UusInfo uusInfo = (UusInfo) obj;
        return this.uusType == uusInfo.uusType && this.uusDcs == uusInfo.uusDcs && HidlSupport.deepEquals(this.uusData, uusInfo.uusData);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uusType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uusDcs))), Integer.valueOf(HidlSupport.deepHashCode(this.uusData)));
    }

    public final String toString() {
        return "{.uusType = " + UusType.toString(this.uusType) + ", .uusDcs = " + UusDcs.toString(this.uusDcs) + ", .uusData = " + this.uusData + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<UusInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<UusInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            UusInfo uusInfo = new UusInfo();
            uusInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            arrayList.add(uusInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.uusType = hwBlob.getInt32(j);
        this.uusDcs = hwBlob.getInt32(4 + j);
        long j2 = j + 8;
        this.uusData = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<UusInfo> arrayList) {
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
        hwBlob.putInt32(j, this.uusType);
        hwBlob.putInt32(4 + j, this.uusDcs);
        hwBlob.putString(j + 8, this.uusData);
    }
}
