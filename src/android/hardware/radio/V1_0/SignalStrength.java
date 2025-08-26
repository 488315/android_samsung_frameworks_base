package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SignalStrength {
    public GsmSignalStrength gw = new GsmSignalStrength();
    public CdmaSignalStrength cdma = new CdmaSignalStrength();
    public EvdoSignalStrength evdo = new EvdoSignalStrength();
    public LteSignalStrength lte = new LteSignalStrength();
    public TdScdmaSignalStrength tdScdma = new TdScdmaSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SignalStrength.class) {
            return false;
        }
        SignalStrength signalStrength = (SignalStrength) obj;
        return HidlSupport.deepEquals(this.gw, signalStrength.gw) && HidlSupport.deepEquals(this.cdma, signalStrength.cdma) && HidlSupport.deepEquals(this.evdo, signalStrength.evdo) && HidlSupport.deepEquals(this.lte, signalStrength.lte) && HidlSupport.deepEquals(this.tdScdma, signalStrength.tdScdma);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.gw)), Integer.valueOf(HidlSupport.deepHashCode(this.cdma)), Integer.valueOf(HidlSupport.deepHashCode(this.evdo)), Integer.valueOf(HidlSupport.deepHashCode(this.lte)), Integer.valueOf(HidlSupport.deepHashCode(this.tdScdma)));
    }

    public final String toString() {
        return "{.gw = " + this.gw + ", .cdma = " + this.cdma + ", .evdo = " + this.evdo + ", .lte = " + this.lte + ", .tdScdma = " + this.tdScdma + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(60L), 0L);
    }

    public static final ArrayList<SignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SignalStrength> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 60, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SignalStrength signalStrength = new SignalStrength();
            signalStrength.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 60);
            arrayList.add(signalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.gw.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32 + j);
        this.tdScdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(60);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 60);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 60);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.gw.writeEmbeddedToBlob(hwBlob, j);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12 + j);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.lte.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.tdScdma.writeEmbeddedToBlob(hwBlob, j + 56);
    }
}
