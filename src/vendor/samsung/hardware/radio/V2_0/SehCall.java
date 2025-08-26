package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_2.AudioQuality;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCall {
    public Call base = new Call();
    public int audioQuality = 0;
    public ArrayList<SehCallDetails> callDetails = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCall.class) {
            return false;
        }
        SehCall sehCall = (SehCall) obj;
        return HidlSupport.deepEquals(this.base, sehCall.base) && this.audioQuality == sehCall.audioQuality && HidlSupport.deepEquals(this.callDetails, sehCall.callDetails);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.audioQuality))), Integer.valueOf(HidlSupport.deepHashCode(this.callDetails)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .audioQuality = " + AudioQuality.toString(this.audioQuality) + ", .callDetails = " + this.callDetails + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<SehCall> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCall> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCall sehCall = new SehCall();
            sehCall.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
            arrayList.add(sehCall);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.audioQuality = hwBlob.getInt32(88 + j);
        long j2 = j + 96;
        int int32 = hwBlob.getInt32(j + 104);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, hwBlob.handle(), j2, true);
        this.callDetails.clear();
        for (int i = 0; i < int32; i++) {
            SehCallDetails sehCallDetails = new SehCallDetails();
            sehCallDetails.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            this.callDetails.add(sehCallDetails);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCall> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(88 + j, this.audioQuality);
        int size = this.callDetails.size();
        long j2 = 96 + j;
        hwBlob.putInt32(104 + j, size);
        hwBlob.putBool(j + 108, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            this.callDetails.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
