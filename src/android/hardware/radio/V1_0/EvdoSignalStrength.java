package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class EvdoSignalStrength {
    public int dbm = 0;
    public int ecio = 0;
    public int signalNoiseRatio = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != EvdoSignalStrength.class) {
            return false;
        }
        EvdoSignalStrength evdoSignalStrength = (EvdoSignalStrength) obj;
        return this.dbm == evdoSignalStrength.dbm && this.ecio == evdoSignalStrength.ecio && this.signalNoiseRatio == evdoSignalStrength.signalNoiseRatio;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.dbm))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ecio))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalNoiseRatio))));
    }

    public final String toString() {
        return "{.dbm = " + this.dbm + ", .ecio = " + this.ecio + ", .signalNoiseRatio = " + this.signalNoiseRatio + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final ArrayList<EvdoSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<EvdoSignalStrength> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            EvdoSignalStrength evdoSignalStrength = new EvdoSignalStrength();
            evdoSignalStrength.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 12);
            arrayList.add(evdoSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.dbm = hwBlob.getInt32(j);
        this.ecio = hwBlob.getInt32(4 + j);
        this.signalNoiseRatio = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<EvdoSignalStrength> arrayList) {
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
        hwBlob.putInt32(j, this.dbm);
        hwBlob.putInt32(4 + j, this.ecio);
        hwBlob.putInt32(j + 8, this.signalNoiseRatio);
    }
}
