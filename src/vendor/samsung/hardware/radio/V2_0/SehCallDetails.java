package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCallDetails {
    public int callType = 0;
    public ArrayList<String> extras = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCallDetails.class) {
            return false;
        }
        SehCallDetails sehCallDetails = (SehCallDetails) obj;
        return this.callType == sehCallDetails.callType && HidlSupport.deepEquals(this.extras, sehCallDetails.extras);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.callType))), Integer.valueOf(HidlSupport.deepHashCode(this.extras)));
    }

    public final String toString() {
        return "{.callType = " + SehCallType.toString(this.callType) + ", .extras = " + this.extras + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<SehCallDetails> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCallDetails> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCallDetails sehCallDetails = new SehCallDetails();
            sehCallDetails.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(sehCallDetails);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.callType = hwBlob.getInt32(j);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j + 16);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2, true);
        this.extras.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = readEmbeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), i2, false);
            this.extras.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCallDetails> arrayList) {
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
        hwBlob.putInt32(j, this.callType);
        int size = this.extras.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(j + 20, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.extras.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
