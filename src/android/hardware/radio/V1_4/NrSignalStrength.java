package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NrSignalStrength {
    public int ssRsrp = 0;
    public int ssRsrq = 0;
    public int ssSinr = 0;
    public int csiRsrp = 0;
    public int csiRsrq = 0;
    public int csiSinr = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NrSignalStrength.class) {
            return false;
        }
        NrSignalStrength nrSignalStrength = (NrSignalStrength) obj;
        return this.ssRsrp == nrSignalStrength.ssRsrp && this.ssRsrq == nrSignalStrength.ssRsrq && this.ssSinr == nrSignalStrength.ssSinr && this.csiRsrp == nrSignalStrength.csiRsrp && this.csiRsrq == nrSignalStrength.csiRsrq && this.csiSinr == nrSignalStrength.csiSinr;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ssRsrp))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ssRsrq))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ssSinr))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csiRsrp))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csiRsrq))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csiSinr))));
    }

    public final String toString() {
        return "{.ssRsrp = " + this.ssRsrp + ", .ssRsrq = " + this.ssRsrq + ", .ssSinr = " + this.ssSinr + ", .csiRsrp = " + this.csiRsrp + ", .csiRsrq = " + this.csiRsrq + ", .csiSinr = " + this.csiSinr + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<NrSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrSignalStrength> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NrSignalStrength nrSignalStrength = new NrSignalStrength();
            nrSignalStrength.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            arrayList.add(nrSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.ssRsrp = hwBlob.getInt32(j);
        this.ssRsrq = hwBlob.getInt32(4 + j);
        this.ssSinr = hwBlob.getInt32(8 + j);
        this.csiRsrp = hwBlob.getInt32(12 + j);
        this.csiRsrq = hwBlob.getInt32(16 + j);
        this.csiSinr = hwBlob.getInt32(j + 20);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrSignalStrength> arrayList) {
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
        hwBlob.putInt32(j, this.ssRsrp);
        hwBlob.putInt32(4 + j, this.ssRsrq);
        hwBlob.putInt32(8 + j, this.ssSinr);
        hwBlob.putInt32(12 + j, this.csiRsrp);
        hwBlob.putInt32(16 + j, this.csiRsrq);
        hwBlob.putInt32(j + 20, this.csiSinr);
    }
}
