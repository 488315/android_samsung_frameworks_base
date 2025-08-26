package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LteSignalStrength {
    public int signalStrength = 0;
    public int rsrp = 0;
    public int rsrq = 0;
    public int rssnr = 0;
    public int cqi = 0;
    public int timingAdvance = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LteSignalStrength.class) {
            return false;
        }
        LteSignalStrength lteSignalStrength = (LteSignalStrength) obj;
        return this.signalStrength == lteSignalStrength.signalStrength && this.rsrp == lteSignalStrength.rsrp && this.rsrq == lteSignalStrength.rsrq && this.rssnr == lteSignalStrength.rssnr && this.cqi == lteSignalStrength.cqi && this.timingAdvance == lteSignalStrength.timingAdvance;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalStrength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rsrp))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rsrq))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rssnr))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cqi))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.timingAdvance))));
    }

    public final String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .rsrp = " + this.rsrp + ", .rsrq = " + this.rsrq + ", .rssnr = " + this.rssnr + ", .cqi = " + this.cqi + ", .timingAdvance = " + this.timingAdvance + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<LteSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LteSignalStrength> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LteSignalStrength lteSignalStrength = new LteSignalStrength();
            lteSignalStrength.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            arrayList.add(lteSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.signalStrength = hwBlob.getInt32(j);
        this.rsrp = hwBlob.getInt32(4 + j);
        this.rsrq = hwBlob.getInt32(8 + j);
        this.rssnr = hwBlob.getInt32(12 + j);
        this.cqi = hwBlob.getInt32(16 + j);
        this.timingAdvance = hwBlob.getInt32(j + 20);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LteSignalStrength> arrayList) {
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
        hwBlob.putInt32(j, this.signalStrength);
        hwBlob.putInt32(4 + j, this.rsrp);
        hwBlob.putInt32(8 + j, this.rsrq);
        hwBlob.putInt32(12 + j, this.rssnr);
        hwBlob.putInt32(16 + j, this.cqi);
        hwBlob.putInt32(j + 20, this.timingAdvance);
    }
}
