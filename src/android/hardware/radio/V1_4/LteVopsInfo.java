package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LteVopsInfo {
    public boolean isVopsSupported = false;
    public boolean isEmcBearerSupported = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LteVopsInfo.class) {
            return false;
        }
        LteVopsInfo lteVopsInfo = (LteVopsInfo) obj;
        return this.isVopsSupported == lteVopsInfo.isVopsSupported && this.isEmcBearerSupported == lteVopsInfo.isEmcBearerSupported;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isVopsSupported))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isEmcBearerSupported))));
    }

    public final String toString() {
        return "{.isVopsSupported = " + this.isVopsSupported + ", .isEmcBearerSupported = " + this.isEmcBearerSupported + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public static final ArrayList<LteVopsInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LteVopsInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LteVopsInfo lteVopsInfo = new LteVopsInfo();
            lteVopsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
            arrayList.add(lteVopsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isVopsSupported = hwBlob.getBool(j);
        this.isEmcBearerSupported = hwBlob.getBool(j + 1);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(2);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LteVopsInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 2);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putBool(j, this.isVopsSupported);
        hwBlob.putBool(j + 1, this.isEmcBearerSupported);
    }
}
