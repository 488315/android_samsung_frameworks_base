package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.Dial;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehDial {
    public Dial base = new Dial();
    public ArrayList<SehCallDetails> callDetails = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehDial.class) {
            return false;
        }
        SehDial sehDial = (SehDial) obj;
        return HidlSupport.deepEquals(this.base, sehDial.base) && HidlSupport.deepEquals(this.callDetails, sehDial.callDetails);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.callDetails)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .callDetails = " + this.callDetails + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<SehDial> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehDial> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehDial sehDial = new SehDial();
            sehDial.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(sehDial);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 40;
        int int32 = hwBlob.getInt32(j + 48);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, hwBlob.handle(), j2, true);
        this.callDetails.clear();
        for (int i = 0; i < int32; i++) {
            SehCallDetails sehCallDetails = new SehCallDetails();
            sehCallDetails.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            this.callDetails.add(sehCallDetails);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehDial> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        int size = this.callDetails.size();
        long j2 = 40 + j;
        hwBlob.putInt32(48 + j, size);
        hwBlob.putBool(j + 52, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            this.callDetails.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
