package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NrVopsInfo {
    public byte vopsSupported = 0;
    public byte emcSupported = 0;
    public byte emfSupported = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NrVopsInfo.class) {
            return false;
        }
        NrVopsInfo nrVopsInfo = (NrVopsInfo) obj;
        return this.vopsSupported == nrVopsInfo.vopsSupported && this.emcSupported == nrVopsInfo.emcSupported && this.emfSupported == nrVopsInfo.emfSupported;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.vopsSupported))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.emcSupported))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.emfSupported))));
    }

    public final String toString() {
        return "{.vopsSupported = " + VopsIndicator.toString(this.vopsSupported) + ", .emcSupported = " + EmcIndicator.toString(this.emcSupported) + ", .emfSupported = " + EmfIndicator.toString(this.emfSupported) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
    }

    public static final ArrayList<NrVopsInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrVopsInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 3, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NrVopsInfo nrVopsInfo = new NrVopsInfo();
            nrVopsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 3);
            arrayList.add(nrVopsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.vopsSupported = hwBlob.getInt8(j);
        this.emcSupported = hwBlob.getInt8(1 + j);
        this.emfSupported = hwBlob.getInt8(j + 2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(3);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrVopsInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 3);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.vopsSupported);
        hwBlob.putInt8(1 + j, this.emcSupported);
        hwBlob.putInt8(j + 2, this.emfSupported);
    }
}
