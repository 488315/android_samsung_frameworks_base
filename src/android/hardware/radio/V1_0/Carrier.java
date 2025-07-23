package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Carrier {
    public String mcc = new String();
    public String mnc = new String();
    public int matchType = 0;
    public String matchData = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Carrier.class) {
            return false;
        }
        Carrier carrier = (Carrier) obj;
        return HidlSupport.deepEquals(this.mcc, carrier.mcc) && HidlSupport.deepEquals(this.mnc, carrier.mnc) && this.matchType == carrier.matchType && HidlSupport.deepEquals(this.matchData, carrier.matchData);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.mcc)), Integer.valueOf(HidlSupport.deepHashCode(this.mnc)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.matchType))), Integer.valueOf(HidlSupport.deepHashCode(this.matchData)));
    }

    public final String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .matchType = " + CarrierMatchType.toString(this.matchType) + ", .matchData = " + this.matchData + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<Carrier> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Carrier> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            Carrier carrier = new Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(carrier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.mcc = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.mnc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.matchType = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        this.matchData = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Carrier> arrayList) {
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
        hwBlob.putString(j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt32(32 + j, this.matchType);
        hwBlob.putString(j + 40, this.matchData);
    }
}
